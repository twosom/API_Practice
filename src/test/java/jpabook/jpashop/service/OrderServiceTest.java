package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Item movie = createMovie("TENET", 10_000, 10);

        //when
        int orderCount = 8;
        Long orderId = orderService.order(member.getId(), movie.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER이어야 된다.", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", movie.getPrice() * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 2, movie.getStockQuantity());

    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item tenet = createMovie("TENET", 10_000, 200_000);

        int orderCount = 300_000;
        orderService.order(member.getId(), tenet.getId(), orderCount);

        //when

        //then
        fail("재고수량초과 예외가 발생해야한다.");

    }



    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item tenet = createMovie("TENET", 10_000, 20_000_000);

        int orderCount = 10_000_000;
        Long orderId = orderService.order(member.getId(), tenet.getId(), orderCount);

        //when
        assertEquals("주문한 후에 재고수량이 맞아야 한다", 10_000_000, tenet.getStockQuantity());
        orderService.cancelOrder(orderId);
        //then
        assertEquals("주문취소 후에 재고수량이 맞아야 한다", 20_000_000, tenet.getStockQuantity());
        assertEquals("주문취소 후에 주문상태가 취소로 되어야 한다.", OrderStatus.CANCEL, orderRepository.findOne(orderId).getStatus());

    }

    private Item createMovie(String name, int price, int stockQuantity) {
        Item movie = new Movie();
        movie.setName(name);
        movie.setPrice(price);
        movie.setStockQuantity(stockQuantity);
        em.persist(movie);
        return movie;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}
