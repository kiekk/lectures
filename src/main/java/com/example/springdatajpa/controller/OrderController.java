package com.example.springdatajpa.controller;

import com.example.springdatajpa.domain.Member;
import com.example.springdatajpa.domain.Order;
import com.example.springdatajpa.domain.item.Item;
import com.example.springdatajpa.repository.OrderSearch;
import com.example.springdatajpa.service.ItemService;
import com.example.springdatajpa.service.MemberService;
import com.example.springdatajpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @PostMapping("orders/{orderId}/cancel")
    public String orderCancel(@PathVariable Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/orders";
    }

}
