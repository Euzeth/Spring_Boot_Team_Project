package com.example.demo.Domain.Service;

import com.example.demo.Controller.NoticeController;
import com.example.demo.Domain.Dto.Criteria;
import com.example.demo.Domain.Dto.NoticeDto;
import com.example.demo.Domain.Dto.PageDto;
import com.example.demo.Domain.Entity.Notice;
import com.example.demo.Domain.Repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
public class NoticeService {

    private String uploadDir = "c:\\upload";

    @Autowired
    private NoticeRepository noticeRepository;


    @Transactional(rollbackFor = SQLException.class)
    public Map<String,Object> GetNoticeList(Criteria criteria) {

        Map<String,Object> returns = new HashMap<String,Object>();


        //전체게시물 건수 받기(type,Keyword가 적용된 count로 변경
        //int totalcount = (int)noticeRepository.count();
        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        int totalcount=0;
        if(criteria!=null&& criteria.getType()!=null) {
            if (criteria.getType().equals("title"))
                totalcount = noticeRepository.countWhereTitleKeyword(criteria.getKeyword());
        }
        else
            totalcount = (int)noticeRepository.count();



        //PageDto 만들기
        PageDto pagedto = new PageDto(totalcount,criteria);

        //시작 게시물 번호 구하기(수정) - OFFSET
        int offset =(criteria.getPageno()-1) * criteria.getAmount();    //1page = 0, 2page = 10

        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        List<Notice> list = null;
        if(criteria!=null&& criteria.getType()!=null) {
            if (criteria.getType().equals("title")) {
                list = noticeRepository.findNoticeTitleAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
                System.out.println("TITLE SEARCH!");
                System.out.println(list);
            }
            else if (criteria.getType().equals("none"))
                list = noticeRepository.findNoticeAmountStart(pagedto.getCriteria().getAmount(), offset);
        }
        else
            list  =  noticeRepository.findNoticeAmountStart(pagedto.getCriteria().getAmount(),offset);


        returns.put("list",list);
        returns.put("pageDto",pagedto);

        return returns;

    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean addNotice(NoticeDto dto) throws IOException {

        Notice notice = new Notice();
        notice.setCategory(dto.getCategory());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setRegdate(LocalDate.now());
        notice.setCount(0L);

        MultipartFile[] files = dto.getFiles();
//        System.out.println("FILES's LENGTH : " +files.length);
        System.out.println("FILES's SIZE : "  + files[0].getSize());
        List<String> filenames = new ArrayList<String>();
        List<String> filesizes = new ArrayList<String>();


        //파일업로드
        if(dto.getFiles().length >= 1 && dto.getFiles()[0].getSize()!=0L)
        {
            //Upload Dir 미존재시 생성
            String path = uploadDir+ File.separator+"notice"+File.separator+ UUID.randomUUID();
            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            for(MultipartFile file  : dto.getFiles())
            {
                System.out.println("--------------------");
                System.out.println("FILE NAME : " + file.getOriginalFilename());
                System.out.println("FILE SIZE : " + file.getSize() + " Byte");
                System.out.println("--------------------");

                //파일명 추출
                String filename = file.getOriginalFilename();
                //파일객체 생성
                File fileobj = new File(path,filename);
                //업로드
                file.transferTo(fileobj);
                //filenames 저장
                filenames.add(filename);
                filesizes.add(file.getSize()+"");
            }

            notice.setDirpath(path);

            notice.setFilename(filenames.toString());
            notice.setFilesize(filesizes.toString());

        }


        notice =    noticeRepository.save(notice);

        boolean issaved =  noticeRepository.existsById(notice.getNo());
        return issaved;
    }
    @Transactional(rollbackFor = SQLException.class)
    public Notice getNoticeOne(Long no) {

        Optional<Notice> notice =    noticeRepository.findById(no);
        if(notice.isEmpty())
            return null;
        else
            return notice.get();
    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean removeFile(String no, String filename) {

        Long num = Long.parseLong(no);
        filename = filename.trim();

        //------------------------------------------------
        //파일삭제도 하고
        //------------------------------------------------
        File file = new File(NoticeController.READ_NOTICE_DIR_PATH ,filename);
        if(!file.exists()){
            System.out.println("없음.. " + NoticeController.READ_NOTICE_DIR_PATH);
            return false;
        }

        //------------------------------------------------
        //DB도 지우고
        //------------------------------------------------
        Notice readNotice = noticeRepository.findById(num).get();
        String fn []=  readNotice.getFilename().split(",");
        String fs []=  readNotice.getFilesize().split(",");

        //첫문자열에 '[' 마지막 ']' 제거
        fn[0] = fn[0].substring(1, fn[0].length());
        int lastIdx = fn.length-1;
        fn[lastIdx] = fn[lastIdx].substring(0,fn[lastIdx].lastIndexOf("]"));

        fs[0] = fs[0].substring(1, fs[0].length());
        int lastIdx2 = fs.length-1;
        fs[lastIdx2] = fs[lastIdx2].substring(0,fs[lastIdx2].lastIndexOf("]"));

        List<String> newFn  = new ArrayList();
        List<String> newFs  = new ArrayList();

        for(int i=0;i<fn.length;i++)
        {
            if(!StringUtils.contains(fn[i],filename)){
                System.out.println("보존 FILENAME : " + fn[i]);
                newFn.add(fn[i]);
                newFs.add(fs[i]);
            }


        }

        readNotice.setFilename(newFn.toString());
        readNotice.setFilesize(newFs.toString());

        if(readNotice.getFilename().equals("[]")){
            readNotice.setFilename(null);
            readNotice.setFilesize(null);
            readNotice.setDirpath(null);
        }

        readNotice = noticeRepository.save(readNotice);


        return file.delete();
    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean updateNotice(NoticeDto dto) throws IOException {

        //--------------------------------
        System.out.println("upload File Count : " +dto.getFiles().length);
        System.out.println("NoticeController.READ_DIRECTORY_PATH : " +NoticeController.READ_NOTICE_DIR_PATH);


        Notice notice = new Notice();
        notice.setCategory(dto.getCategory());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setRegdate(LocalDate.now());
        notice.setCount(dto.getCount());

        MultipartFile [] files = dto.getFiles();
//        System.out.println("FILES's LENGTH : " +files.length);
//        System.out.println("FILES's SIZE : "  + files[0].getSize());

        List<String> filenames = new ArrayList<String>();
        List<String> filesizes = new ArrayList<String>();

        //기존 정보 가져오기
        Notice oldNotice =  noticeRepository.findById(dto.getNo()).get();

        if(dto.getFiles().length >= 1 && dto.getFiles()[0].getSize()!=0L)
        {
            //--------------------------------
            //Upload Dir 미존재시 생성[추가]
            //--------------------------------
            if(NoticeController.READ_NOTICE_DIR_PATH==null){
                String path = uploadDir+ File.separator+"notice"+File.separator+ UUID.randomUUID();
                File dir = new File(path);
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                NoticeController.READ_NOTICE_DIR_PATH = path;
            }
            notice.setDirpath(NoticeController.READ_NOTICE_DIR_PATH.toString());
            //--------------------------------


            for(MultipartFile file  : dto.getFiles())
            {
                System.out.println("--------------------");
                System.out.println("FILE NAME : " + file.getOriginalFilename());
                System.out.println("FILE SIZE : " + file.getSize() + " Byte");
                System.out.println("--------------------");

                //파일명 추출
                String filename = file.getOriginalFilename();
                //파일객체 생성
                File fileobj = new File(NoticeController.READ_NOTICE_DIR_PATH,filename);
                //업로드
                file.transferTo(fileobj);

                //filenames 저장
                filenames.add(filename);
                filesizes.add(file.getSize()+"");
            }

            //--------------------
            //기존 파일 정보와 병합
            //--------------------
            //----------------------------------------------------------------
            //코드 수정
            //----------------------------------------------------------------
            if(oldNotice.getFilename()!=null) {
                String oldFilenames = oldNotice.getFilename();
                String oldFilesizes = oldNotice.getFilesize();
                String[] old_fn_arr = oldFilenames.split(",");
                String[] old_fs_arr = oldFilesizes.split(",");

                //첫문자열에 '[' 마지막 ']' 제거
                old_fn_arr[0] = old_fn_arr[0].substring(1, old_fn_arr[0].length());
                int lastIdx = old_fn_arr.length - 1;
                old_fn_arr[lastIdx] = old_fn_arr[lastIdx].substring(0, old_fn_arr[lastIdx].lastIndexOf("]"));

                old_fs_arr[0] = old_fs_arr[0].substring(1, old_fs_arr[0].length());
                int lastIdx2 = old_fs_arr.length - 1;
                old_fs_arr[lastIdx2] = old_fs_arr[lastIdx2].substring(0, old_fs_arr[lastIdx2].lastIndexOf("]"));


                String newFilenames[] = Stream.concat(Arrays.stream(old_fn_arr), Arrays.stream(filenames.toArray())).toArray(String[]::new);
                String newFilesizes[] = Stream.concat(Arrays.stream(old_fs_arr), Arrays.stream(filesizes.toArray())).toArray(String[]::new);
                System.out.println("newFilenames : " + (Arrays.toString(newFilenames)));
                System.out.println("newFilesizes : " + (Arrays.toString(newFilesizes)));

                notice.setFilename(Arrays.toString(newFilenames));
                notice.setFilesize(Arrays.toString(newFilesizes));
            }
            else {
                notice.setDirpath(notice.getDirpath());
                notice.setFilename(filenames.toString());
                notice.setFilesize(filesizes.toString());
            }


        }
        else {
            notice.setDirpath(oldNotice.getDirpath());
            notice.setFilename(oldNotice.getFilename());
            notice.setFilesize(oldNotice.getFilesize());
        }
        notice.setNo(oldNotice.getNo());

        notice =  noticeRepository.save(notice);


        return notice!=null;
    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean removeNotice(Long no) {
        Notice notice = noticeRepository.findById(no).get();
        String removePath = NoticeController.READ_NOTICE_DIR_PATH;

        //파일 있으면삭제
        if (notice.getDirpath() != null) {
            File dir = new File(removePath);
            if (dir.exists()) {
                File files[] = dir.listFiles();
                for (File file : files) {
                    file.delete();
                }
                dir.delete();
            }
        }
        //DB 삭제
        noticeRepository.delete(notice);

        return noticeRepository.existsById(no);
    }




    // COUNT UP
    @Transactional(rollbackFor = SQLException.class)
    public void countUp(Notice notice) {
        notice.setCount(notice.getCount()+1L);
        noticeRepository.save(notice);
    }
}