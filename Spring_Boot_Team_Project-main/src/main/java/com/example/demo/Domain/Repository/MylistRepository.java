package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Mylist;
import com.example.demo.Domain.Entity.MylistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MylistRepository extends JpaRepository<Mylist, MylistId> {
    @Query(value = "SELECT l FROM Mylist l WHERE lusername = :lusername ORDER BY lmusic_code ASC")
    List<Mylist> GetMylistByUsernameAsc(@Param("lusername") String lusername);

}