package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long rno;
    @ManyToOne
    @JoinColumn(name = "qno",foreignKey = @ForeignKey(name = "FK_reply_qna",
            foreignKeyDefinition = "FOREIGN KEY (qno) REFERENCES qna(no) ON DELETE CASCADE ON UPDATE CASCADE") ) //FK설정\
    private QnA qna;
    private String content;
    private LocalDateTime regdate;  // 등록날짜



}