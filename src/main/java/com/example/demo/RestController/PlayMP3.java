package com.example.demo.RestController;


import javazoom.jl.player.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

//@RestController
//public class PlayMP3 extends Thread{
//
//
//    private Player player;
//    private boolean isLoop;
//    private File file;
//    private FileInputStream fis;
//    private BufferedInputStream bis;
//
//    @GetMapping
//    public void PlayMP3(String name, boolean isLoop){
//        try{
//            this.isLoop = isLoop;
//            file = new File(Main.class.getResource("../music/"+name).toURI());
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public int getTime(){
//        if(player == null)
//            return 0;
//        return player.getPosition();
//    }
//    public void close(){
//        isLoop = false;
//        player.close();
//        this.interrupt();
//    }
//
//    @Override
//    public void run(){
//        try{
//            do{
//                player.play();
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                player = new Player(bis);
//            }while(isLoop);
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//}
@RestController
public class PlayMP3 {
	private Player player;
	private boolean isLoop;
	private String currentMusicName;
	private FileInputStream finalFis;
	private BufferedInputStream finalBis;

	@GetMapping("/play")
	public void playMusic(@RequestParam String name, @RequestParam String isLoop){
		try {
			if (player != null) {
				player.close();
			}
			this.isLoop = Boolean.parseBoolean(isLoop);;
//            currentMusicName = name;

//            String basePath = "C:\\Users\\Administrator\\Desktop\\노래\\";
//            String filePath = basePath + currentMusicName;
//            FileInputStream fis = new FileInputStream(filePath);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            player = new Player(bis);

			String filePath = "C:\\08SPING_BOOT-main\\프로젝트\\프로젝트\\노래\\2BIC(투빅)-01-그대와 나만-320k.mp3";

			// File 객체를 생성하여 절대 경로를 얻습니다.
			File file = new File(filePath);
			String absolutePath = file.getAbsolutePath();

			// 절대 경로 출력
			System.out.println("절대 경로: " + absolutePath);
			FileInputStream fis = new FileInputStream(absolutePath);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
			System.out.println("재생완료");


			Thread playThread = new Thread(() -> {
				try {
					do {
						player.play();

						String basePath2 = "C:\\Users\\Administrator\\Desktop\\노래\\";
						String filePath2= basePath2 + currentMusicName;
						finalFis = new FileInputStream(filePath2);
						finalBis = new BufferedInputStream(finalFis);
						player = new Player(finalBis);
					} while (Boolean.parseBoolean(isLoop));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			});
			playThread.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@GetMapping("/pause")
	public void pauseMusic() {

	}

	@GetMapping("/stop")
	public void stopMusic() {
		// 음악을 정지하고 player 객체를 닫는 코드 작성
	}

	@GetMapping("/time")
	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}
}