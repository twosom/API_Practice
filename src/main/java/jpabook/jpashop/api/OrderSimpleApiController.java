package jpabook.jpashop.api;


import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


/*
* xToOne(ManyToOne, OneToOne)
* Order
* Oder -> Member
* Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

}
