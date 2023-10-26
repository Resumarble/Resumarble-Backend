## 이력서를 기반으로 한 면접 예상 질문 서비스
![image](https://github.com/Resumarble/Resumarble-Backend/assets/93868431/bfef20b9-235a-4eba-9030-1a536b0ab38a)

## ⏳ Branch Strategy

main: 배포용 서비스 코드가 있는 메인 브랜치로, 항상 현재 서비스 상태를 반영해야 합니다.

develop: 다음 릴리스를 위해 최신 개발이 진행되는 개발 브랜치입니다.

feature 브랜치: 새로운 기능 개발에 사용되는 브랜치입니다. develop 브랜치에서 생성되며, 기능 개발이 완료되면 develop 브랜치에 병합됩니다.

fix 브랜치: 빠르게 코드를 수정하는 데 사용되는 브랜치입니다. develop 브랜치에서 생성되며, 수정이 완료되면 develop -> main 브랜치에 병합됩니다.

이러한 브랜치 전략을 통해 개발 과정을 체계적으로 관리하고, 기능별로 병렬적인 작업을 진행할 수 있습니다. 코드 변경은 각자의 기능 브랜치에서 이루어지며, 최종적인 안정 버전은 main 브랜치에 반영됩니다.


## Tech Stack

- Kotlin 1.9.10
- Spring Boot 3.1.4
- Spring Security + JWT
- JPA(+ Spring Data JPA)
- Kotlin Jdsl 3.0
- Spring Cloud Open Feign
- MySQL 8.0.33
- Redis(+ Spring Data Redis)
- Kotest
- Coroutine

## 비즈니스 로직 플로우 차트

![image](https://github.com/Resumarble/Resumarble-Backend/assets/93868431/93a1c481-bdc9-4c46-be09-824cc827381a)

## **🛠️** 기술적 도전 및 트러블 슈팅

### **코루틴을 활용한 트랜잭션 강결합 문제 트러블 슈팅**

블로그 링크: **[[Link]](https://waveofmymind.github.io/posts/about-coroutine/)**

- **문제점 발생 및 이슈**
    - 질문 생성 요청 -> 외부 API로 질문 생성 -> 부가적인 로직(질문 저장, 사용자 이력 저장) -> 결과 응답의 흐름에서 문제 발생
    - 면접 예상 질문 생성시 외부 API 및 부가적인 로직의 예외 전파로 메인 로직이 실패하는 현상 발생
- **해결 방안**
    - DB 저장이 실패하더라도 사용자가 결과를 확인할 수 있는 것이 서비스에서 더 적합하다고 판단 -> 부가적인 로직을 비동기로 처리
    - Facade 패턴 적용
        - 네트워크를 이용하는 외부 API를 트랜잭션 밖으로 이동
        - DB 트랜잭션 레벨을 다운그레이드하여 트랜잭션의 범위를 최소한으로 설정
    - 예상 질문과 사용자 로그를 코루틴으로 비동기적으로 저장하여 예외 전파 해결 시도
- **결과**
    - 트랜잭션이 외부 서비스 및 부가적인 로직과 묶여서 예외 전파로 인해 롤백되는 강결합 문제 해결
    - 코루틴 Scope 내에서 발생하는 부가적인 로직의 예외 발생시 CoroutineExceptionHandler로 핸들링 및 별도로 로깅 처리
 
### 2. 백엔드 서버에서 더블 클릭 문제 발생

- **문제점 발생 및 이슈**
    - 예상 질문 생성시, 더블 클릭을 할 경우 서버로 같은 두 개의 요청이 오는 문제 발생
    - 사용자는 하나의 결과만을 확인하게 되지만, 서버에서는 응답을 DB에 모두 저장하게 되므로, 중복 데이터가 DB에 저장
    - 이는 마이페이지에서 의도하지 않는 중복 데이터를 확인할 수 있고, Open Ai의 limit 토큰을 불필요하게 사용하는 문제로 이어질 수 있다.
- **해결 방안**
    - RateLimiter를 활용해서 일정 시간 내에는 동일한 요청시 응답으로 429를 반환하도록 변경해야 함
    - Redis를 사용하고 있기 때문에, Counter 형식으로 구현
    - 로직마다, 회원마다 uniqueKey를 구분해야함(캐시 충돌 방지)
- **결과**
    - Redis를 활용하여 해당 로직의 Key에 대한 count값을 통해, 지정한 값 이상일 경우 예외를 반환
    - AOP의 장점을 활용하여, 핵심 로직에서 RateLimit 로직을 분리하여 코드의 가독성과 재사용성을 향상
        - 핵심 로직 내에서 RateLimit 처리를 직접 포함시키는 것은 로직의 복잡성을 증가시킬 수 있다.
        - 다른 로직에도 RateLimit을 사용하고자 할 경우, 코드가 중복되고 쉽게 확장할 수 없다.
        - count값의 경우 함수명 + userId를 통해 uniqueKey를 생성하므로, 캐시 충돌 방지 시도
