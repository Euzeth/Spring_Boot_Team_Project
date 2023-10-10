package com.example.demo.Domain.Repository;


import com.example.demo.Domain.Entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE qno = :qno ORDER BY rno DESC")
    List<Reply> GetReplyByBnoDesc(@Param("qno") Long qno);

    @Query("SELECT COUNT(r) FROM Reply r WHERE qno = :qno")
    Long GetReplyCountByBnoDesc(@Param("qno") Long qno);

}
