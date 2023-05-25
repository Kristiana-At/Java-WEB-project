package com.example.JavaWEBbff.models.resources;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCardOwnerDto {
    private String name;
    private Integer age;
    private String username;
    private String password;
}
