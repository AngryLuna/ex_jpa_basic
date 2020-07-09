package proxy;

import javax.persistence.*;

import static org.hibernate.Hibernate.initialize;

public class ProxyBasicMain {
    public static void main(final String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            final Member member = new Member();
            member.setUserName("testName");

            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            findMember(entityManager, member.getId());
            entityManager.clear();

            getReferenceMember(entityManager, member.getId());
            entityManager.clear();

            proxyUtil(entityManagerFactory.getPersistenceUnitUtil(), entityManager, member.getId());

            getReferenceDetachInitFail(entityManager, member.getId());

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private static void findMember(final EntityManager entityManager, final Long id) {
        System.out.println("------------ find start ------------");
        final Member findMember = entityManager.find(Member.class, id); // 여기서 쿼리 날려서 데이터 조회
        System.out.println("---------- toString start ----------");
        System.out.println(findMember.toString());
        System.out.println("------------------------------------");
    }

    private static void getReferenceMember(final EntityManager entityManager, final Long id) {
        System.out.println("---------- gerReferenceMember start ----------");
        final Member refMember = entityManager.getReference(Member.class, id); // 여기서는 파라미터로 넘긴 id만 세팅된 더미 객체를 리턴
        System.out.println(refMember.getClass()); // 실제 Member 클래스가 아니라 Member 클래스를 상속받은 HibernateProxy 객체
        System.out.println("--------------- toString start ---------------");
        System.out.println(refMember.toString()); // 실제로 해당 필드에 접근시 쿼리 날려서 데이터 조회
        System.out.println(refMember.getClass()); // 초기화 후에 Member 클래스로 바뀌는게 아님 (클래스 비교시 instance of 사용해야 함)
        System.out.println("----------------------------------------------");
    }

    private static void proxyUtil(final PersistenceUnitUtil util, final EntityManager entityManager, final Long id) {
        // 초기화 체크 및 강제 초기화
        final Member refMember = entityManager.getReference(Member.class, id);
        System.out.println(String.format("isLoaded : %b", util.isLoaded(refMember)));
        initialize(refMember); // JPA 표준은 아니고 하이버네이트에서 지원
        System.out.println(String.format("isLoaded : %b", util.isLoaded(refMember)));

    }

    private static void getReferenceDetachInitFail(final EntityManager entityManager, final Long id) {
        final Member refMember = entityManager.getReference(Member.class, id);
        entityManager.detach(refMember); // 준영속 상태로 설정
        System.out.println(refMember.toString()); // 필드 접근시 영속성 컨텍스트를 통해 초기화 해야 하는데 준영속 상태라 초기화가 불가능한 상태
    }
}
