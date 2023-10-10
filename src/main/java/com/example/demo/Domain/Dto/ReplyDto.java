package com.example.demo.Domain.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private Long rno;
    private Long bno;
    private String content;
    private LocalDate regdate;  // 등록날자
}
