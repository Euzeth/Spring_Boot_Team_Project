package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.QnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnARepository extends JpaRepository<QnA,Long> {

    @Query(value = "SELECT * FROM musicdb.qna ORDER BY no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<QnA> findQnAAmountStart(@Param("amount") int amount, @Param("offset") int offset);

    // Type , Keyword 로 필터링된 count 계산
    @Query("SELECT COUNT(q) FROM QnA q WHERE q.title LIKE %:keyWord%")
    Integer countWhereTitleKeyword(@Param("keyWord")String keyWord);

    @Query("SELECT COUNT(q) FROM QnA q WHERE q.username LIKE %:keyWord%")
    Integer countWhereUsernameKeyword(@Param("keyWord")String keyWord);

    @Query("SELECT COUNT(q) FROM QnA q WHERE q.content LIKE %:keyWord%")
    Integer countWhereContentKeyword(@Param("keyWord")String keyWord);

    @Query(value = "SELECT * FROM musicdb.qna q WHERE q.title LIKE %:keyWord%  ORDER BY q.no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<QnA> findQnATitleAmountStart(@Param("keyWord")String keyWord, @Param("amount") int amount, @Param("offset") int offset);

    @Query(value = "SELECT * FROM musicdb.qna q WHERE q.username LIKE %:keyWord%  ORDER BY q.no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<QnA> findQnAUsernameAmountStart(@Param("keyWord")String keyWord, @Param("amount") int amount, @Param("offset") int offset);

    @Query(value = "SELECT * FROM musicdb.qna q WHERE q.content LIKE %:keyWord%  ORDER BY q.no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<QnA> findQnAContentsAmountStart(@Param("keyWord")String keyWord, @Param("amount") int amount, @Param("offset") int offset);



}