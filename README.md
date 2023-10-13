## 이력서를 기반으로 한 면접 예상 질문 서비스
![image](https://github.com/Resumarble/Resumarble-Backend/assets/93868431/bfef20b9-235a-4eba-9030-1a536b0ab38a)

## ⏳ Branch Strategy

main: 배포용 서비스 코드가 있는 메인 브랜치로, 항상 현재 서비스 상태를 반영해야 합니다.

develop: 다음 릴리스를 위해 최신 개발이 진행되는 개발 브랜치입니다.

feature 브랜치: 새로운 기능 개발에 사용되는 브랜치입니다. develop 브랜치에서 생성되며, 기능 개발이 완료되면 develop 브랜치에 병합됩니다.

hotfix 브랜치: 빠르게 코드를 수정하는 데 사용되는 브랜치입니다. develop 브랜치에서 생성되며, 수정이 완료되면 main develop 브랜치에 병합됩니다.

이러한 브랜치 전략을 통해 저희는 개발 과정을 체계적으로 관리하고, 기능별로 병렬적인 작업을 진행할 수 있습니다. 코드 변경은 각자의 기능 브랜치에서 이루어지며, 최종적인 안정 버전은 main 브랜치에 반영됩니다.


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
