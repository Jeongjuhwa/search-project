# search-project
외부(오픈) API를 연동하여 블로그 검색 프로젝트

# 기술스택
- java11, springboot, jpa
- db: h2, redis
# 주의사항
- h2-console 접속시, jdbc url -> jdbc:h2:mem:test 로 변경 필요
- 메모리 디비로 실행마다 디비는 리셋되나, 캐싱용 레디스는 지워지지 않아 재실행시 레디스 초기화 필요(DB 키값 기반으로 레디스 키 생성)

# 추가된 기능
- package search.schedules.keywords.KeywordsScheduler
  - 레디스에 저장된 검색어 조회 카운트를 스케줄러를 통해 만개씩 끊어서 DB에 싱크맞춰주는 기능
