package jpql.execute;

import jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class NamedQuery {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            final Member member1 = new Member();
            member1.setUserName("Member1");

            final Member member2 = new Member();
            member2.setUserName("Member2");

            entityManager.persist(member1);
            entityManager.persist(member2);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        test();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void test() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- Test ----------");
            final Member result = entityManager.createNamedQuery("Member.findByUserName", Member.class)
                    .setParameter("userName", "Member1")
                    .getSingleResult();

            System.out.println(result);
        } finally {

        }
    }
}
