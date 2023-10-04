package com.example.demo.Config.auth.provider;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoUserInfo implements OAuth2UserInfo{

    private String id;

    private Map<String,Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }
    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }
    @Override
    public String getName() {
        return (String)attributes.get("nickname");
    }

    public String getAddr1() {return (String)attributes.get("addr1");}
    public String getAddr2(){return (String)attributes.get("addr2");}
    public String getPhone(){return (String)attributes.get("phone");}
    public String getZipcode(){return (String)attributes.get("zipcode");}
}
