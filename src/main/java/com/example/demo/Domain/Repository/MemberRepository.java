package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Member;
import com.example.demo.Domain.Entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,String> {
    @Query(value = "SELECT * FROM musicdb.member m WHERE username = :username",nativeQuery = true)
    Member findByUsername(@Param("username")String username);
}
