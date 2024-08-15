package com.sparta.springfirst.repository;

import com.sparta.springfirst.dto.ScheduleResponseDto;
import com.sparta.springfirst.dto.UpdateDto;
import com.sparta.springfirst.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체


        String sql = "INSERT INTO schedule (sd_username, contents, sd_passward) VALUES (?, ?, ? )";
        jdbcTemplate.update(con -> {
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

        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {
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

    public ScheduleResponseDto update(UpdateDto updateDto) {
        String sql = "UPDATE schedule SET sd_username = ?, contents = ?, sd_modifydate = NOW() WHERE sd_id = ? AND sd_passward = ?"; // Now()는 데이터 수정시 현제시간을 나타내줌
        jdbcTemplate.update(sql, updateDto.getUsername(), updateDto.getContents(), updateDto.getId(), updateDto.getPassward());
        sql = "select * from schedule WHERE sd_id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return new ScheduleResponseDto(resultSet.getLong("sd_id"), resultSet.getString("sd_username"), resultSet.getString("contents"), resultSet.getDate("sd_modifydate"));
            } else {
                return null;
            }
        }, updateDto.getId());
    }

    public void delete(Long id, String passward) {
        String sql = "DELETE FROM schedule WHERE sd_id = ? AND sd_passward = ?";
        jdbcTemplate.update(sql, id, passward);
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE sd_id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
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
