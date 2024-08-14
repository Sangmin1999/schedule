package com.sparta.springfirst.dto;

import com.sparta.springfirst.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String passward;
    private Long creationDate;
    private Long modificationDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.contents = schedule.getContents();
        this.passward = schedule.getPassward();
        this.creationDate = schedule.getCreationDate();
        this.modificationDate = schedule.getModificationDate();
    }
}
