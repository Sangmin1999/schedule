package com.sparta.springfirst.entity;


import com.sparta.springfirst.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String username;
    private String contents;
    private String passward;
    private Date creationDate;
    private Date modificationDate;

    public Schedule(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.passward = requestDto.getPassward();

    }

    public void update(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.passward = requestDto.getPassward();
    }
}
