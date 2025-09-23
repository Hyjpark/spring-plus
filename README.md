# spring-plus

Spring 학습 프로젝트로, 트랜잭션, JPA, QueryDSL, JWT, Spring Security, AOP 등 실무 핵심 기능을 단계별로 실습하며 구현 역량을 강화하는 것을 목표로 합니다.  
각 기능은 **Lv1~Lv3**로 나누어 코드 개선, 테스트, 심화 기능 구현을 진행했습니다.

---

## 프로젝트 구조
- **Lv1 (기본 학습)**: 코드 개선, JPA/트랜잭션 이해, 테스트 코드 작성, AOP 학습
- **Lv2 (중급)**: JPA Cascade, N+1 문제 해결, QueryDSL 적용, Spring Security 적용
- **Lv3 (도전)**: QueryDSL 기반 검색 기능 구현, 트랜잭션 심화 적용, 페이징 처리 및 로깅

---

## Lv1 – 기본 학습
- **코드 개선 퀴즈 – @Transactional**
  - API `/todos` 호출 시 에러 해결
- **코드 추가 퀴즈 – JWT**
  - User 테이블에 `nickname` 컬럼 추가
  - nickname 컬럼 중복 허용 확인
  - JWT 페이로드에 nickname 추가
- **코드 개선 퀴즈 – JPA**
  - 조건별 검색, 수정일 기준 기간 검색
  - 조건이 있을 때와 없을 때 모두 올바른 검색
  - JPQL 기반 검색 쿼리 작성
  - weather 조건으로 할 일 검색
- **테스트 코드 퀴즈 – Controller 테스트**
  - todo 단건 조회 시 예외 발생 테스트 통과 확인
- **코드 개선 퀴즈 – AOP**
  - `changeUserRole()` 실행 전 AOP 동작 확인
  - UserAdminController에서 AOP 적용 확인

---

## Lv2 – 중급 학습
- **JPA Cascade**
  - 할 일 저장 시 담당자 등록
  - cascade 기능 적절히 적용
- **N+1 문제 해결**
  - CommentController `getComments()` 호출 시 N+1 문제 방지
- **QueryDSL로 쿼리 변경**
  - 기존 JPQL `findByIdWithUser`를 QueryDSL로 변환
  - 쿼리 결과 정확히 반환, N+1 문제 해결
- **Spring Security 적용**
  - 기존 Filter/Argument Resolver 대체
  - JWT 활용 인증
  - 권한 및 접근 제어 정상 동작

---

## Lv3 – 도전 과제
- **QueryDSL 검색 기능**
  - 제목, 생성일, 담당자 닉네임 등 조건 검색
  - 필요한 필드만 반환, 페이징 처리
  - 검색 API 정상 동작
- **Transaction 심화**
  - @Transactional 옵션 활용, 매니저 등록 실패 시 로그 남기기
  - 매니저 등록 요청 시 로그 정상 저장

---

## 브랜치별 구현 내역

### Lv1 – 기본 학습
- **feature/transactional-fix** : @Transactional 적용으로 데이터 저장 및 API 에러 수정
- **feature/todo-search-jpql** : JPA 검색 조건 기반 쿼리 구현 (JPQL)
- **feature/todo-controller-test-fix** : Controller 테스트 코드 수정 및 통과 확인
- **feature/aop-fix** : AOP 적용 – changeUserRole() 전 동작 확인
- **feature/jwt-user-info** : JWT 페이로드에 nickname 정보 추가

### Lv2 – 중급 학습
- **feature/jpa-cascade-apply** : JPA Cascade 적용, 할 일 저장 시 담당자 등록
- **fix/n-plus-one** : N+1 문제 해결 (CommentController)
- **feature/apply-querydsl** : JPQL → QueryDSL 변환, N+1 문제 해결
- **feature/spring-security** : Spring Security 적용 및 JWT 인증 구현

### Lv3 – 도전 과제
- **feature/querydsl-search** : QueryDSL 기반 일정 검색 기능 구현
- **feature/transaction-manager-log** : Transaction 심화 – 매니저 등록 실패 시 로그 남기기
- **feature/add-workflow** : 프로젝트 전반 워크플로우 및 기능 통합
