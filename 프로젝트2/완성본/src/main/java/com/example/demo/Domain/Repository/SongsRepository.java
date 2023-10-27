package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SongsRepository extends JpaRepository<Music, String> {

    @Query(value = "SELECT * FROM tbl_music WHERE title = :title", nativeQuery = true)
    Music findByTitle(@Param("title") String title);

}
