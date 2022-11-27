package com.tobyspring.studytobyspring.service;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import com.tobyspring.studytobyspring.policy.UserLevelUpgradePolicy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.NoSuchProviderException;
import javax.sql.DataSource;
import java.util.List;

@Component
public class UserService {

    private final UserDao userDao;
    private final UserLevelUpgradePolicy policy;
    private final DataSource dataSource;
    // UserService 가 트랜잭션 매니저의 구현체를 알고 있으면 DI 원칙에 위배되기 때문에 의존성을 주입받음
    private final PlatformTransactionManager transactionManager;

    public UserService(UserDao userDao, UserLevelUpgradePolicy policy, DataSource dataSource, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.policy = policy;
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() throws Exception {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();

            for (User user : users) {
                if (policy.canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }

            // 정상일 경우 commit
            this.transactionManager.commit(status);
        } catch (Exception e) {
            // 예외가 발생할 경우 rollback
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    public void upgradeLevel(User user) throws NoSuchProviderException {
        policy.upgradeLevel(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(from);
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 [" + user.getLevel().name() + "] 로 업그레이드 되었습니다.");

        mailSender.send(mailMessage);
    }

    public void add(User user) {
        // level 이 비어 있을 경우 BASIC 으로 초기화
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

}
