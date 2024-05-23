package org.example.finalproject.Project.demo.member.repository;

import org.example.finalproject.Project.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Object> {


    Member findAllById(String name, String email, String phone);

    Optional<Member> findByNameAndEmail(String name, String email);

    List<Member> findAll(String id, String email, String pwd);
}
