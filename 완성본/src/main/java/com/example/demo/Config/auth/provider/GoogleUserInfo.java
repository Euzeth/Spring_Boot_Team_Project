package com.example.demo.Config.auth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    private Map<String,Object> attributes;

    public GoogleUserInfo(Map<String,Object> attributes){
        this.attributes = attributes;
    }
    @Override
    public String getProviderId() {
        return (String)attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }

    public String getAddr1() {return (String)attributes.get("addr1");}
    public String getAddr2(){return (String)attributes.get("addr2");}
    public String getPhone(){return (String)attributes.get("phone");}
    public String getZipcode(){return (String)attributes.get("zipcode");}
}
