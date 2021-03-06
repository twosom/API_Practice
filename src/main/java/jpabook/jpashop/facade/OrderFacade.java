package jpabook.jpashop.facade;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    public Order findOrder(Long id) {
        Order order = orderService.findOrder(id);
        /* 프레젠테이션 계층이 필요한 프록시 객체를 강제로 초기화한다. */
        order.getMember().getName();

        return order;
    }
}
