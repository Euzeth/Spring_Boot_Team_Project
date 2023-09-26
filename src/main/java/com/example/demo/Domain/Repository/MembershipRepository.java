package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Dto.MembershipDto;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Entity.Notice;
import com.example.demo.Domain.Entity.QnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,String> {

    @Query(value="SELECT * FROM MEMBERSHIP order by membershipcode asc", nativeQuery = true)
    List<MembershipDto> findMembershipListAll();
    @Query(value = "SELECT * FROM musicdb.membership m WHERE m.username LIKE :username", nativeQuery = true)
    Membership findMembershipUsername(@Param("username")String username);

    @Query(value = "SELECT * FROM musicdb.membership m WHERE m.membershipcode LIKE :membershipcode", nativeQuery = true)
    List<MembershipDto> findMembershipCode(@Param("membershipcode")String membershipcode);

    @Query(value = "SELECT * FROM musicdb.membership m WHERE m.enddate LIKE :enddate", nativeQuery = true)
    List<MembershipDto> findMembershipEnddate(@Param("enddate")LocalDate enddate);



}
