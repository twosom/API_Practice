package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class OrderDto {

    private Long orderId;
    private String name;
    private String orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss EEE"));
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(toList());
    }
}

