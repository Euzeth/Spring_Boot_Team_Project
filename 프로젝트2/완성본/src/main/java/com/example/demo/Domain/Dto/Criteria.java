package com.example.demo.Domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Criteria {
    private int pageno;	 			//현재 페이지
    private int amount;	 			//표시할 게시물 양(10건)
    private String type;			//	제목,작성자,게시물번호
    private String keyword;			//	포함문자열


    public Criteria() {
        pageno=1;
        amount=10;
    }
    public Criteria(int no,int amt) {
        pageno = no;
        amount =amt;
    }


}