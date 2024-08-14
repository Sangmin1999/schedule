package com.sparta.springfirst.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String username;
    private String contents;
    private String passward;
    private Long creationDate;
    private Long modificationDate;
}
