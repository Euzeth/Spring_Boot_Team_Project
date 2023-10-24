package com.example.demo.Config.auth;

import com.example.demo.Domain.Dto.MemberDto;
import com.example.demo.Domain.Entity.Member;
import com.example.demo.Domain.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> user =  memberRepository.findById(username);

		if(user.isEmpty())
			return null;

		MemberDto dto = new MemberDto();
		dto.setUsername(user.get().getUsername());
		dto.setPassword(user.get().getPassword());
		dto.setRole(user.get().getRole());

		PrincipalDetails principalDetails = new PrincipalDetails();
		principalDetails.setMember(dto);

		return principalDetails;
	}
}
