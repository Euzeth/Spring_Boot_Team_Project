package com.example.demo.Domain.Dto;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Entity.Mylist;
import com.example.demo.Domain.Entity.MylistId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MylistDto {
    private String lusername;
    private Long lmusic_code;
    private Music music;
}
