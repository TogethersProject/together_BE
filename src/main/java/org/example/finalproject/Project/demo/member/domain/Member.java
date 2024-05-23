package org.example.finalproject.Project.demo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.finalproject.Project.demo.member.DTO.MemberDTO;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "member_id")
    @Size(min = 6
            , message = "아이디의 길이는 6개 이상이여야 합니다.")
    @NotNull
    private String id;   //아이디


    @NotNull
    @Column(name = "member_name")
    @Size(min = 2
            , message = "이름은 2글자 이상이여야 합니다.")
    private String name; //이름

    @NotNull
    @Column(name = "member_pwd")
    @Size(min = 7 , max = 14
            ,message = "비밀번호는 7 글자이상 14 글자 이하여야 합니다 .")
    private String pwd;  //비밀번호

    @NotNull
    @Column(name = "member_address")
    private String address;


    @Column(name = "member_phone")
    private Long phone; //전화번호

    @NotNull
    @Column(name = "member_email")
    private String email; //이메일

    @NotNull
    @Column(name = "member_jointime")
    private LocalDateTime jointime ; // 가입 시간

    @Column(name = "member_bncode")
    private String bncode;

    public static Member createDtoToEntity(MemberDTO memberDTO) {

        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setPwd(memberDTO.getPwd());
        member.setAddress(memberDTO.getAddress());
        member.setPhone(memberDTO.getPhone());
        member.setEmail(memberDTO.getEmail());
        member.setJointime(memberDTO.getJointime());
        member.setBncode(memberDTO.getBncode());
        return member;
    }

}
