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
    private String username;
    private String content;
    private Long likecount;       //좋아요 Count
    private Long unlikecount;     //싫어요 Count
    private LocalDateTime regdate;  // 등록날자
}
