package com.webperside.webmallappv1.dto;

public class CompanyRegisterDto {

    private Integer userId;
    private String name;
    private String description;

    public CompanyRegisterDto(Integer userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
