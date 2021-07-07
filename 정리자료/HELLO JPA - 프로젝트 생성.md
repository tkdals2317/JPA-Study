# HELLO JPA - 프로젝트 생성

> JPA를 사용하는 간단한 프로젝트를 만들어보자



## H2 데이터베이스 설치와 실행

- http://www.h2database.com/
- 최고의 실습용  DB
- 가볍다
- 웹용 쿼리 툴 제공
- MySQL, Oracle 데이터베이스 시뮬레이션 기능
- 시퀀스, AUTO INCREMENT 기능 지원

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\h2_오류.png)



시작부터 빨간 창을 마주쳤다 ㅎㅎ!!

존재하지 않는 데이터베이스에 연결하려고 해서 에러가 뜬거같다

해결방법 : https://atoz-develop.tistory.com/entry/H2-Database-%EC%84%A4%EC%B9%98-%EC%84%9C%EB%B2%84-%EC%8B%A4%ED%96%89-%EC%A0%91%EC%86%8D-%EB%B0%A9%EB%B2%95



![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\h2_연결성공.png)

후 연결에 성공했따!

다음으로 인텔리제이에서 프로젝트를 생성해보자

### 1. 메이븐으로 프로젝트 생성

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\프로젝트생성1.png)

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\프로젝트생성2.png)



### 2. pom.xml 에서 dependency 추가해주기

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>jpa-basic</groupId>
    <artifactId>ex1-hello-jpa</artifactId>
    <version>1.0.0</version>
    <dependencies>
        <!-- JPA 하이버네이트 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.3.10.Final</version>
        </dependency>
        <!-- H2 데이터베이스 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.199</version>
        </dependency>
    </dependencies>
</project>
```



### 3. JPA 설정하기

persistence.xml 만들기

- JPA 설정파일
- /META-INF/persistence.xml 위치(위치가 중요하다!!!resources폴더안에 META-INF 폴더를 만들고 파일 생성)
- persistence-unit name으로 이름 지정
- javax.persistence로 시작 : JPA 표준 속성
- hibernavte로 시작 : 하이버네이트 전용속성

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\jpa설정파일.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello"> <!--jpa 이름은 뭘 쓸꺼야-->
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/> <!--데이터베이스 접근 정보, 오라클이면 오라클-->
            <property name="javax.persistence.jdbc.user" value="sa"/> <!--user name-->
            <property name="javax.persistence.jdbc.password" value=""/> <!--password-->
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/> <!--접근 url-->
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/> <!--중요!!!! hibernate.dialect 속성에 지정-->
            
            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/> <!-- 쿼리 출력 -->
            <property name="hibernate.format_sql" value="true"/> <!-- 이쁘게 포매팅 -->
            <property name="hibernate.use_sql_comments" value="true"/> <!-- 쿼리가 왜 나왔는지 출력 -->
            
            <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
        </properties>
    </persistence-unit>
</persistence>
```

`<persistence-unit name="hello">` : JPA 이름

`<property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>` : 데이터베이스 접근 정보, 오라클이면 오라클

`<property name="javax.persistence.jdbc.user" value="sa"/> ` : 사용자 이름

`<property name="javax.persistence.jdbc.password" value=""/>` : 비밀번호

`<property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>` : 접근 url

`property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>` : 아주 중요! hibernate.dialect 속성에 지정(JPA가 사용할 데이터베이스 방언을 지정)

>**데이터베이스 방언** 
>
>- JPA는 특정 데이터베이스에 종속 X, MySql에서 오라클로 바뀌어도 바뀌어야 함!
>- 각각 데이터베이스가 제공하는 SQL 문법과 함수는 조금씩 다름
>  - 가변 문자 : MySQL은 VARCHAR, Oracle은 VARCHAR2
>  - 문자열 자르는 함수 : SQL은 SUBSTRING(), Oracle은 SUBSTR()
>  - 페이징 : MySQL은 LIMIT, Oracle은 ROWNUM
>- 방언 : SQL 표준을 지키지 않는 특정 데이터베이스만의 고유의 기능
>
>위 코드는 H2 데이터베이스 방언을 사용해서 개발해! JPA는 아 그래 그럼 그걸로 번역할게를 설정해주는 코드이다
>
>​	• H2 : org.hibernate.dialect.H2Dialect
>​	• Oracle 10g : org.hibernate.dialect.Oracle10gDialect
>​	• MySQL : org.hibernate.dialect.MySQL5Dialect
>
> 하이버네이트는 40가지 이상의 데이터베이스 방언 지원

