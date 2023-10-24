package com.example.demo.Domain.Dto;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Entity.Membership;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipDto {
    private String username;
    private String name;
    private LocalDate startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;
    private String membershipcode;

    public static Membership dtoToEntity(MembershipDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
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
