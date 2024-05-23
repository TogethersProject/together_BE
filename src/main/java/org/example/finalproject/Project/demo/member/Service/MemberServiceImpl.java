package org.example.finalproject.Project.demo.member.Service;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.Project.demo.member.DTO.MemberDTO;
import org.example.finalproject.Project.demo.member.domain.Member;
import org.example.finalproject.Project.demo.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository ;

    /*
    * 회원가입
    * */
    public Member MemberJoin(MemberDTO memberDTO) {

        //entity -> dto 만들기
        Member member = Member.createDtoToEntity(memberDTO);
        Member saveMember = memberRepository.save(member);

        return saveMember;
    }

    /**
    * 로그인 하기
    * 아이디 틀렸을때 아이디 틀렸다 비밀번호 틀렸을때는 비밀번호가 틀렸다 .
    * */
    public MemberDTO login (String id, String password) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(!optionalMember.isPresent()) {
            throw new IllegalArgumentException("아이디가 틀렸습니다.");
        }
        Member member = optionalMember.get();
        if(!member.getPwd().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return new MemberDTO(member);
    }


    /**
    * 아이디 찾기
    * */
    public MemberDTO findIdByEmailAndName(String name, String email) {

        //DTO 를 Entity로 변환하기
        Member member = memberRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원 정보가 없습니다."));
                    //Error터트리기

        return new MemberDTO(member);
    }

    /**
    * 전체 회원 정보 조회
    * */
    @Transactional
    public List<Member> findAllMembers(MemberDTO memberDTO) {
        return memberRepository.findAll();
    }

    /**
    * 비밀번호 찾기
    * */
    public List<Member> findPwd (String id , String email , String pwd ){
        return memberRepository.findAll(id, email , pwd);
    }

    /**
    *아이디 유효성 검사
    * */
    @Transactional
    public void overlapId(Member member) {

        if (memberRepository.findById(member.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }
}
