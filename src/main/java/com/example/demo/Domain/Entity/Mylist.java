package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mylist {
    @Id
    private String lusername;
    @Id
    private Long lmusic_code;
    @ManyToMany
    @JoinColumn(name = "lusername",foreignKey = @ForeignKey(name = "FK_mylist_member",
            foreignKeyDefinition = "FOREIGN KEY (lusername) REFERENCES member(username) ON DELETE CASCADE ON UPDATE CASCADE") ) //FK설정\
    private Member member;
    @JoinColumn(name = "lmusic_code", foreignKey = @ForeignKey(name = "FK_mylist_music",
            foreignKeyDefinition = "FOREIGN KEY (lmusic_code) REFERENCES music(music_code) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Music music;
}
