## 이력서를 기반으로 한 면접 예상 질문 서비스
![image](https://github.com/Resumarble/Resumarble-Backend/assets/93868431/bfef20b9-235a-4eba-9030-1a536b0ab38a)

서비스 링크: https://www.resumarble.site

## ⏳ Branch Strategy

main: 배포용 서비스 코드가 있는 메인 브랜치로, 항상 현재 서비스 상태를 반영해야 합니다.

develop: 다음 릴리스를 위해 최신 개발이 진행되는 개발 브랜치입니다.

feature 브랜치: 새로운 기능 개발에 사용되는 브랜치입니다. develop 브랜치에서 생성되며, 기능 개발이 완료되면 develop 브랜치에 병합됩니다.

fix 브랜치: 빠르게 코드를 수정하는 데 사용되는 브랜치입니다. develop 브랜치에서 생성되며, 수정이 완료되면 develop -> main 브랜치에 병합됩니다.

이러한 브랜치 전략을 통해 개발 과정을 체계적으로 관리하고, 기능별로 병렬적인 작업을 진행할 수 있습니다. 코드 변경은 각자의 기능 브랜치에서 이루어지며, 최종적인 안정 버전은 main 브랜치에 반영됩니다.

## Tech Stack

- Kotlin 1.9.20
- Spring Boot 3.1.6
- Spring MVC
- Spring Reactive Webflux
- Spring Security + JWT
- JPA(+ Spring Data JPA)
- Kotlin Jdsl 3.0
- Spring Cloud Gateway
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

### 3. 코루틴 사용시 Structured Concurrency 고려하기

- **문제점 발생 및 이슈**
    - 한번의 요청으로 면접 예상 질문 생성을 최대 3개까지 할 수 있도록 확장했지만, 블로킹 방식의 네트워크 요청은 개수에 비례해서 응답 시간이 늘어나는 문제가 발생 → 코루틴을 활용한 병렬 처리를 적용하였음
    - 코루틴 사용시, 자식 코루틴(네트워크 요청)이 실패하면서 다른 코루틴까지 실패하는 문제가 발생
- **해결 방안**
    - 부모 코루틴과 자식 코루틴의 Structured Concurrency 특성으로, 자식 코루틴의 예외가 부모로 전파되고, 부모가 종료를 위해 남은 자식 코루틴이 종료시키는 문제
    - 코루틴 빌더 중 SupervisorScope를 이용할 경우 자식 코루틴에서 발생한 예외는 전파되지 않을 것이라고 판단
- **결과**
    - 자식 코루틴(async)에서 예외가 발생하더라도 예외가 부모 코루틴까지 전파되지 않도록 격리할 수 있었음
    - 또한 요청이 병렬적으로 3번 요청되는 상황에서 각 요청은 독립적으로 처리되어 서로의 실패에 영향을 받지 않게 됨(**3개 중 1개 실패, 2개 성공시 모두 실패하던 로직 → 사용자는 2개에 대한 결과를 확인 가능)**
 
### 4. AOP가 적용된 내부 함수 호출시 AOP가 적용되지 않는 문제 발생

- **문제점 발생 및 이슈**
    - AOP가 적용된 비즈니스 로직을 내부 함수로 호출했을 때 AOP가 적용되지 않는 문제가 발생한다.
    - A함수에서 B함수를 호출할 때(B함수는 AOP 적용) A에서 호출하는 B 함수는 프록시가 아닌 인스턴스 자신의 함수이기 때문에 프록시가 사용될 수 없다.
- **해결 방안**
    - 관심사를 분리할 때 AOP가 아닌 코루틴의 후행 람다를 사용해서 분리한다.
- **결과**
    - 로그 처리와 RateLimiter를 AOP를 사용하지 않고 내부 함수 호출시에도 적용할 수 있게 되었지만, Continuation 객체가 넘어가야하기 때문에 suspend로 함수식을 선언했다.
    - 아직 완벽하게 이해하지 못했기 때문에 검증이 더 필요하다.

### 5. Thread Per Request의 한계

- **문제점 발생 및 이슈**
    - 레주마블 서버는 MVC 톰캣에서 실행되기 때문에, 사용자가 요청부터 결과를 받아볼 때까지 20초 내외가 소요된다.
    - 최대 쓰레드 수가 디폴트로 200개인 톰캣 옵션을 변경하여 다양하게 부하 테스트를 진행해본 결과, 사용자 요청이 크게 늘어나지 않았다.
- **해결 방안**
    - 사용자 요청당 1 쓰레드가 배정되기 때문에, 동시에 200개(디폴트)의 요청만 수행할 수 있고, 이후에는 대기 큐에 쌓인다.
    - 외부 API 호출에 코루틴을 활용하더라도, 사용자 요청에 배정된 쓰레드는 코루틴이 종료될 때까지 기다리게된다.
    - 요청을 많이 처리하기 위해 쓰레드 개수를 늘리는 것은 리소스를 더 많이 사용하기 때문에, 최적의 방법이라곤 할 수 없다.(스케일 업과 유사)
    - 이를 이벤트 루프 기반의 웹플럭스가 해결해줄 수 있다고 판단했다. 

## 프로젝트 멤버
| Backend. | Frontend. |
|:---:|:---:|
| ![전상준](https://avatars.githubusercontent.com/u/93868431?v=4) | ![이유](https://avatars.githubusercontent.com/u/48672106?v=4)
| [**전상준**](https://github.com/waveofmymind) | [**이유**](https://github.com/ReturnReason)
