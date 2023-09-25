package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    @Query(value = "SELECT * FROM musicdb.notice ORDER BY no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Notice> findNoticeAmountStart(@Param("amount") int amount, @Param("offset") int offset);


    @Query(value = "SELECT * FROM musicdb.notice n WHERE n.title LIKE %:keyWord%  ORDER BY n.no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Notice> findNoticeTitleAmountStart(@Param("keyWord")String keyWord, @Param("amount") int amount,@Param("offset") int offset);

    // Type , Keyword 로 필터링된 count 계산
    @Query("SELECT COUNT(n) FROM Notice n WHERE n.title LIKE %:keyWord%")
    Integer countWhereTitleKeyword(@Param("keyWord")String keyWord);

}