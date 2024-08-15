package com.sparta.springfirst.dto;

import com.sparta.springfirst.entity.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String contents;
    private Date creationDate;
    private Date modificationDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.contents = schedule.getContents();

    }

    public ScheduleResponseDto(Long id, String username, String contents,  Date creationDate, Date modificationDate) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }


    public ScheduleResponseDto(long id, String username, String contents, Date modificationDate) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.modificationDate = modificationDate;
    }
}
