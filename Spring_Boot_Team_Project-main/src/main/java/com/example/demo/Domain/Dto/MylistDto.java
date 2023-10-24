package com.example.demo.Domain.Dto;

import com.example.demo.Domain.Entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MylistDto {
    private String lusername;
    private Long lmusic_code;
    private Music music;
}