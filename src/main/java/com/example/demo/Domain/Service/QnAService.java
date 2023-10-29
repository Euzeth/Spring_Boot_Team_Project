package com.example.demo.Domain.Service;

import com.example.demo.Controller.QnAController;
import com.example.demo.Domain.Dto.Criteria;
import com.example.demo.Domain.Dto.PageDto;
import com.example.demo.Domain.Dto.QnADto;
import com.example.demo.Domain.Dto.ReplyDto;
import com.example.demo.Domain.Entity.QnA;
import com.example.demo.Domain.Entity.Reply;
import com.example.demo.Domain.Repository.QnARepository;
import com.example.demo.Domain.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class QnAService {
    private String uploadDir = "c:\\upload";

    @Autowired
    private QnARepository qnaRepository;

    @Autowired
    private ReplyRepository replyRepository;


    //모든 게시물 가져오기
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> GetQnAList(Criteria criteria) {


        Map<String,Object> returns = new HashMap<String,Object>();


        //전체게시물 건수 받기(type,Keyword가 적용된 count로 변경
        //int totalcount = (int)qnaRepository.count();
        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        int totalcount=0;
        if(criteria!=null&& criteria.getType()!=null) {
            if (criteria.getType().equals("title"))
                totalcount = qnaRepository.countWhereTitleKeyword(criteria.getKeyword());
            else if (criteria.getType().equals("username"))
                totalcount = qnaRepository.countWhereUsernameKeyword(criteria.getKeyword());
            else if (criteria.getType().equals("content"))
                totalcount = qnaRepository.countWhereContentKeyword(criteria.getKeyword());
        }
        else
            totalcount = (int)qnaRepository.count();


        System.out.println("COUNT  :" + totalcount);

        //PageDto 만들기
        PageDto pagedto = new PageDto(totalcount,criteria);

        //시작 게시물 번호 구하기(수정) - OFFSET
        int offset =(criteria.getPageno()-1) * criteria.getAmount();    //1page = 0, 2page = 10

        //--------------------------------------------------------
        //SEARCH
        //--------------------------------------------------------
        List<QnA> list = null;
        if(criteria!=null&& criteria.getType()!=null) {
            if (criteria.getType().equals("title")) {
                list = qnaRepository.findQnATitleAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
                System.out.println("TITLE SEARCH!");
                System.out.println(list);
            } else if (criteria.getType().equals("username"))
                list = qnaRepository.findQnAUsernameAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
            else if (criteria.getType().equals("content"))
                list = qnaRepository.findQnAContentsAmountStart(criteria.getKeyword(), pagedto.getCriteria().getAmount(), offset);
            else if (criteria.getType().equals("none"))
                list = qnaRepository.findQnAAmountStart(pagedto.getCriteria().getAmount(), offset);
        }
        else
            list  =  qnaRepository.findQnAAmountStart(pagedto.getCriteria().getAmount(),offset);


        returns.put("list",list);
        returns.put("pageDto",pagedto);

        return returns;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean addQnA(QnADto dto) throws IOException {

        //--------------------------------
        System.out.println("upload File Count : " +dto.getFiles().length);


        QnA qna = new QnA();
        qna.setTitle(dto.getTitle());
        qna.setContent(dto.getContent());
        qna.setRegdate(LocalDate.now());
        qna.setUsername(dto.getUsername());
        qna.setCount(0L);

        MultipartFile[] files = dto.getFiles();
//        System.out.println("FILES's LENGTH : " +files.length);
//        System.out.println("FILES's SIZE : "  + files[0].getSize());

        List<String> filenames = new ArrayList<String>();
        List<String> filesizes = new ArrayList<String>();


        if(dto.getFiles().length >= 1 && dto.getFiles()[0].getSize()!=0L)
        {
            //Upload Dir 미존재시 생성
            String path = uploadDir+ File.separator+dto.getUsername()+File.separator+ UUID.randomUUID();
            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            //qna에 경로 추가
            qna.setDirpath(dir.toString());


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
            qna.setFilename(filenames.toString());
            qna.setFilesize(filesizes.toString());
        }


        qna =    qnaRepository.save(qna);

        boolean issaved =  qnaRepository.existsById(qna.getNo());

        return issaved;
    }

    @Transactional(rollbackFor = Exception.class)

    public QnA getQnAOne(Long no) {

        Optional<QnA> qna =    qnaRepository.findById(no);
        if(qna.isEmpty())
            return null;
        else
            return qna.get();
    }

    //----------------------------------------------------------------
    //수정하기코드!
    //----------------------------------------------------------------
    @Transactional(rollbackFor = SQLException.class)
    public boolean updateQnA(QnADto dto) throws IOException {

        //--------------------------------
        System.out.println("upload File Count : " +dto.getFiles().length);
        System.out.println("QnAController.READ_DIRECTORY_PATH : " + QnAController.READ_DIRECTORY_PATH);


        QnA qna = new QnA();
        qna.setTitle(dto.getTitle());
        qna.setContent(dto.getContent());
        qna.setRegdate(LocalDate.now());
        qna.setUsername(dto.getUsername());
        qna.setCount(dto.getCount());

        MultipartFile [] files = dto.getFiles();
//        System.out.println("FILES's LENGTH : " +files.length);
//        System.out.println("FILES's SIZE : "  + files[0].getSize());

        List<String> filenames = new ArrayList<String>();
        List<String> filesizes = new ArrayList<String>();

        //기존 정보 가져오기
        QnA oldQnA =  qnaRepository.findById(dto.getNo()).get();

        if(dto.getFiles().length >= 1 && dto.getFiles()[0].getSize()!=0L)
        {
            //--------------------------------
            //Upload Dir 미존재시 생성[추가]
            //--------------------------------
            if(QnAController.READ_DIRECTORY_PATH==null){
                String path = uploadDir+ File.separator+dto.getUsername()+File.separator+ UUID.randomUUID();
                File dir = new File(path);
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                QnAController.READ_DIRECTORY_PATH = path;
            }
            qna.setDirpath(QnAController.READ_DIRECTORY_PATH.toString());
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
                File fileobj = new File(QnAController.READ_DIRECTORY_PATH,filename);
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
            if(oldQnA.getFilename()!=null) {
                String oldFilenames = oldQnA.getFilename();
                String oldFilesizes = oldQnA.getFilesize();
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

                qna.setFilename(Arrays.toString(newFilenames));
                qna.setFilesize(Arrays.toString(newFilesizes));
            }
            else {
                qna.setDirpath(qna.getDirpath());
                qna.setFilename(filenames.toString());
                qna.setFilesize(filesizes.toString());
            }


        }
        else {
            qna.setDirpath(oldQnA.getDirpath());
            qna.setFilename(oldQnA.getFilename());
            qna.setFilesize(oldQnA.getFilesize());
        }
        qna.setNo(oldQnA.getNo());

        qna =  qnaRepository.save(qna);


        return qna!=null;

    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean removeFile(String no,String filename){

        Long num = Long.parseLong(no);
        filename = filename.trim();
        //------------------------------------------------
        //파일삭제도 하고
        //------------------------------------------------
        File file = new File(QnAController.READ_DIRECTORY_PATH,filename);
        if(!file.exists()){
            System.out.println("없음.. " + QnAController.READ_DIRECTORY_PATH);
            return false;
        }

        //------------------------------------------------
        //DB도 지우고
        //------------------------------------------------
        QnA readQnA = qnaRepository.findById(num).get();
        String fn []=  readQnA.getFilename().split(",");
        String fs []=  readQnA.getFilesize().split(",");

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

        readQnA.setFilename(newFn.toString());
        readQnA.setFilesize(newFn.toString());

        if(readQnA.getFilename().equals("[]")){
            readQnA.setFilename(null);
            readQnA.setFilesize(null);
            readQnA.setDirpath(null);
        }

        readQnA = qnaRepository.save(readQnA);


        return file.delete();


    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean removeQnA(Long no) {

        QnA qna = qnaRepository.findById(no).get();
        String removePath = QnAController.READ_DIRECTORY_PATH;

        //파일 있으면삭제
        if (qna.getDirpath() != null) {
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
        qnaRepository.delete(qna);

        return qnaRepository.existsById(no);
    }

    //----------------------------------------------------------------
    // COUNT
    //----------------------------------------------------------------
    @Transactional(rollbackFor = SQLException.class)
    public void countUp(QnA qna) {
        qna.setCount(qna.getCount()+1L);
        qnaRepository.save(qna);
    }

    //ADD REPLY
    public void addReply(Long qno, String contents) {
        Reply reply = new Reply();
        QnA qna = new QnA();
        qna.setNo(qno);

        reply.setRno(null);
        reply.setQna(qna);
        reply.setContent(contents);
        reply.setRegdate(LocalDateTime.now());

        replyRepository.save(reply);


    }

    public List<ReplyDto> getReplyList(Long qno) {
        List<Reply> replyList =  replyRepository.GetReplyByQnoDesc(qno);

        List<ReplyDto> returnReply  = new ArrayList();
        ReplyDto dto = null;

        if(!replyList.isEmpty()) {
            for(int i=0;i<replyList.size();i++) {

                dto = new ReplyDto();
                dto.setQno(replyList.get(i).getQna().getNo());
                dto.setRno(replyList.get(i).getRno());
                dto.setContent(replyList.get(i).getContent());
                dto.setRegdate(replyList.get(i).getRegdate());

                returnReply.add(dto);

            }
            return returnReply;
        }

        return null;
    }

    public Long getReplyCount(Long qno) {
        return replyRepository.GetReplyCountByQnoDesc(qno);

    }

    public void deleteReply(Long rno) {
        replyRepository.deleteById(rno);
    }

}