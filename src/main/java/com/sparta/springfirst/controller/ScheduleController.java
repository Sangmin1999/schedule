package com.sparta.springfirst.controller;

import com.sparta.springfirst.dto.ScheduleRequestDto;
import com.sparta.springfirst.dto.ScheduleResponseDto;
import com.sparta.springfirst.dto.UpdateDto;
import com.sparta.springfirst.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        
        String sql = "INSERT INTO schedule (sd_username, contents, sd_passward) VALUES (?, ?, ? )";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getUsername());
                    preparedStatement.setString(2, schedule.getContents());
                    preparedStatement.setString(3, schedule.getPassward());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedule() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("sd_id");
                String username = rs.getString("sd_username");
                String contents = rs.getString("contents");
                Date creationDate = rs.getDate("sd_regdata");
                Date modificationDate = rs.getDate("sd_modifydate");
                return new ScheduleResponseDto(id, username, contents, creationDate, modificationDate);
            }
        });
    }

    @PutMapping("/schedules")
    public ScheduleResponseDto updateSchedule(@RequestBody UpdateDto updateDto) {

        String sql = "UPDATE schedule SET sd_username = ?, contents = ? WHERE sd_id = ? AND sd_passward = ?";
        jdbcTemplate.update(sql, updateDto.getUsername(), updateDto.getContents(), updateDto.getId(), updateDto.getPasswards());
        sql = "select * from schedule WHERE sd_id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return new ScheduleResponseDto(resultSet.getLong("sd_id"), resultSet.getString("sd_username"), resultSet.getString("contents"),resultSet.getDate("sd_regdata"),resultSet.getDate("sd_modifydate"));
            } else{
                return null;
            }
        }, updateDto.getId());

    }

    @DeleteMapping("/schedules/{id}/{passward}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String passward) {
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {
            // 일정 삭제
            String sql = "DELETE FROM schedule WHERE sd_id = ? AND sd_passward = ?";
            jdbcTemplate.update(sql, id, passward);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    @GetMapping("/schedules/read/{id}")
    public Schedule findById(@PathVariable Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE sd_id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setUsername(resultSet.getString("sd_username"));
                schedule.setContents(resultSet.getString("contents"));
                schedule.setCreationDate(resultSet.getDate("sd_regdata"));
                schedule.setModificationDate(resultSet.getDate("sd_modifydate"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}