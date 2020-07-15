package jpql.execute;

import jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Bulk {
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

            final Member member3 = new Member();
            member3.setUserName("Member3");
            member3.setAge(30);

            entityManager.persist(member1);
            entityManager.persist(member2);
            entityManager.persist(member3);

            // 이 시점에서 flush
            System.out.println("---------- Update ----------");
            final int result = entityManager.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println(result);

            // 벌크 연산은 영속성 컨텍스트와 무관한 작업
            // 이 시점에서 벌크 연산으로 인해 DB에는 20으로 값이 바뀌었지만 영속성 컨텍스트는 반영이 되지 않아 초기값이 유지
            System.out.println("---------- Print1 ----------");
            System.out.println(entityManager.find(Member.class, member1.getId()));
            System.out.println(entityManager.find(Member.class, member2.getId()));
            System.out.println(entityManager.find(Member.class, member3.getId()));

            entityManager.clear();

            // 영속성 컨텍스트 초기화 이후 재검색 하기때문에 이 시점에서는 변경된 값이 조회
            System.out.println("---------- Print2 ----------");
            System.out.println(entityManager.find(Member.class, member1.getId()));
            System.out.println(entityManager.find(Member.class, member2.getId()));
            System.out.println(entityManager.find(Member.class, member3.getId()));

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        ENTITY_MANAGER_FACTORY.close();
    }
}
