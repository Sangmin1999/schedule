package com.sparta.springfirst.service;

import com.sparta.springfirst.dto.ScheduleRequestDto;
import com.sparta.springfirst.dto.ScheduleResponseDto;
import com.sparta.springfirst.dto.UpdateDto;
import com.sparta.springfirst.entity.Schedule;
import com.sparta.springfirst.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule saveSchedule = scheduleRepository.save(schedule);


        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;

    }

    public List<ScheduleResponseDto> getSchedule() {
        // DB 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll();


    }

    public ScheduleResponseDto updateSchedule(UpdateDto updateDto) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.update(updateDto);


    }

    public Long deleteSchedule(Long id, String passward) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            // 일정 삭제
            scheduleRepository.delete(id, passward);


            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }

    }

    public Schedule findById(Long id) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        // DB 조회
        return scheduleRepository.findById(id);

    }
}
