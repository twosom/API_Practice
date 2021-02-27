package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.controller.MemberForm;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;
    @Embedded
    private Address address;


    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


    //==DTO를 위한 엔티티 변환 메소드==//
    @Builder
    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public MemberForm toDto() {
        return MemberForm
                .builder()
                .id(getId())
                .name(getName())
                .city(getAddress().getCity())
                .street(getAddress().getStreet())
                .zipcode(getAddress().getZipcode())
                .build();
    }
}
