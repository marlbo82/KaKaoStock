### KaKaoStock


### 개발 프레임워크
- Spring Boot



### 빌드 및 실행방법
1. git Project clone
2. Update Maven Project
3. Embeded Tomcat을 이용한 application 실행



### API 명세
1. 1번 API
   1) uri:  /customers?func=getLargestSumAmtCustomerByYear
   2) method: GET
   
2. 2번 API
   1) uri: /customers?func=getNoTransCustomerListByYear
   2) method: GET

3. 3번 API
   1) uri:  /branches?func=getSumAmtBranchListByYear
   2) method: GET
   
4. 4번 API
   1) uri: /branches?func=getSumAmtBranch
   2) method: GET
   3) Content-Type: application/json
   4) body: ex1) {"brName" : "분당점"}
            ex2) {"brName" : "판교점"}
            ex3) {"brName" : "요하네스버그점"}

            

### 문제해결 방법
1. 의문사항
   1) API 작성은 요구사항만 만족할 수 있으면 되는 것인가?
   2) 다양한 request 정의를 통해 요구사항을 parameter로 받아 처리할 수 있도록 API가 설계되어야 하는 것은 아닌가?
   3) 기타의 Exception 사항에 대한 처리는 임의로 하면 되는가?

2. 의문해결
   1) 의문사항1)과 의문사항2) 중,
      API 실행 결과를 얻어내는 일반적인 set를 사용하실 것을 고려하여,
      요구사항만을 만족할 수 있도록 최소한의 API 구분자를 사용하여 API를 개발하였습니다.
   2) 기타의 Exception 처리는 임의로 처리하였습니다.
   
3. 기타
   1) csv 형태의 파일 내, data를 읽어들여 data 처리를 하다보니 data를 조건을 통해 불러들이거나, index를 이용하는 효과 등을 줄 수 없는 것이 아쉬웠습니다.
   2) 최대한 중복된 data 읽기를 배제하고, 전체 프로세스 처리 시간 복잡도를 줄이는 고민에 포커싱을 맞췄습니다.
   3) 읽어들인 Map 형태의 초기 Data의 중복 읽어들임과 변경 가능성을 안정적으로 배제하면서,
      Deep copy 하여 사용함으로써 전체 프로세스 시간복잡도를 줄이기 위해 Gson repository를 pom.xml에 추가하여 활용하였습니다.



### Testcase
1. JUnit 환경에서 Mock 객체를 활용한 API 테스트가 가능하도록 작성하였습니다.

