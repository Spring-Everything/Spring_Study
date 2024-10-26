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
### findAll(), Stream(), map(), collect(), Collectors.toList() 함수
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