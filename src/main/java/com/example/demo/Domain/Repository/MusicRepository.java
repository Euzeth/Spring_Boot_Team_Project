package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music,Long> {
    @Query(value = "SELECT * FROM musicdb.music m WHERE m.title LIKE %:keyWord%  ORDER BY m.music_code ASC" ,nativeQuery = true)
    List<Music> findSearchMusicTitle(@Param("keyWord")String keyWord);

    @Query(value = "SELECT * FROM musicdb.music m WHERE m.artist LIKE %:keyWord%  ORDER BY m.music_code ASC" ,nativeQuery = true)
    List<Music> findSearchMusicArtist(@Param("keyWord")String keyWord);

    @Query(value = "SELECT *, RANK() OVER (ORDER BY m.count DESC) AS ranking FROM musicdb.music m ORDER BY m.count DESC LIMIT 100",nativeQuery = true)
    List<Music> findTop100();

    @Query(value = "SELECT * FROM musicdb.music m WHERE music_code = :music_code",nativeQuery = true)
    Music findByMusicCode(@Param("music_code")Long music_code);
}