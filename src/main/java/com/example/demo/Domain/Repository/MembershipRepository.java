package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Dto.MembershipDto;
import com.example.demo.Domain.Entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,String> {

    @Query(value="SELECT * FROM musicdb.membership m order by m.membershipcode asc", nativeQuery = true)
    List<Membership> findMembershipListAll();

    @Query(value = "SELECT * FROM musicdb.membership m WHERE m.username LIKE :username", nativeQuery = true)
    Membership findMembershipUsername(@Param("username")String username);

    @Query(value = "SELECT * FROM musicdb.membership m WHERE m.membershipcode = :membershipcode", nativeQuery = true)
    List<Membership> findMembershipCode(@Param("membershipcode")String membershipcode);

    @Query(value = "SELECT NEW com.example.demo.Domain.Dto.MembershipDto(m.username, m.name, m.startdate, m.enddate, m.membershipcode) FROM Membership m WHERE m.enddate = :enddate")
    List<MembershipDto> findMembershipEnddate(@Param("enddate")LocalDate enddate);

    @Query(value = "SELECT * FROM musicdb.membership m WHERE m.terminationdate = :now", nativeQuery = true)
    List<Membership> findByTerminationdateBefore(@Param("now")LocalDate now);


}