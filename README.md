# URL Shortening Project

## Intro
  - URL Shortening Project는 긴 길이의 URL을 짧은 URL로 전환해주고 단축된 URL을 Browser에 입력시 단축하기 전 URL로 연결해주는 서비스입니다.
  - 이를 통해 사용자는 긴 길이의 URL을 전달하거나 외워야할 시에 편리할 뿐만 아니라 자신 외에는 해당 URL이 어느 URL로 연결되는지 알수 없게 됩니다.

## Spec
  - Front-end
    - HTML
    - JQuery
    - Javascript (es6)
    - Bootstrap
  - Back-end
    - Spring Boot 2.1.0
    - Java 1.8
    - Gradle
    - H2 embedded DB
    - JDBC
  - Test
    - JUnit
  
## Resolution Strategy
  - URL Shortning을 위해서는 총 2가지 Hash Algorithm이 사용되었습니다.
  - 첫번째는 SHA256 알고리즘입니다. SHA256은 어떠한 값에 대해서는 동일한 길이의 Hash값으로 암호화시킵니다.
  - 또한 같은 입력에 점 하나만 더해지더라도 완전히 다른 값을 가져오기 때문에 Hash Collision을 피하기에 최적이라고 판단하였습니다.
  - 이에 1차적으로 SHA256 Hash를 값을 구하였고 이를 다시 Base62를 이용하여 8자리 문자로 전환하였습니다.
  - Base62 8자리는 최대값이 62^8까지 나올수 있기 때문에 SHA256의 앞 12자리(0 ~ 11) 값을 가져와서 Base62를 만들기 위한 Key값으로 사용하였습니다.
  - SHA256의 12자리에서 나올수 있는 값은 64^8이기 때문에 만일 12자리 값이 62^8을 넘어갈 때는 한자리 shift하여 1 ~ 12 값을 가져오도록 하였습니다.
  - 연이어 값이 넘더라도 최대 29개까지 Key를 가질수 있으며, 만일 29번 시도에도 값을 못찾을 경우에는 Hash 값을 다시 한번 암호화하여 동일 작업을 반복하기 때문에 실제적으로 62^8 내에서 Key를 못 만들어낼 가능성은 거의 없습니다.
  - 만들어진 Key는 입력된 URL과 함께 H2 DB로 insert 됩니다.
  - DB를 사용한 것은 이미 입력된 URL의 경우 값을 탐색하여 동일한 Key를 돌려주기 위함이며 키의 중복도 마찬가지로 탐색을 통해서 막도록 하였습니다.
  - Key는 localhost, service port 와 합쳐져 완성된 Shorten URL이되며 사용자가 사용할 수 있게 됩니다.

## Build
  - Gradle을 설치한 후 Project 경로로 이동
  - cmd 혹은 terminal에서 gradle build 실행
  - 혹은 Windows의 경우 gradlew.bat 입력하거나 Linux의 경우 sh ./gradlew 입력

## Run
  - Project 경로에서 gradle bootRun 입력

## Test
  - Unit Test는 URLShortening\src\test\java\com\example\demo\UrlShorteningApplicationTests.java 에 구현되어 있습니다.

## etc
  - URL은 http:// 혹은 https://로 입력된 값만 처리하도록 제한하였습니다.
  - 단축된 URL을 clip board에 저장할 수 있도록 버튼이 생성됩니다.
