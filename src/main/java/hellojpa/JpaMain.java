package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(final String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

//            create(entityManager, 2L, "TestMember2");
//            delete(entityManager, 2L);
            update(entityManager, 1L, "UpdatedName1");

            entityTransaction.commit();
        } catch (final Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    private static void create(final EntityManager entityManager, final long id, final String name) {
        final Member member = new Member();
        member.setId(id);
        member.setName(name);

        entityManager.persist(member);
    }

    private static Member read(final EntityManager entityManager, final long id) {
        return entityManager.find(Member.class, id);
    }

    private static void update(final EntityManager entityManager, final long id, final String name) {
        final Member member = read(entityManager, id);

        member.setName(name);
    }

    private static void delete(final EntityManager entityManager, final long id) {
        final Member member = read(entityManager, id);

        entityManager.remove(member);
    }
}
