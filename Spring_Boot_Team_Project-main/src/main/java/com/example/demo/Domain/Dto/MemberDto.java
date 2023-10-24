package com.example.demo.Domain.Dto;

import com.example.demo.Domain.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberDto {

	@NotBlank(message = "username을 입력하세요")
	private String username;
	@NotBlank(message = "password를 입력하세요")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;
	@NotBlank(message = "password를 입력하세요")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String repassword;

	private String role;
	private String phone;
	@NotBlank(message = "주소를 입력하세요")
	private String zipcode;
	private String addr1;
	private String addr2;
	private String name;
	@Email(message = "올바른 이메일 주소를 입력하세요")
	private String email;

	// OAuth2 Added
	private String provider;
	private String providerId;

	public static Member dtoToEntity(MemberDto dto){
		Member member = Member.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.role(dto.getRole())
				.phone(dto.getPhone())
				.zipcode(dto.getZipcode())
				.addr1(dto.getAddr1())
				.addr2(dto.getAddr2())
				.name(dto.getName())
				.email(dto.getEmail())
				.build();

		return member;
	}

}