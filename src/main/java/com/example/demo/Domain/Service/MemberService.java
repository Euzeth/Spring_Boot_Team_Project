package com.example.demo.Domain.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Domain.Dto.MemberDto;
import com.example.demo.Domain.Entity.Member;
import com.example.demo.Domain.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Member> getAllMember(){
		return memberRepository.findAll();
	}

	public Optional<Member> searchMember(String username){ return memberRepository.findById(username);}
	
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
	public boolean modifyMember(MemberDto dto) {
		// 이전 회원 정보 가져오기
		Member oldMember = memberRepository.findById(dto.getUsername()).orElse(null);
        System.out.println("DTO : " +dto);
		if (oldMember != null) {
			// 새로운 정보로 업데이트
			oldMember.setName(dto.getName());
			oldMember.setZipcode(dto.getZipcode());
			oldMember.setAddr1(dto.getAddr1());
			oldMember.setAddr2(dto.getAddr2());
			oldMember.setPhone(dto.getPhone());
			oldMember.setEmail(dto.getEmail());
			oldMember.setRole("ROLE_USER");
			oldMember.setPassword(passwordEncoder.encode(dto.getRepassword()));

			// 회원 정보 저장
			Member updatedMember = memberRepository.save(oldMember);

			return updatedMember != null;
		} else {
			// 기존 회원 정보가 없을 경우 예외 처리 또는 오류 처리
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeMember(String id) {
		memberRepository.deleteById(id);
	}
	
	public Member getMemberName(String username) {
        return memberRepository.findById(username).orElse(null);
    }

	public MemberDto getUpdatedUserInfo(String username) {
		Member member = memberRepository.findById(username).orElse(null);
		if (member != null) {
			// Member를 MemberDto로 변환하여 반환
			return convertMemberToDto(member);
		}
		return null;
	}

	public MemberDto convertMemberToDto(Member member) {
		MemberDto memberDto = new MemberDto();
		memberDto.setUsername(member.getUsername());
		memberDto.setName(member.getName());
		memberDto.setZipcode(member.getZipcode());
		memberDto.setAddr1(member.getAddr1());
		memberDto.setAddr2(member.getAddr2());
		memberDto.setPhone(member.getPhone());
		memberDto.setEmail(member.getEmail());
		memberDto.setRole("ROLE_USER");

		System.out.println("memberDto : " + memberDto);

		return memberDto;
	}

	public boolean idcheck(String username) throws Exception {
		Member dbMember = memberRepository.findById(username).orElse(null);
		if (dbMember == null) {
			System.out.println("[INFO] 사용가능한 아이디입니다.");
			return true;
		}
		System.out.println("[ERROR] 이미 사용중인 아이디입니다.");
		return false;
	}

}