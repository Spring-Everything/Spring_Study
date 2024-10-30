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
### 📚 `findAll()`, `Stream()`, `map()`, `collect()`, `Collectors.toList()` 함수
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

### 📚 `.orElseThrow()` 함수 사용 이유
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

### 📚 Spring Data JPA의 메서드 이름 특정 규칙
- 🤔 들어가기 전에
  - Spring Data JPA에서 메서드 이름은 특정 규칙을 따라야 하며, 이 규칙에 따라 이름을 지어야 자동으로 쿼리가 생성된다 
  - 예를하나 들어보자. 메서드 이름이 `findBy`로 시작하고, 이어서 필드 이름과 조건을 붙여 나가는 방식으로 작성이 된다 
  - 이러한 규칙을 통해 Spring은 메서드 이름을 분석하게되고 해당 조건에 맞는 SQL 쿼리를 자동으로 생성하게된다

- 🤔 그러면 메서드 이름이 주어지는 규칙이 뭘까?
  - 위에서 `findBy`로 예시를 들었다. 대부분 "검색을 의미하는 접두사"의 의미로 알고있으며 정답이다
  - 우리가 자주쓰는 `findById`나 `findByUid`등 뒤의 `Id`나 `Uid` 등은 "필드이름"에서 가져온다
   
- 🤔 그외의 조건?
  - `Containing` : 특정 문자열이 포함되어 있다는 의미
  - `IgnoreCase` : 대소문자를 구분하지 않겠다는 의미
    - 예시 : `findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase` : 제목이나 내용에 주어진 키워드가 포함된 모든 객체를 조회

### 📚 어노테이션 정리
- `@NoArgsConstructor`
  - 파라미터가 없는 기본 생성자를 자동으로 생성
- `@AllArgsConstructor`
  - 모든 필드를 파라미터로 받는 생성자를 자동으로 생성
- `@Getter`
  - 모든 필드에 대해 getter 메서드를 자동으로 생성
- `@Setter`
  - 모든 필드에 대해 setter 메서드를 자동으로 생성
- `@Builder`
  - 빌더 패턴을 자동으로 생성하여 객체를 간편하게 생성하게 해줌
- `@Service`
  - 해당 클래스가 비즈니스 로직을 담당하는 서비스 클래스임을 나타냄
- `@RequiredArgsConstructor`
  - final이나 @NonNull이 붙은 필드만을 매개변수로 받는 생성자를 자동으로 생성, 
    - final로 선언된 필드에 대해 생성자를 자동 생성해준다는 뜻. 그 아래를 사용하지 않아도 됨
      -     public UserServiceImpl(UserRepository userRepository) {
                this.userRepository = userRepository;
            }
      - 의존성 주입 더 안해줘도 됨
- `@RestController`
  - Spring MVC에서 REST API를 처리하는 컨트롤러임을 나타내며, 메서드에서 반환된 데이터는 JSON 형태로 응답
- `@RequestMapping`
  - 특정 URL에 대해 요청을 매핑하며, 클래스 또는 메서드 단위에서 사용됨
- `@Slf4j`
  - log 객체를 자동으로 생성해주는 로깅 라이브러리 Lombok 어노테이션. 아래처럼 사용 가능
    -     log.info("This is an informational log message.")
- `@Transactional`
  - 메서드 또는 클래스에 트랜잭션을 적용하여 작업을 일관되게 수행하고, 예외 발생 시 자동으로 롤백하도록 설정
- `@Builder.Default`
  - 초기값, 기본값 설정
    - 초기값, 기본값 설정할땐 위 어노테이션보단 `@Column(nullable = false)` 권장

### 📚 자바 상식
- 변수를 초기화 하지 않았을 때 발생하는 문제 (좋아요 등의 수 증가 등등 ,,, )
  - `NullPointerException` - JVM이 이 null 값을 수치 연산에 사용하려고 시도하여 `NullPointerException`을 발생시키는 것. 그러니 항상 초기화를 시켜주자
    - 엔티티에 초기값 설정을 할 수 있지만 난 함수에 적용하는게 더 편한듯,,,
- 