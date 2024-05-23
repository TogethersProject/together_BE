package org.example.finalproject.Project.demo.member.DTO;

import lombok.*;
import org.example.finalproject.Project.demo.member.domain.Member;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {



    private String id;   //아이디

    private String name; //이름

    private String pwd;  //비밀번호

    private String pwd_re ;

    private String address;

    private Long phone; //전화번호

    private String email; //이메일

    private LocalDateTime jointime ; // 가입 시간

    private String bncode;

    public MemberDTO(Member member) {

        this.id = member.getId();
        this.name = member.getName();
        this.pwd = member.getPwd();
        this.address = member.getAddress();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.jointime = member.getJointime();
        this.bncode = member.getBncode();
    }

    public static MemberDTO createEntityToDto(Member member) {

        return new MemberDTO(member);
    }
}
