package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티매니저팩토리는 애플리케이션 로딩시점에 딱 하나만 만들어야한다!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//pom.xml의 JPA 이름
        //단위 작업을 할때마다 만들어줘야한다! 고객의 요청이 오면 생겼다 버렸다 한다
        // 그래서 쓰레드간 공유 X
        EntityManager em = emf.createEntityManager();//엔티티 매니저 생성

        //트랜잭션 생성
        EntityTransaction tx = em.getTransaction();
        //트랜잭션 시작
        tx.begin();

        try {
            //객체 생성
            //Member member = new Member();
            //member.setId(1L);
            //member.setName("HelloA");

            //삽입
            //em.persist(member);

            //조회
            //Member findMember = em.find(Member.class, 1L);
            //System.out.println("findMenmber.id = " + findeMember.getId());
            //System.out.println("findMenmber.name = " + findeMember.getName());

            //삭제
            //Member findMember = em.find(Member.class, 1L);
            //em.remove(findeMember);

            //수정
            //Member findMember = em.find(Member.class, 2L);
            //findMember.setName("HelloJPA");

            //JPQL
            //멤버 전체 가져오기
            //List<Member> result1 = em.createQuery("select m from Member as m", Member.class).getResultList();
            //페이징
            //List<Member> result2 = em.createQuery("select m from Member as m", Member.class)
            //        .setFirstResult(5)
            //       .setMaxResults(8)
            //        .getResultList();
            //영속성 컨텍스트
            //1. 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            //2. 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);

            //3. 준영속, 삭제
            //회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
            em.detach(member);
            //객체를 삭제한 상태(실제 DB에서 지우는 것)
            em.remove(member);

            System.out.println("=== AFTER ===");
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
