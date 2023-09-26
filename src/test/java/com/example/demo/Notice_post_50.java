package com.example.demo;


import com.example.demo.Domain.Entity.Notice;
import com.example.demo.Domain.Repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class Notice_post_50 {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void post_50() throws Exception {
        for(Long i = 1L; i<=500; i++){

            Notice notice = Notice.builder()
                    .no(i)
                    .count(0L)
                    .content("내용"+i)
                    .dirpath(null)
                    .filename(null)
                    .filesize(null)
                    .regdate(LocalDate.now())
                    .title("제목"+i)
                    .category("안내")
                    .build();
            noticeRepository.save(notice);
        }

    }
    }

