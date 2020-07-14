package jpql.execute;

import jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Random;

public class Paging {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            for (int idx = 1 ; idx <= 100 ; idx++) {
                final Member member = new Member();
                member.setUserName(String.format("Member%d", idx));
                member.setAge(idx);
                entityManager.persist(member);
            }

            final List<Member> result = entityManager.createQuery("select m from Member m order by m.age desc")
                    .setFirstResult(10)
                    .setMaxResults(10)
                    .getResultList();

            for (final Member member : result) {
                System.out.println(member.toString());
            }

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
