package com.example.demo.RestController;

import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Service.MusicService;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Controller
@Slf4j

public class PlayMP3 {
    private Player player;
    private boolean isLoop;
    private String currentMusicName;
    private FileInputStream finalFis;
    private BufferedInputStream finalBis;
    private MusicDto musicDto = new MusicDto();
    @Autowired
    private MusicService musicService;

    @PostMapping("/musicinfo")
    public @ResponseBody MusicDto MusicInfo(@RequestBody Map<String, String> request) {
        try {
            System.out.println("----------------------------------------------------");
            String title = request.get("title");
            System.out.println("title : " + title);
            musicDto = musicService.connect(title);
            System.out.println("MusicDto : " + musicDto);

            return musicDto;

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @GetMapping("/musicinfo")
    public @ResponseBody void playMusic(){
        System.out.println("play실행했따");
    }



    @PostMapping("/play")
    public @ResponseBody void playMusic(@RequestBody Map<String, String> request) {
        String fileTitle = null;
        try {
            if(player != null){
                player.close();
                System.out.println("멈춤");
            }
            System.out.println("play------------------------");

            String title = request.get("title");
            musicDto = musicService.connect(title);
            String filePath = musicDto.getMusic_path();

            File file = new File(filePath);
            String absolutePath = file.getAbsolutePath();
            System.out.println("FILE : " + filePath);

            // 절대 경로 출력
            System.out.println("절대 경로: " + absolutePath);
            FileInputStream fis = new FileInputStream(absolutePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            Thread playThread = new Thread(() -> {
                try {
                    player.play();
                    System.out.println("Play Music");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            playThread.start();
            System.out.println("다음곡재생완료");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }


    @GetMapping("/pause")
    public @ResponseBody void pauseMusic() {
        if(player != null){
            player.close();
            System.out.println("멈춤");
        }

    }

    @GetMapping("/stop")
    public @ResponseBody void stopMusic() {
        if(player!= null)
            player.close();
        System.out.println("멈춤");


    }

    @GetMapping("/time")
    public @ResponseBody int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }
}