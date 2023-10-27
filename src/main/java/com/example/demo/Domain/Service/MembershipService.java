package com.example.demo.Domain.Service;

import com.example.demo.Domain.Dto.MembershipDto;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Repository.MembershipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MembershipService {

    @Autowired
    MembershipRepository membershipRepository;


    @Transactional(rollbackFor = SQLException.class)
    public List<Membership> getMembershipList() {
        return membershipRepository.findMembershipListAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Membership getMembershipOne(String username) {
        return membershipRepository.findMembershipUsername(username);
    }


    @Transactional(rollbackFor = Exception.class)
    public List<Membership> getMembershipCode(String membershipcode) {
        return membershipRepository.findMembershipCode(membershipcode);
    }


    @Transactional(rollbackFor = Exception.class)
    public List<MembershipDto> getMembershipDate(LocalDate enddate) {
        return membershipRepository.findMembershipEnddate(enddate);
    }


    @Transactional(rollbackFor = SQLException.class)
    public void addMembership(MembershipDto dto, Authentication authentication, HttpServletRequest request) throws IOException {
        String requestURL = request.getRequestURL().toString();

        //request1, 2에 따라서 코드 다르게 적용
        if (requestURL.contains("/membership/success1")) {
            dto.setMembershipcode("WMM_1");
        } else if (requestURL.contains("/membership/success2")) {
            dto.setMembershipcode("WMM_2");
        }

        Membership membership = MembershipDto.dtoToEntity(dto, authentication);
        membershipRepository.save(membership);
    }

    @Transactional(rollbackFor = SQLException.class)
    public String removeMembership(String username) {
        Optional<Membership> membershipOptional = membershipRepository.findById(username);
        System.out.println(membershipOptional);

        if (membershipOptional.isPresent()) {
            Membership membership = membershipOptional.get();
            membershipRepository.delete(membership);
            return username + " 유저가 삭제되었습니다.";
        } else {
            return "찾으시는 USERNAME이 없습니다.";
        }
    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean terminateMembership(String username) {
        Membership terminateUser = membershipRepository.findMembershipUsername(username);
        System.out.println("user : "+terminateUser);

        terminateUser.setTerminationdate(terminateUser.getEnddate().plusDays(1));

        membershipRepository.save(terminateUser);

        return true;
    }

    @Scheduled(cron = "0 0 0 * * *") //매일 자정 실행
    public void deleteTerminatedMemberships() {
        LocalDate now = LocalDate.now();
        List<Membership> membershipsToDelete = membershipRepository.findByTerminationdateBefore(now);
        for (Membership membership : membershipsToDelete) {
            //멤버십 삭제
            membershipRepository.delete(membership);
        }
    }

}