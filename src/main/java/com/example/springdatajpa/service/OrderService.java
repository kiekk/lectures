package com.example.springdatajpa.service;

import com.example.springdatajpa.domain.Delivery;
import com.example.springdatajpa.domain.Member;
import com.example.springdatajpa.domain.Order;
import com.example.springdatajpa.domain.OrderItem;
import com.example.springdatajpa.domain.item.Item;
import com.example.springdatajpa.repository.ItemRepository;
import com.example.springdatajpa.repository.MemberRepository;
import com.example.springdatajpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // entity 조회
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.find(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancel(Long orderId) {
        // 주문 entity 조회
        Order order = orderRepository.find(orderId);

        // 주문 취소
        order.cancel();
    }

}
