package com.example.demo.Domain.Service;

import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MusicService {
    @Autowired
    MusicRepository musicRepository;

    @Transactional(rollbackFor = SQLException.class)
    public Map<String,Object> GetSearchList(MusicDto dto) {

        Map<String,Object> returns = new HashMap<String,Object>();
        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        List<Music> list = null;
        if(dto!=null&& dto.getType()!=null) {
            if (dto.getType().equals("title")) {
                list = musicRepository.findSearchMusicTitle(dto.getSearchText());
                System.out.println(dto.getSearchText());

            }
            else if (dto.getType().equals("artist")) {
                list = musicRepository.findSearchMusicArtist(dto.getSearchText());
                System.out.println(dto.getSearchText());
            }
        }

        returns.put("list",list);

        return returns;

    }

//    @Transactional(rollbackFor = SQLException.class)
//    public Music getMusicOne(int musicId) {
//
//        Optional<Music> music = musicRepository.findById(musicId);
//        if(music.isEmpty())
//            return null;
//        else
//            return music.get();
//    }




    public void likeMusic(Long musicId) {
        Music music =  musicRepository.findById(musicId).get();
        music.setMlike(music.getMlike()+1L);
        musicRepository.save(music);
    }

}