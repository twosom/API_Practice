package jpabook.jpashop;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();

    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", new Address("서울", "1", "1111"));
            em.persist(member);

            Item movie1 = createMovie("TENET", 10_000, 100);
            em.persist(movie1);

            Item movie2 = createMovie("PIRATE_OF_THE_CARIBBEAN", 20_000, 100);
            em.persist(movie2);

            OrderItem orderItem1 = OrderItem.createOrderItem(movie1, movie1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(movie2, movie2.getPrice(), 2);


            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }


        public void dbInit2() {
            Member member = createMember("userB", new Address("진주", "2", "2222"));
            em.persist(member);

            Item movie1 = createMovie("BATTLE_SHIP", 20_000, 200);
            em.persist(movie1);

            Item movie2 = createMovie("SOURCE_CODE", 40_000, 300);
            em.persist(movie2);

            OrderItem orderItem1 = OrderItem.createOrderItem(movie1, movie1.getPrice(), 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(movie2, movie2.getPrice(), 4);


            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            delivery.setStatus(DeliveryStatus.READY);
            return delivery;
        }

        private Item createMovie(String name, int price, int stockQuantity) {
            Item movie1 = new Movie();
            movie1.setName(name);
            movie1.setPrice(price);
            movie1.setStockQuantity(stockQuantity);
            return movie1;
        }

        private Member createMember(String name, Address address) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(address);
            return member;
        }
    }
}


