package com.example.demo.Domain.Service;

import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MusicService {
    @Autowired
    MusicRepository musicRepository;

    @Transactional(rollbackFor = SQLException.class)
    public MusicDto connect(MusicDto dto) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/musicdb";
        String username = "root";
        String password = "1234";

        String filePath = null;
        String fileTitle = null;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "SELECT * FROM music LIMIT 1 OFFSET 1";
            System.out.println(sql);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    fileTitle = resultSet.getString("title");
                    filePath = resultSet.getString("music_path");

                    dto.setTitle(fileTitle);
                    dto.setMusic_path(filePath);
                    System.out.println("현재 File Title: " + fileTitle);
                    System.out.println("현재 File Path: " + filePath);

                    return dto;

                } else {
                    System.out.println("파일을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Transactional(rollbackFor = SQLException.class)
    public MusicDto connectNext(MusicDto dto) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/musicdb";
        String username = "root";
        String password = "1234";

        String filePath = null;
        String fileTitle = null;

        MusicDto musicDto = connect(dto);
        String connectfile = dto.getTitle();
        System.out.println("connectfile : " + connectfile);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "SELECT * FROM music WHERE title > ? ORDER BY title ASC LIMIT 1";
            System.out.println("sql : " + sql);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, connectfile);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    fileTitle = resultSet.getString("title");
                    filePath = resultSet.getString("music_path");

                    dto.setTitle(fileTitle);
                    dto.setMusic_path(filePath);
                    System.out.println("Next File Title: " + fileTitle);
                    System.out.println("Next File Path: " + filePath);

                    return dto;

                } else {
                    System.out.println("파일을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Transactional(rollbackFor = SQLException.class)
    public MusicDto connectPrevious(MusicDto dto) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/musicdb";
        String username = "root";
        String password = "1234";

        String filePath = null;
        String fileTitle = null;

        MusicDto musicDto = connect(dto);
        String connectfile = dto.getTitle();
        System.out.println("connectfile : " + connectfile);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "SELECT * FROM music WHERE title < ? ORDER BY title DESC LIMIT 1";
            System.out.println("sql : " + sql);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, connectfile);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    fileTitle = resultSet.getString("title");
                    filePath = resultSet.getString("music_path");

                    dto.setTitle(fileTitle);
                    dto.setMusic_path(filePath);
                    System.out.println("Before File Title: " + fileTitle);
                    System.out.println("Before File Path: " + filePath);

                    return dto;

                } else {
                    System.out.println("파일을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

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

    @Transactional(rollbackFor = SQLException.class)
    public void likeMusic(Long musicId) {
        Music music =  musicRepository.findById(musicId).get();
        music.setMlike(music.getMlike()+1L);
        musicRepository.save(music);
    }

}