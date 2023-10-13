package com.example.demo.RestController;

import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Service.MusicService;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@Controller
@Slf4j

public class PlayMP3 {
    private Player player;
    private boolean isLoop;
    private String currentMusicName;
    private FileInputStream finalFis;
    private BufferedInputStream finalBis;

    @Autowired
    private MusicService musicService;

    @GetMapping("/play")
    public String playMusic(Model model) {
        String fileTitle = null;
        try {
            if (player != null) {
                player.close();
            }

            MusicDto dto = new MusicDto();
            MusicDto musicDto = musicService.connect(dto);
            fileTitle = musicDto.getTitle();
            String filePath = musicDto.getMusic_path();

            File file = new File(filePath);
            String absolutePath = file.getAbsolutePath();
            System.out.println("FILE : " + filePath);

            // 절대 경로 출력
            System.out.println("절대 경로: " + absolutePath);
            FileInputStream fis = new FileInputStream(absolutePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            player.play();
            System.out.println("재생완료");

            model.addAttribute("title", fileTitle);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return fileTitle;
    }

    @GetMapping("/nextplay")
    public String NextPlayMusic(Model model) {
        String fileTitle = null;
        try {

            MusicDto dto = new MusicDto();
            MusicDto musicDto = musicService.connectNext(dto);
            fileTitle = musicDto.getTitle();
            String filePath = musicDto.getMusic_path();


            File file = new File(filePath);
            String absolutePath = file.getAbsolutePath();
            System.out.println("FILE : " + filePath);

            // 절대 경로 출력
            System.out.println("절대 경로: " + absolutePath);
            FileInputStream fis = new FileInputStream(absolutePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            player.play();
            System.out.println("다음곡재생완료");

            model.addAttribute("title", fileTitle);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return fileTitle;
    }

    @GetMapping("/previousplay")
    public String PreviousPlayMusic(Model model) {
        String fileTitle = null;
        try {
            if (player != null) {
                player.close();
            }

            MusicDto dto = new MusicDto();
            MusicDto musicDto = musicService.connectNext(dto);
            fileTitle = musicDto.getTitle();
            String filePath = musicDto.getMusic_path();


            File file = new File(filePath);
            String absolutePath = file.getAbsolutePath();
            System.out.println("FILE : " + filePath);

            // 절대 경로 출력
            System.out.println("절대 경로: " + absolutePath);
            FileInputStream fis = new FileInputStream(absolutePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            player.play();
            System.out.println("이전곡재생완료");

            model.addAttribute("title", fileTitle);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return fileTitle;
    }

    @GetMapping("/pause")
    public void pauseMusic() {


    }

    @GetMapping("/stop")
    public void stopMusic() {
        if(player!= null)
            player.close();
        System.out.println("멈춤");


    }

    @GetMapping("/time")
    public int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }
}