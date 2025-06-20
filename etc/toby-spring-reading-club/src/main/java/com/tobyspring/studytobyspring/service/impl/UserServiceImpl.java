package com.tobyspring.studytobyspring.service.impl;

import com.tobyspring.studytobyspring.dao.UserDao;
import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import com.tobyspring.studytobyspring.policy.UserLevelUpgradePolicy;
import com.tobyspring.studytobyspring.service.UserService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final MailSender mailSender;
    private final UserLevelUpgradePolicy policy;
    private final PlatformTransactionManager transactionManager;

    public UserServiceImpl(UserDao userDao, MailSender mailSender, UserLevelUpgradePolicy policy, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.mailSender = mailSender;
        this.policy = policy;
        this.transactionManager = transactionManager;
    }

    @Override
    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            upgradeLevelsInternal();
            // 정상일 경우 commit
            this.transactionManager.commit(status);
        } catch (Exception e) {
            // 예외가 발생할 경우 rollback
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    private void upgradeLevelsInternal() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if (policy.canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public void upgradeLevel(User user) {
        policy.upgradeLevel(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("dbstnsgh2@naver.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 [" + user.getLevel().name() + "] 로 업그레이드 되었습니다.");

        this.mailSender.send(mailMessage);
    }
}
