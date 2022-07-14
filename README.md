#바로고 개발 과제
## 구현 목표
- 재고 서비스를 위한 Back-End API 개발
## 요구사항
- 재고 조회 
- 재고 증감
- 재고 차감
## 사용기술 스택
- Framework : Spring Boot
- RDB : Mysql, H2
- In-Memory DB : Redis

## 실행 방법
- docker run -p 3306:3308 --name mysql_boot -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=crud_db -e MYSQL_USER=ksh -e MYSQL_PASSWORD=pass -d mysql
- docker run -p 6379:6379 --name redis_crud_db -d redis
- docker run -d -p 1521:1521 -p 8081:81 -v /Users/dev/util/h2:/opt/h2-data -e H2_OPTIONS=-ifNotExists --name=h2 oscarfonts/h2:1.4.199
- ./gradlew clean build --exclude-task test
- java -jar build/libs/inventory_api-0.0.1-SNAPSHOT.jar
- 브라우저에서 http://localhost:8080/swagger-ui.html 접속

## 구현 API 처리 방법
- exception
    - ApiCustomException : Exception 메시지를 커스텀 하여 처리 할 수 있도록 처리 하였습니다
    - GlobalExceptionHandler : Dto 유효성 체크의 Exception 처리 하도록 하였습니다
- common
    - ErrorCode : 오류 코드
- config
    - swagger 설정
    - redis 설정
    - async 설정
- controller
    - GoodsController : 상품 API 
        - POST /api/v1/goods : 상품 등록
        - POST /api/v1/goods/{goodsSrl}/inventory : 상품 재고 수정
          - redisson을 사용하여 싱글락 처리를 하였습니다.
        - GET : 상품 조회
- 테스트 컨트롤러
    - GoodsControllerTest
        - 테스트목록
            - createGoods : 상품 생성
            - existsByGoods : 상품 중복 체크
            - updateGoodsInventoryIncrease : 상품 재고 증감
            - minusUpdateGoodsInventory : 재고 마이너스 입력
            - updateGoodsInventoryDeduction : 재고 차감
            - updateGoodsInventoryDeductionZero : 재고 차감시 0 이하일때 예외처리
            - findGoodsNm : 상품명 조회
            - findGoodsNmAndOptionNm : 상품명 + 옵션명 조회
- 테스트 서비스 
    - GoodsServiceTest
        - 테스트 목록
          - updateIncreaseInventoryMultiThreadTest : 재고 증감 멀티쓰레드 테스트
          - updateDeductionInventoryMultiThreadTest : 재고 차감 멀티쓰레드 테스트

## DB
```  sql

    create table goods
(
    goods_srl   bigint       not null
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    goods_nm    varchar(255) not null,
    option_nm   varchar(255) null
);

create table inventory
(
    inventory_srl bigint not null
        primary key,
    stock         bigint null,
    goods_srl     bigint null,
    constraint FK9t96vlpmhnt9u1gohp0lp6s71
        foreign key (goods_srl) references goods (goods_srl)
);

```

## 개선사항
- redisson을 싱글락을 처리 하였으나 다수의 인스턴스 홛경에서는 분산락이 필요 할 것 같습니다.
- API는 조회 사용율이 높으니 글로벌 캐싱 처리가 필요합니다.
- API별 처리 속도를 개선 하기위해 MySQL Replication을 사용하여 master와 slave를 구분하여 master(insert, update, delete), slave(select) 처리하도록 추가가 필요합니다.
- 다수의 서버의 많은 트래픽 방지를 위해 로드밸런싱 처리 및 scale out이 필요합니다.
