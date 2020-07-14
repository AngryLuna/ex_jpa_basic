package jpql.execute;

import jpql.Member;

import javax.persistence.*;
import java.util.List;

public class Basic {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            final Member member1 = new Member();
            member1.setUserName("Member1");
            member1.setAge(10);

            final Member member2 = new Member();
            member2.setUserName("Member2");
            member2.setAge(20);

            entityManager.persist(member1);
            entityManager.persist(member2);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        test1();
        test2();

        ENTITY_MANAGER_FACTORY.close();
    }

    /**
     * TypedQuery, Query를 사용한 다중, 단일 검색 테스트
     */
    private static void test1() {
        System.out.println("=============== test1 ===============");

        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            // 반환 타입이 명확한 경우 TypedQuery
            final TypedQuery<Member> query1 = entityManager.createQuery("select m from Member m", Member.class);
            final TypedQuery<Member> query2 = entityManager.createQuery("select m from Member m where m.id is null", Member.class);
            final TypedQuery<Long> query3 = entityManager.createQuery("select sum(m.age) from Member m", Long.class);

            // 반환 타입이 명확하지 않은 경우 Query
            final Query query4 = entityManager.createQuery("select m.userName, m.age from Member m");

            // 결과 값이 여러개인 경우 getResultList 사용
            final List<Object[]> result1 = query4.getResultList();
            System.out.println("---------- result1 ----------");
            for (final Object[] objects : result1) {
                System.out.println(String.format("id : %s | userName : %s", objects[0], objects[1]));
            }
            System.out.println("-----------------------------");

            // 결과 값이 없는 경우 빈 리스트 리턴
            final List<Member> result2 = query2.getResultList();
            System.out.println("---------- result2 ----------");
            for (final Member member : result2) {
                System.out.println(member.toString());
            }
            System.out.println("-----------------------------");

            // 결과 값이 하나인 경우 getSingleResult 사용
            final long result3 = query3.getSingleResult();
            System.out.println(String.format("---------- result3 : %d ----------", result3));

            // 결과 값이 없는 경우 NoResultException 발생
            try {
                query2.getSingleResult();
            } catch (final NoResultException e) {
                System.out.println("!!!!!!!!!! NoResultException 발생 !!!!!!!!!!");
                e.printStackTrace();
            }

            // 결과 값이 여러개인 경우 NonUniqueResultException 발생
            try {
                query1.getSingleResult();
            } catch (final NonUniqueResultException e) {
                System.out.println("!!!!!!!!!! NonUniqueResultException 발생 !!!!!!!!!!");
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * 파라미터 바인딩 테스트
     */
    private static void test2() {
        System.out.println("=============== test2 ===============");

        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            // 파라미터명으로 바인딩
            final Member result1 = entityManager.createQuery("select m from Member m where m.userName = :userName", Member.class)
                    .setParameter("userName", "Member1")
                    .getSingleResult();

            System.out.println(String.format("result1 : %s", result1.toString()));

            // 순서로 바인딩
            final Member result2 = entityManager.createQuery("select m from Member m where m.userName = ?1 and m.age = ?2", Member.class)
                    .setParameter(1, "Member2")
                    .setParameter(2, 20)
                    .getSingleResult();

            System.out.println(String.format("result2 : %s", result2.toString()));

        } finally {
            entityManager.close();
        }
    }
}
