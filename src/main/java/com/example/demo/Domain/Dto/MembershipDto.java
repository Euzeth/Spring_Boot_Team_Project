package com.example.demo.Domain.Dto;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Entity.Member;
import com.example.demo.Domain.Entity.Membership;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

/*@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDto {
    private String username;
    private String name;
    private LocalDate startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;
    private String membershipcode;*/

    public interface MembershipDto{
        String getUsername();
        String getName();
        LocalDate getStartdate();
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate getEnddate();
        String getMembershipcode();
        String setMembershipcode(String membershipcode);

    public static Membership dtoToEntity(MembershipDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        Membership membership = Membership.builder()
                .username(principalDetails.getUsername())
                .name(principalDetails.getName())
                .membershipcode(dto.getMembershipcode())
                .startdate(startDate)
                .enddate(endDate)
                .build();

        return membership;
    }
}
