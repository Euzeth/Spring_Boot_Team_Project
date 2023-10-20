package com.example.demo.Domain.Dto;

import com.example.demo.Domain.Entity.QnA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnADto {

    private Long no;
    @NotBlank(message = "username을 입력하세요")
    private String username;
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regdate;
    private Long count;
    private MultipartFile[] files;


    public static QnADto Of(QnA qna) {
        QnADto dto = new QnADto();
        dto.no = qna.getNo();
        dto.title=qna.getTitle();
        dto.content = qna.getContent();
        dto.regdate = qna.getRegdate();
        dto.count = qna.getCount();
        dto.username  = qna.getUsername();
        return dto;
    }
}