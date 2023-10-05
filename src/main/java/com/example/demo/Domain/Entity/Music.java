package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Music {
    @Id
    private int music_code;
    private String artist;
    private String title;
    private String music_path;
    private Long count;


}
