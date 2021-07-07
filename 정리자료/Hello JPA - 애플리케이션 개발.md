# HELLO JPA - 애플리케이션 개발



## 1. JpaMain 작성

Main 클래스를 생성해주자

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\JpaMain 생성.png)



## 2. 데이터베이스 테이블과 엔티티를 만들어주자

1. h2 데이터베이스에 MEMBER  테이블 생성

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\엔티티생성.png)



2. Member 클래스 생성

![](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\멤버 클래스 생성.png)



## 3. 객체를 INSERT해보자

```java
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티매니저팩토리는 애플리케이션 로딩시점에 딱 하나만 만들어야한다!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//pom.xml의 JPA 이름
        //단위 작업을 할때마다 만들어줘야한다
        EntityManager em = emf.createEntityManager();//엔티티 매니저 생성

        //트랜잭션 생성
        EntityTransaction tx = em.getTransaction();
        //트랜잭션 시작
        tx.begin();

        //객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setName("HelloA");

        //엔티티 저장
        em.persist(member);

        //커밋
        tx.commit();

        em.close();
        emf.close();
    }
}

```

![엔티티저장콘솔창](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\엔티티저장콘솔창.png)

실행 시켰을 때 콘솔창에 이렇게 뜨면 된다!



## 결과 확인

![멤버 삽입확인](C:\Users\multicampus\Desktop\jpa 교재\수업 내용 정리\img\멤버 삽입확인.png)

와우!!! 정말 신기하다 데이터베이스에 들어갔다!!!

SQL을 작성을 안 해줬는데 JPA가 매핑 정보만 보고 쿼리를 만들어서 알아서 들어갔다 

그 이유는 관례적으로 클래스의 이름으로 찾지만 아래와 같이 어노테이션으로  DB에 매핑될 테이블이나 칼럼도 정해줄 수 있다

```java
...
@Entity
@Table(name = "USER") //DB에 매핑될 테이블 이름
public class Member {

    @Id //PK가 뭔지 알려줘야 함
    private Long id;
    @Column(name = "username") //DB에 매핑될 칼럼 이름
    private String name;
...
}

```

## 예외처리를 해주자

```java
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티매니저팩토리는 애플리케이션 로딩시점에 딱 하나만 만들어야한다!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//pom.xml의 JPA 이름
        //단위 작업을 할때마다 만들어줘야한다
        EntityManager em = emf.createEntityManager();//엔티티 매니저 생성

        //트랜잭션 생성
        EntityTransaction tx = em.getTransaction();
        //트랜잭션 시작
        tx.begin();

        try {
            //객체 생성
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");

            //엔티티 저장
            em.persist(member);
            //커밋
            tx.commit();
        }catch (Exception e){
            tx.rollback(); // 예외가 발생시 롤백
        }finally {
            em.close();
        }

        emf.close();
    }
}

```



## 조회 

```java
Member findMember = em.find(Member.class, 1L);
System.out.println("findMenmber.id = " + findeMember.getId());
System.out.println("findMenmber.name = " + findeMember.getName());
```



## 삭제

```java
Member findMember = em.find(Member.class, 1L);
em.remove(findeMember);
```



## 수정 

```java
//수정
Member findMember = em.find(Member.class, 2L);
findMember.setName("HelloJPA");
```

수정이 참 신기하다 그냥 setName으로 setter만 불러줬을 뿐인데도 객체가 수정이 된다!

자바 컬렉션을 다루는 것처럼 설계되어있기 때문이다.

JPA를 통해서 엔티티를 가져오면 JPA가 관리를 한다

JPA가 트랜잭션을 커밋하는 시점에 변경되어있는지 검사를 하고 업데이트 쿼리를 생성해서 보낸다



## 주의!

- 엔티티 매니저 팩토리는 애플리케이션 전체에서 하나만 생성!
- 엔티티 매니저는 스레드간에 공유(고객 요청이 있을 때 생성하고 버리고)
- JPA는 모든 데이터 변경은 트랜잭션 안에서 실행



## JPQL

그래 위에서 객체를 그냥 단순히 하나만 조회하는 건 그렇다 치자

나이가 18살 이상인 모든 회원을 모두 검색하고 싶다면? JPQL을 사용해야한다

JPQL은 데이터베이스 방언에 맞게  객체를 대상으로 객체지향 쿼리라고 생각하면 된다

- 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 무리가 있다 -> 데이터베이스 종속적이게 된다
- 검색을 하려면 결국 SQL이 필요하다
- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어를 제공한다
- SQL 문법과 유사

### JPQL과 SQL의 차이점

JPQL - **엔티티 객체**를 대상으로 쿼리를 날린다

SQL - **데이터베이스 테이블**을 대상으로 쿼리를 날린다



### 어떻게 사용할까?

```java
//JPQL 예시

//멤버 전체 가져오기
List<Member> result1 = em.createQuery("select m from Member as m", Member.class).getResultList();
//페이징
List<Member> result2 = em.createQuery("select m from Member as m", Member.class)
.setFirstResult(5)
.setMaxResults(8)
.getResultList();
```

