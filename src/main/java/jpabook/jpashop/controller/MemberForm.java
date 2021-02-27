package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;


    @NotEmpty(message = "주소는 필수입니다.")
    private String city;
    @NotEmpty(message = "주소는 필수입니다.")
    private String street;
    @NotEmpty(message = "주소는 필수입니다.")
    private String zipcode;
    private Long id;

    //==엔티티 변환 메소드==//
    public Member toEntity() {
        return Member.builder()
                .name(getName())
                .address(new Address(getCity(), getStreet(), getZipcode()))
                .build();
    }

    //==엔티티를 위한 DTO 변환 메소드==//
    @Builder
    public MemberForm(Long id, String name, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }




}
