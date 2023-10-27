package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
}
