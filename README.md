# 🧑‍💻 Spring_Study
🍀 Spring으로 이것저것 무작정 구현해보기 🍀

## 💻 구현 환경
`Spring Boot`
- Spring Version : 3.4.0 SNAPSHOT
- Java Version : 23
- Gradle - Groovy
- Java
- yml

## 📝 기록지
### `findAll()`, `Stream()`, `map()`, `collect()`, `Collectors.toList()` 함수
    List<UserDTO> userDTO = userRepository.findAll().stream()
                .map(UserDTO::entityToDto)
                .collect(Collectors.toList());
- 🤔 들어가기 전에
  - 아이디, 닉네임 조회처럼 그냥 findAll 함수쓰고 orElseThrow로 예외 던지면 끝아님?
    - 리스트 자체를 묶어서 다루기 때문에 위와 같이 해줘야함

- `userRepository.findAll()`
  - 말 그대로 userRepository에서 제공하는 현재 데이터베이스에 존재하는 모든 객체를 조회
- `stream()`
  - 조회된 UserEntity 리스트에 스트림을 생성
  - 스트림을 사용하면 각 요소에 대해 일련의 변환 및 처리를 적용 가능
- `map(UserDTO::entityToDto)`
  - map 메서드는 스트림의 각 요소 (여기서는 UserEntity 객체) 에 대해 지정된 함수를 적용하고 그 결과를 새로운 스트림으로 반환
- `collect(Collectors.toList())`
  - collect 메서드는 스트림의 요소들을 수집하여 최종 결과를 보여줌
  - Collectors.toList() 메서드는 스트림을 List<UserDTO> 클래스 형태로 변환해 주기 때문에 최종적으로 List<UserDTO> 클래스 타입의 userDTO 객체가 됨

### `.orElseThrow()` 함수 사용 이유
    호환되지 않는 타입입니다. 발견: 'java.util.Optional<com.example.Spring_Study.Entity.UserEntity>', 필요: 'com.example.Spring_Study.Entity.UserEntity'
- 특정 객체를 조회할때 .orElseThrow();를 사용하지않으면 위와같은 에러가 발생
- 🤔 들어가기 전에
  - 그럼 왜 이 에러가 발생할까?
    - 반환되는 값이 `UserEntity` 타입이 아닌 `Optional<UserEntity>`를 반환하고 있기 때문이다
    - findById, findAll, save, delete 등과 같은 레포지토리 인터페이스 jpa 제공 함수의 반환값은 Optional<UserEntity>이기 때문
      - 그래서 레포지토리 인터페이스에 `UserEntity findByUid(String uid);` 구현 후 uid로 회원 조회를 추가해 봤더니 없이 사용 가능했음
- 결론적으로 `orElseThrow()` 반환값이 `Optional<UserEntity>` 일 경우 null값이 존재할 수 있기 때문에 예외를 던지기 위해 사용하는 것
- `.orElseThrow()`는 값이 없을 때 바로 예외를 던져 간결하고 예외 처리를 명확하게 해줌
- 그래서 대부분의 경우 `.orElseThrow()`를 사용하는 것이 더 간편하고 오류를 방지하는 데 유리함