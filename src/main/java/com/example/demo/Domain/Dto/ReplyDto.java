package com.example.demo.Domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private Long rno;
    private Long qno;
    private String content;
    private LocalDateTime regdate;  // 등록날짜
}