package org.example.finalproject.Project.demo.member.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.finalproject.Project.demo.member.DTO.MemberDTO;
import org.example.finalproject.Project.demo.member.Service.MemberServiceImpl;
import org.example.finalproject.Project.demo.member.domain.Member;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Log4j2
public class MemberController {

    private final MemberServiceImpl memberService;

    /**
    * 회원가입 하기
    * */

    @PostMapping(path = "/membersave")
    public ResponseEntity<Void> saveMember(@Valid @RequestBody MemberDTO memberDTO) {

        Member member = Member.createDtoToEntity(memberDTO);
        memberService.MemberJoin(memberDTO);

        return ResponseEntity.ok().build();
    }

    /**
     * 아이디 유효성 검사
     * */
    @PostMapping("/overlap")
    public ResponseEntity<Member>overlapId(@Valid @RequestBody MemberDTO memberDTO){

//        DTO -> ENTITY
        Member member = Member.createDtoToEntity(memberDTO);
        //서비스에 변경하기
        memberService.overlapId(member);
        return ResponseEntity.ok(member);
    }


    /**
    * 로그인 하기
    * */

    @GetMapping(path = "/Login")
    public ResponseEntity<MemberDTO> login(@Valid @RequestBody MemberDTO memberDTO, HttpServletResponse response) {
        try {
            MemberDTO loginResponse = memberService.login(memberDTO.getId(), memberDTO.getPwd());
            if (loginResponse == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Cookie cookie = new Cookie("id", memberDTO.getId());
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return ResponseEntity.ok(memberDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
    * 로그아웃
    * */
    @GetMapping("/Logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 쿠키를 생성하고 즉시 만료시킵니다.
        Cookie cookie = new Cookie("id", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃이 되었습니다. ");
    }


    /**
     * 아이디 찾기
     * */
    @PostMapping("/findID")
    public ResponseEntity<MemberDTO> findId(@RequestBody MemberDTO memberDTO) {

        MemberDTO findMemberID = memberService.findIdByEmailAndName(memberDTO.getName(), memberDTO.getEmail());

        if (findMemberID != null) {
            return ResponseEntity.ok(findMemberID);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 비밀번호 찾기
     */

    @GetMapping("/finPwd")
    public ResponseEntity<List<Member>> findPwd (@RequestBody MemberDTO memberDTO){

        List<Member> findPwd = memberService
                .findPwd(memberDTO.getId(), memberDTO.getEmail(), memberDTO.getPwd());

        return ResponseEntity.ok(findPwd);
    }
}
