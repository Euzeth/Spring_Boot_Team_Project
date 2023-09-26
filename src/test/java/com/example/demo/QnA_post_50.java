package com.example.demo;

import com.example.demo.Domain.Entity.QnA;
import com.example.demo.Domain.Repository.QnARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class QnA_post_50 {

    @Autowired
    private QnARepository qnaRepository;

    @Test
    public void qna_post_50() throws Exception {
        for(Long i = 1L; i <= 500; i++) {

            QnA qna = QnA.builder()
                    .no(i)
                    .count(0L)
                    .title("제목"+i)
                    .content("내용"+i)
                    .filename(null)
                    .filesize(null)
                    .regdate(LocalDate.now())
                    .title("제목"+i)
                    .build();
            qnaRepository.save(qna);
        }
    }
}
