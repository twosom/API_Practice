package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.MovieForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter @Setter
@Entity
@DiscriminatorValue("M")
@NoArgsConstructor
public class Movie extends Item {
    private String director;
    private String actor;


    @Builder
    public Movie(Long id, String name, int price, int stockQuantity, String director, String actor) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.director = director;
        this.actor = actor;
    }


    public MovieForm toMovieForm() {
        return MovieForm.builder()
                .id(getId())
                .name(getName())
                .price(getPrice())
                .stockQuantity(getStockQuantity())
                .director(getDirector())
                .actor(getActor())
                .build();
    }
}
