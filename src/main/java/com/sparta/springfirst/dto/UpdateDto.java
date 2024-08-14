package com.sparta.springfirst.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateDto {

    private Long id;
    private String username;
    private String contents;
    private String passwards;
}
