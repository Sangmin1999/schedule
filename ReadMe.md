| 기능       | 메서드    | URL                         | request  | response |
|------------|--------|-----------------------------|----------|----------|
| 일정 작성  | POST   | /api/schedules              | 요청 body  | 등록 정보    |
| 일정 조회  | GET    | /api/schedules/{scheduleId} | 요청 param | 단건 응답 정보 |
| 일정 목록 조회 | GET    | /api/schedules              | 요청 param | 다건 응답 정보 |
| 일정 수정  | PUT    | /api/schedules/{scheduleId} | 요청 body  | 수정 정보    |
| 일정 삭제  | DELETE | /api/schedules/{scheduleId} | 요청 param | -        |
