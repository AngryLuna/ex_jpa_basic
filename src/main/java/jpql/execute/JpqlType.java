package jpql.execute;

import jpql.Member;
import jpql.MemberType;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlType {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            final Member member1 = new Member();
            member1.setUserName("Member1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);

            final Member member2 = new Member();
            member2.setUserName("Member2");
            member2.setAge(20);
            member2.setType(MemberType.USER);

            final Member member3 = new Member();
            member3.setUserName("Member3");
            member3.setAge(30);
            member3.setType(MemberType.ADMIN);

            entityManager.persist(member1);
            entityManager.persist(member2);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        type();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void type() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- type ----------");

            final List<Object[]> result = entityManager.createQuery("select m.userName, m.type, 'Hello', 1, 2L, 3.4D, 5.6F, true " +
                    "from Member m " +
                    "where m.type = :memberType " +
                    "or m.type = jpql.MemberType.ADMIN") // 하드코딩으로 enum 타입을 사용하는 경우는 전체 패키지경로를 등록해야 사용 가능
                    .setParameter("memberType", MemberType.USER)
                    .getResultList();

            for (final Object[] objects : result) {
                for (final Object object : objects) {
                    System.out.print(object + " ");
                }
                System.out.println();
            }
        } finally {
            entityManager.close();
        }
    }
}
