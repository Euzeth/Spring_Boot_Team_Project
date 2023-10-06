package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Dto.MembershipDto;
import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Entity.QnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music,Integer> {
    @Query(value = "SELECT * FROM musicdb.music m WHERE m.title LIKE %:keyWord%  ORDER BY m.music_code ASC" ,nativeQuery = true)
    List<Music> findSearchMusicTitle(@Param("keyWord")String keyWord);

    @Query(value = "SELECT * FROM musicdb.music m WHERE m.artist LIKE %:keyWord%  ORDER BY m.music_code ASC" ,nativeQuery = true)
    List<Music> findSearchMusicArtist(@Param("keyWord")String keyWord);

    // Type , Keyword 로 필터링된 count 계산
    @Query("SELECT COUNT(m) FROM Music m WHERE m.title LIKE %:keyWord%")
    Integer countWhereTitleKeyword(@Param("keyWord")String keyWord);

    @Query("SELECT COUNT(m) FROM Music m WHERE m.artist LIKE %:keyWord%")
    Integer countWhereArtistKeyword(@Param("keyWord")String keyWord);


}
