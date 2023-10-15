package com.example.demo.Domain.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mylist {
    @EmbeddedId
    private MylistId mylistId;
    @ManyToOne
    @MapsId("lusername")
    @JoinColumn(name = "lusername", foreignKey = @ForeignKey(name = "FK_mylist_member",
            foreignKeyDefinition = "FOREIGN KEY (lusername) REFERENCES member(username) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Member member;
    @ManyToOne
    @MapsId("lmusic_code")
    @JoinColumn(name = "lmusic_code", foreignKey = @ForeignKey(name = "FK_mylist_music",
            foreignKeyDefinition = "FOREIGN KEY (lmusic_code) REFERENCES music(music_code) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Music music;

    public void setMylistId(String lusername, Long lmusic_code) {
        if (mylistId == null) {
            mylistId = new MylistId();
        }
        mylistId.setLusername(lusername);
        mylistId.setLmusic_code(lmusic_code);
    }

}
