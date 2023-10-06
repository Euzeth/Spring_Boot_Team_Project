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

//    @Transactional(rollbackFor = SQLException.class)
//    public List<Music> getTitleSearchList(MusicDto dto) {
//        int totalcount=0;
//        if(dto!=null&& dto.getType()!=null) {
//            if (dto.getType().equals("title"))
//                totalcount = musicRepository.countWhereTitleKeyword(dto.getSearchText());
//        }
//        else
//            totalcount = (int)musicRepository.count();
//
//
//        List<Music> titleSearchList = musicRepository.findSearchMusicTitle(dto.getTitle());
//
//
//        return titleSearchList;
//    }
//
//    @Transactional(rollbackFor = SQLException.class)
//    public List<Music> getArtistSearchList(MusicDto dto) {
//        int totalcount=0;
//        if(dto!=null&& dto.getType()!=null) {
//            if (dto.getType().equals("artist"))
//                totalcount = musicRepository.countWhereArtistKeyword(dto.getSearchText());
//        }
//        else
//            totalcount = (int)musicRepository.count();
//        List<Music> artistSearchList = musicRepository.findSearchMusicArtist(dto.getArtist());
//
//
//        return artistSearchList;
//    }

    @Transactional(rollbackFor = SQLException.class)
    public Map<String,Object> GetSearchList(MusicDto dto) {

        Map<String,Object> returns = new HashMap<String,Object>();

        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        int totalcount=0;
        if(dto!=null&& dto.getType()!=null) {
            if (dto.getType().equals("title"))
                totalcount = musicRepository.countWhereTitleKeyword(dto.getSearchText());
            else if (dto.getType().equals("artist"))
                totalcount = musicRepository.countWhereArtistKeyword(dto.getSearchText());
        }
        else
            totalcount = (int)musicRepository.count();

        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        List<Music> list = null;
        if(dto!=null&& dto.getType()!=null) {
            if (dto.getType().equals("title")) {
                list = musicRepository.findSearchMusicTitle(dto.getSearchText());
                System.out.println(dto.getSearchText());
                System.out.println("TITLE SEARCH!");
                System.out.println(list);
            }
            else if (dto.getType().equals("artist"))
                list = musicRepository.findSearchMusicArtist(dto.getSearchText());
        }


        returns.put("list",list);

        return returns;

    }


}
