package com.example.demo.Domain.Service;

import java.util.List;

import com.example.demo.Domain.Dto.MemberDto;
import com.example.demo.Domain.Entity.Member;
import com.example.demo.Domain.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class MemberService{

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;
	
	public List<MemberDto> getAllMember(){
		return null;
	}
	
	public MemberDto searchMember(String id){
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean addMember(MemberDto dto, Model model, HttpServletRequest request) {
		if(!dto.getPassword().equals(dto.getRepassword()))
		{
			model.addAttribute("repassword", "패스워드가 일치하지 않습니다.");
			return false;
		}

		//Email 인증여부 확인
		HttpSession session = request.getSession();
		Boolean is_email_auth = (Boolean)session.getAttribute("is_email_auth");
		if(is_email_auth != null)
		{
			if(is_email_auth)   // 코드 인증 확인 -> 회원가입 진행
			{
				dto.setRole("ROLE_USER");
				dto.setPassword( passwordEncoder.encode(dto.getPassword()) );

				Member member = MemberDto.dtoToEntity(dto);

				memberRepository.save(member);

				return true;
			}
			else                // 코드 인증 실패상태
			{
				return false;
			}
		}
		else                    // 코드 인증 진행 X
		{
			return false;
		}

	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyMember(MemberDto dto) {

	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeMember(String id) {

	}
	
	public String getMemberName(String id) {

        return null; //
    }
	
	public boolean idcheck(String id) throws Exception {

		return false;
	}

}