package com.example.demo.Domain.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Data
public class MylistId implements Serializable {
    private String lusername;  // Mylist 엔티티의 FK로 참조할 Member 테이블의 username
    private Long lmusic_code;  // Mylist 엔티티의 FK로 참조할 Music 테이블의 music_code

    public MylistId(String lusername, Long lmusic_code) {
        this.lusername = lusername;
        this.lmusic_code = lmusic_code;
    }

    public void setUsername(String username) {
        this.lusername = username;
    }

    public void setMusicCode(Long music_code) {
        this.lmusic_code = music_code;
    }
}