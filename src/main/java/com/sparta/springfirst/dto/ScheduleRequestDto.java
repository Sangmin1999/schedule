package com.sparta.springfirst.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleRequestDto {
    private String username;
    private String contents;
    private String passward;
    private Long creationDate;
    private Long modificationDate;

    public ScheduleRequestDto(){
    }

    public ScheduleRequestDto(ScheduleRequestDto scheduleRequestDto) {
        this.passward = scheduleRequestDto.getPassward();
        this.contents = scheduleRequestDto.getContents();
        this.username = scheduleRequestDto.getUsername();
    }



}

