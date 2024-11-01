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
- `@OneToMany`
  - 의미 : 한 엔티티가 여러 개의 다른 엔티티와 연결될 때 사용되는 어노테이션
  - 예시 : 예를 들어 한 `User`가 여러 개의 `Order`를 가질 수 있는 경우 `User`와 `Order`는 `@OneToMany` 관계가 됨
  - 사용법 : `@OneToMany는` "다"쪽 엔티티에서 `@ManyToOne으로` 연결을 설정하고 "하나"쪽에서 mappedBy 속성을 통해 양방향 관계를 정의
  - 특징 : `cascade`, `orphanRemoval`, `fetch` 같은 추가 옵션을 사용해 데이터 전파나 로딩 방식을 지정할 수 있음. mappedBy 속성을 통해 연관관계의 주인을 지정
- `@ManyToMany`
  - 의미 : 여러 개의 엔티티가 하나의 엔티티와 연결될 때 사용되는 어노테이션
  - 예시 : 여러 개의 `Order`가 하나의 `User`와 연결될 때 `Order`와 `User`는 `@ManyToOne` 관계가 됨
  - 사용법 : `@ManyToOne은` 관계에서 "다"쪽에 설정되고, `@JoinColumn을` 통해 외래 키 컬럼을 지정할 수 있음
  - 특징 : 지연 로딩(`fetch = FetchType.LAZY`)이 기본값. 즉, 관계 엔티티를 실제 사용할 때까지 로드하지 않음
- `@ManyToOne`
  - 의미 : 여러 개의 엔티티가 서로 여러 개의 다른 엔티티와 연결될 때 사용되는 어노테이션
  - 예시 : 학생(`Student`)과 과목(`Course`) 관계에서, 하나의 학생이 여러 과목을 듣고, 하나의 과목을 여러 학생이 들을 수 있을 때 `Student` `Course` `@ManyToMany` 관계가 됨
  - 사용법 : `@ManyToMany는` 기본적으로 조인 테이블을 통해 두 엔티티 간의 관계를 설정. `@JoinTable`을 사용하여 중간 테이블 이름과 키를 지정할 수 있음
  - 특징 : 연관관계의 주인은 보통 `mappedBy` 속성을 통해 설정. 양방향 관계에서 두 엔티티 중 하나에만 `mappedBy`를 설정함


### 📚 `cascade`
- 🤔 들어가기 전에
  - `cascade`는 JPA에서 부모 엔티티에 대한 작업이 자식 엔티티에도 영향을 미치도록 설정하는 것
  - 특히 부모와 자식 엔티티 간의 관계에서 부모 엔티티를 변경 즉 삭제, 저장 등을 할 때 자식 엔티티에도 동일한 작업을 자동으로 수행하도록 해주는 기능
- 🤔 종속 제거 예시 (외래키 삭제)
  - `Entity`에서 필요한 코드
    -     @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
          @Builder.Default
          private Set<MessageEntity> messages = new HashSet<>();
    - 🤔 위 코드 분석
      - `@OneToMany(mappedBy = "sender")`
        - `UserEntity`가 여러 개의 `MessageEntity`와 연결되어 있음을 나타내는 부분
      - `fetch = FetchType.LAZY`
        - 데이터를 실제로 사용하기 전까지는 `messages` 필드가 로딩되지 않게 하는 지연 로딩을 사용. 필요할 때만 데이터베이스에서 조회하여 성능을 개선할 수 있음
      - `cascade = CascadeType.ALL`
        - `UserEntity`에 대해 수행된 모든 작업(`PERSIST`, `MERGE`, `REMOVE` 등)이 `messages` 필드에 있는 `MessageEntity` 엔티티에도 동일하게 적용됨
      - `orphanRemoval = true`
        - `messages` 컬렉션에서 `MessageEntity`가 제거되면 데이터베이스에서도 자동으로 삭제하도록하는 부분이 바로 이 부분. 즉, 고아 객체가 되는 엔티티는 자동으로 삭제됨
      - `@Builder.Default`
        - `@Builder` 사용할 때 기본값으로 빈 `HashSet`을 설정해줌. 이렇게 하면 객체를 생성할 때 `messages` 컬렉션이 `null`이 아닌 빈 집합으로 초기화 됨
      - `private Set<MessageEntity> messages = new HashSet<>();`
        - `messages` 필드는 `MessageEntity` 타입의 집합(`Set`)으로 정의되며, 중복 메시지가 없도록 관리함
  - Service, Repository에서 필요한 부분
    -     void deleteByUserUid(String uid);
    -     void deleteBySenderUid(String uid);
          void deleteByReceiverUid(String uid);
    -     // 회원 삭제
          @Transactional
          public UserDTO deleteUser(String uid) {
              UserEntity userEntity = userRepository.findByUid(uid);
              bucketRepository.deleteByUserUid(uid);
              messageRepository.deleteBySenderUid(uid);
              messageRepository.deleteByReceiverUid(uid);
              userRepository.delete(userEntity);
              return UserDTO.entityToDto(userEntity);
          }
    - 🤔 위 코드 분석
      - 해당 유저가 생성한 버킷, 메세지를 순차적으로 삭제하고 최종적으로 해당 유저를 삭제함

### 📚 자바 상식
- 변수를 초기화 하지 않았을 때 발생하는 문제 (좋아요 등의 수 증가 등등 ,,, )
  - `NullPointerException` - JVM이 이 null 값을 수치 연산에 사용하려고 시도하여 `NullPointerException`을 발생시키는 것. 그러니 항상 초기화를 시켜주자
    - 엔티티에 초기값 설정을 할 수 있지만 난 함수에 적용하는게 더 편한듯,,,
- 
