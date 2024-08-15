package com.sparta.springfirst.controller;

import com.sparta.springfirst.dto.ScheduleRequestDto;
import com.sparta.springfirst.dto.ScheduleResponseDto;
import com.sparta.springfirst.dto.UpdateDto;
import com.sparta.springfirst.entity.Schedule;
import com.sparta.springfirst.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 등록
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);


    }

    // 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedule() {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule();

    }

    // 일정 수정
    @PutMapping("/schedules")
    public ScheduleResponseDto updateSchedule(@RequestBody UpdateDto updateDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.updateSchedule(updateDto);


    }

    // 일정 삭제
    @DeleteMapping("/schedules/{id}/{passward}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String passward) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.deleteSchedule(id, passward);

    }

    // 아이디로 일정 조회
    @GetMapping("/schedules/read/{id}")
    public Schedule findById(@PathVariable Long id) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.findById(id);

    }
}