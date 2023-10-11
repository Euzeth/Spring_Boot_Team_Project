package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Music {
    @Id
    private Long music_code;
    private String artist;
    private String title;
    private String music_path;
    private Long mlike;
    private Long count;



}
