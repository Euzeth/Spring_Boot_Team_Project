package com.example.demo.Domain.Dto;



import com.example.demo.Domain.Entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDto {
    private Long no;
    private String category;
    private String title;
    private String content;
    private LocalDate regdate;
    private Long count;
    private MultipartFile[] files;



    public static NoticeDto Of(Notice notice) {
        NoticeDto dto = new NoticeDto();
        dto.no = notice.getNo();
        dto.category = notice.getCategory();
        dto.title=notice.getTitle();
        dto.content = notice.getContent();
        dto.regdate = notice.getRegdate();
        dto.count = notice.getCount();
        return dto;
    }
}