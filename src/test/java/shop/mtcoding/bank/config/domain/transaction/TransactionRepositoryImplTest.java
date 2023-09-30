package shop.mtcoding.bank.config.domain.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import java.util.List;

@Sql("classpath:db/teardown.sql")
@DataJpaTest
public class TransactionRepositoryImplTest extends DummyObject {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        dataSetting();
    }

    @DisplayName("간단한 데이터 조회 테스트 - 따로 검증은 하지 않는다.")
    @Test
    public void dataJpa_test1() {
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach((transaction) -> {
            System.out.println("id : " + transaction.getId());
            System.out.println("sender : " + transaction.getSender());
            System.out.println("receiver : " + transaction.getReceiver());
            System.out.println("gubun : " + transaction.getGubun());
            System.out.println("테스트 : ========================");
        });
    }


    @DisplayName("@BeforeEach 에서 더미 데이터 셋팅이 잘 되었는지, dataJpa_test1과 동일하게 데이터를 조회해본다.")
    @Test
    public void dataJpa_test2() {
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach((transaction) -> {
            System.out.println("테스트 : " + transaction.getId());
            System.out.println("테스트 : " + transaction.getSender());
            System.out.println("테스트 : " + transaction.getReceiver());
            System.out.println("테스트 : " + transaction.getGubun());
            System.out.println("테스트 : ========================");
        });
    }

    private void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));
        User love = userRepository.save(newUser("love", "러브"));
        User admin = userRepository.save(newUser("admin", "관리자"));

        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        Account loveAccount = accountRepository.save(newAccount(3333L, love));
        Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

        Transaction withdrawTransaction1 = transactionRepository
                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
        Transaction depositTransaction1 = transactionRepository
                .save(newDepositTransaction(cosAccount, accountRepository));
        Transaction transferTransaction1 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
        Transaction transferTransaction2 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
        Transaction transferTransaction3 = transactionRepository
                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
    }

}
