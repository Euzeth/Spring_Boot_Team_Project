package com.example.demo.Config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.example.demo.Domain.Dto.MemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {

	private MemberDto member;

	//OAUTH2 ----------------------------------------------------------------
	private Map<String, Object> attributes;
	private String accessToken;
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return member.getName();
	}
	public String getAddr1() {return member.getAddr1();}
	public String getAddr2() {return member.getAddr2();}
	public String getPhone() {return member.getPhone();}
	public String getEmail() {return member.getEmail();}
	public String getZipcode(){return member.getZipcode();}
	public String getRole(){return member.getRole();}
	//OAUTH2 ----------------------------------------------------------------

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList();

		collection.add(new GrantedAuthority(){
			@Override
			public String getAuthority() {
				return member.getRole();
			}

		} );

		return collection;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}