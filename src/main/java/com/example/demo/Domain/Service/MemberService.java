package com.example.demo.Domain.Service;

import java.util.List;
import java.util.Optional;

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

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class MemberService{

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;
	
	public List<Member> getAllMember(){
		return memberRepository.findAll();
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
	public void modifyMember(Member member) {
		// 멤버 정보 가져오기
		Member existingMember = getMemberName(member.getUsername());

		if (existingMember != null) {
			// 수정할 필드 업데이트
			existingMember.setPassword(passwordEncoder.encode(member.getPassword()));
			existingMember.setName(member.getName());
			existingMember.setZipcode(member.getZipcode());
			existingMember.setAddr1(member.getAddr1());
			existingMember.setAddr2(member.getAddr2());
			existingMember.setPhone(member.getPhone());

			// 멤버 정보 저장
			memberRepository.save(existingMember);
		} else {
			// 해당 멤버가 존재하지 않을 경우 예외 처리
			//throw new MemberNotFoundException("Member not found with username: " + member.getUsername());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeMember(String id) {
		memberRepository.deleteById(id);
	}
	
	public Member getMemberName(String username) {
        return memberRepository.findById(username).orElse(null);
    }


}