package com.example.demo.Domain.Service;

import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


@Service
@Slf4j
public class MusicService {
    @Autowired
    MusicRepository musicRepository;

    @Transactional(rollbackFor = SQLException.class)
    public List<Music> getTitleSearchList(String title) {
        List<Music> titleSearchList = musicRepository.findSearchMusicTitle(title);


        return titleSearchList;
    }

    @Transactional(rollbackFor = SQLException.class)
    public List<Music> getArtistSearchList(String artist) {
        List<Music> artistSearchList = musicRepository.findSearchMusicArtist(artist);


        return artistSearchList;
    }
}
