package com.example.product.payment;

import com.example.product.DiscountPolicy;
import com.example.product.Product;
import com.example.product.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class PaymentServiceTest {

    private PaymentService paymentService;
    private PaymentPort paymentPort;

    @BeforeEach
    void setUp() {
        final PaymentGateway paymentGateway = new ConsolePaymentGateway();
        final PaymentRepository paymentRepository = new PaymentRepository();
        paymentPort = new PaymentAdapter(paymentGateway, paymentRepository);
        paymentService = new PaymentService(paymentPort);
    }

    @Test
    void 상품주문() {
        final PaymentRequest request = 주문결제요청_생성();

        paymentService.payment(request);
    }

    private static PaymentRequest 주문결제요청_생성() {
        final Long orderId = 1L;
        final String cardNumber = "1234-1234-1234-1234";
        return new PaymentRequest(orderId, cardNumber);
    }

    private class PaymentAdapter implements PaymentPort {

        private final PaymentGateway paymentGateway;
        private final PaymentRepository paymentRepository;

        private PaymentAdapter(PaymentGateway paymentGateway, PaymentRepository paymentRepository) {
            this.paymentGateway = paymentGateway;
            this.paymentRepository = paymentRepository;
        }

        @Override
        public Order getOrder(Long orderId) {
            return new Order(new Product("상품1", 1000, DiscountPolicy.NONE), 2);
        }

        @Override
        public void pay(Payment payment) {
            paymentGateway.execute(payment);
        }

        @Override
        public void save(Payment payment) {
            paymentRepository.save(payment);
        }
    }

    private record PaymentRequest(Long orderId, String cardNumber) {
        private PaymentRequest {
            Assert.notNull(orderId, "주문 ID는 필수입니다.");
            Assert.hasText(cardNumber, "카드 번호는 필수입니다.");
        }
    }

    private class PaymentService {
        private final PaymentPort paymentPort;

        private PaymentService(PaymentPort paymentPort) {
            this.paymentPort = paymentPort;
        }

        public void payment(final PaymentRequest request) {
            Order order = paymentPort.getOrder(request.orderId);

            Payment payment = new Payment(order, request.cardNumber());

            paymentPort.pay(payment);
            paymentPort.save(payment);
        }
    }

    private interface PaymentPort {
        Order getOrder(final Long orderId);

        void pay(final Payment payment);

        void save(final Payment payment);
    }

    private record Payment(Order order, String cardNumber) {
        private static Long id;

        private Payment {
            Assert.notNull(order, "주문은 필수입니다.");
            Assert.hasText(cardNumber, "카드 번호는 필수입니다.");
        }

        public void assignId(final Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    private interface PaymentGateway {
        void execute(final Payment payment);
    }

    public class ConsolePaymentGateway implements PaymentGateway {
        @Override
        public void execute(Payment payment) {
            System.out.println("결제 완료");
        }
    }

    private class PaymentRepository {
        private Map<Long, Payment> persistence = new HashMap<>();
        private Long sequence = 0L;

        public void save(final Payment payment) {
            payment.assignId(++sequence);
            persistence.put(payment.getId(), payment);
        }
    }
}
