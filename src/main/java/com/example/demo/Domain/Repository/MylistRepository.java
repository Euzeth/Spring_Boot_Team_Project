package com.example.demo.Domain.Repository;

import com.example.demo.Domain.Entity.Mylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MylistRepository extends JpaRepository<Mylist, String> {
    @Query(value = "SELECT l FROM Mylist l WHERE lusername = :lusername ORDER BY lmusic_code ASC")
    List<Mylist> GetMylistByUsernameAsc(@Param("lusername") String lusername);

}
