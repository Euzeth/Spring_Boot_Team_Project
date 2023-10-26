package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Notice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;
    private String category;
    private String title;
    private String content;
    private LocalDate regdate;
    private Long count;
    private String dirpath;
    private String filename;
    private String filesize;


}