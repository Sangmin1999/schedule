package com.sparta.springfirst.entity;


import com.sparta.springfirst.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String username;
    private String contents;
    private String passward;
    private Long creationDate;
    private Long modificationDate;

    public Schedule(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.passward = requestDto.getPassward();
        this.creationDate = requestDto.getCreationDate();
        this.modificationDate = requestDto.getModificationDate();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.passward = requestDto.getPassward();
        this.creationDate = requestDto.getCreationDate();
        this.modificationDate = requestDto.getModificationDate();
    }
}
