package com.example.demo.RestController;

import com.example.demo.Controller.NoticeController;
import com.example.demo.Domain.Service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeRestController {


    @Autowired
    private NoticeService noticeService;

    //-------------------
    // DOWNLOAD
    //-------------------
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadfile(String filename) throws UnsupportedEncodingException {
        filename = filename.trim();
        System.out.println("GET /notice/download filename : " + filename);
        String path  = NoticeController.READ_NOTICE_DIR_PATH + File.separator + filename;
        log.info("RestController_03Download's Download Call.." + path);
        System.out.println("GET /notice/download path : " + path);
        //FileSystemResource : 파일시스템의 특정 파일로부터 정보를 가져오는데 사용
        Resource resource = new FileSystemResource(path);
        //파일명 추출
        filename = resource.getFilename();
        //헤더 정보 추가
        HttpHeaders headers = new HttpHeaders();
        //ISO-8859-1 : 라틴어(특수문자등 깨짐 방지)
        headers.add("Content-Disposition","attachment; filename="+new String(filename.getBytes("UTF-8"),"ISO-8859-1"));
        //리소스,파일정보가 포함된 헤더,상태정보를 전달
        return new ResponseEntity<Resource>(resource,headers, HttpStatus.OK);
    }





    //-------------------
    // 수정하기
    //-------------------
    @PutMapping("/put/{no}/{filename}")
    public void put(@PathVariable String no, @PathVariable String filename){
        log.info("PUT /notice/put " + no + " " + filename);
        boolean isremoved =  noticeService.removeFile(no, filename);


    }


    //-------------------
    // 삭제하기
    //-------------------
    @DeleteMapping("/delete")
    public String delete(Long no){
        log.info("DELETE /notice/delete no " + no);

        boolean isremoved =  !noticeService.removeNotice(no);
        if(isremoved)
            return "success";
        else
            return "failed";

    }

}