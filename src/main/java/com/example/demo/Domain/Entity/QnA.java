package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QnA {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;
    private String username;
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regdate;
    private Long count;
    private String dirpath;
    private String filename;
    private String filesize;



}