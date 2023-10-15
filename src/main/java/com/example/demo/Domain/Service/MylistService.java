package com.example.demo.Domain.Service;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Dto.MylistDto;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Entity.Mylist;
import com.example.demo.Domain.Entity.MylistId;
import com.example.demo.Domain.Repository.MylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MylistService {

    @Autowired
    MylistRepository mylistRepository;

    @Transactional(rollbackFor = SQLException.class)
    public void addMylist(List<Long> musicCodes, String lusername){
        for (Long lmusic_code : musicCodes) {
            Mylist mylist = new Mylist();
            mylist.setMylistId(lusername, lmusic_code);
            mylistRepository.save(mylist);
        }
    }

    @Transactional(rollbackFor = SQLException.class)
    public List<Mylist> GetMylistByUsername(String lusername){
        return mylistRepository.GetMylistByUsernameAsc(lusername);
    }

    @Transactional(rollbackFor = SQLException.class)
    public void removeMylist(MylistId mylistId){
        Optional<Mylist> mylistOptional = mylistRepository.findById(mylistId);
        System.out.println(mylistOptional);

        if (mylistOptional.isPresent()) {
            Mylist mylist = mylistOptional.get();
            mylistRepository.delete(mylist);
        }

    }

}
