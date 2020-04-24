package com.oauth.enums;

/**
 * @author 이승환
 * @since 2020-04-20
 */
public enum UserType {
    
    ADMIN("최고관리자"),
    USER("일반사용자");
    
    private String value;
    
    UserType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
