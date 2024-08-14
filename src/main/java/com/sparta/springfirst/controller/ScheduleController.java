package com.sparta.springfirst.controller;

import com.sparta.springfirst.dto.ScheduleRequestDto;
import com.sparta.springfirst.dto.ScheduleResponseDto;
import com.sparta.springfirst.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // Schedule Max ID Check
        // scheduleList.keySet 메서드를 호출하면 Map에 들어있는 Long 값을 가져온다
        // 그중에 가장 큰 값을 가져온다, 그대로 넣으면 중복되므로 가장 큰 값에 1을 더해서 넣어주고 아니면 1을 넣는다
        Long maxId = scheduleList.size() > 0 ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setId(maxId);

        // DB 저장
        scheduleList.put(schedule.getId(), schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        // Map To List
        // values()를 이용하면 열거형의 모든 상수에 접근, stream()으로 하나씩 for문처럼 돌려준다
        // stream으로 하나씩 나오는 schedule을 파라미터로 가지는 ScheduleResponseDto 생성자가 나온다
        // 그것을 모아서 리스트를 만들어준다.
        List<ScheduleResponseDto> responseList = scheduleList.values().stream().map(ScheduleResponseDto::new).toList();

        return responseList;
    }

    @GetMapping("/schedules/{scheduleId}")
    public Long updateschedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        // containsKey는 Map의 자료 구조에서 Key에 해당하는 부분에 데이터가
        // 있는지 없는지 확인을 해주는 메서드, 반환타입은 boolean이다
        if (scheduleList.containsKey(scheduleId)) {
            // 해당 일정 가져오기
            Schedule schedule = scheduleList.get(scheduleId);
            // 해당 일정 수정
            schedule.update(requestDto);
            return schedule.getId();
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public Long deleteSchedule(@PathVariable Long scheduleId) {
        // 해당 메모가 DB에 존재하는지 확인
        if (scheduleList.containsKey(scheduleId)) {
            // 해당 메모를 삭제하기
            scheduleList.remove(scheduleId);
            return scheduleId;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }
}
