package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mylist {
    @EmbeddedId
    private MylistId mylistId;
    @Getter
    @ManyToOne
    @MapsId("lmusic_code")
    @JoinColumn(name = "lmusic_code", foreignKey = @ForeignKey(name = "FK_mylist_music",
            foreignKeyDefinition = "FOREIGN KEY (lmusic_code) REFERENCES music(music_code) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Music music = new Music();
    @ManyToOne
    @MapsId("lusername")
    @JoinColumn(name = "lusername", foreignKey = @ForeignKey(name = "FK_mylist_member",
            foreignKeyDefinition = "FOREIGN KEY (lusername) REFERENCES member(username) ON DELETE CASCADE ON UPDATE CASCADE"))


    public void setMylistId(String lusername, Long lmusic_code) {

        mylistId = new MylistId();

        mylistId.setLusername(lusername);
        mylistId.setLmusic_code(lmusic_code);

    }

}