package com.example.demo.Config.auth.provider;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
    String getAddr1();
    String getAddr2();
    String getPhone();
    String getZipcode();
}
