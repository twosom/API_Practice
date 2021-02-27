package jpabook.jpashop.controller;


import jpabook.jpashop.domain.item.Movie;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MovieForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //==영화 속성==//
    private String director;
    private String actor;


    public Movie toEntity() {
        return Movie.builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .stockQuantity(this.getStockQuantity())
                .director(this.getDirector())
                .actor(this.getActor())
                .build();
    }

    @Override
    public String toString() {
        return "MovieForm{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }

    @Builder
    public MovieForm(Long id, String name, int price, int stockQuantity, String director, String actor) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.director = director;
        this.actor = actor;
    }
}
