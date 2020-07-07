package relation.onetoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.concurrent.locks.Lock;

public class JpaMain {
    public static void main(final String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            final Locker lockerA = new Locker();
            lockerA.setName("lockerA");
            entityManager.persist(lockerA);

            final Locker lockerB = new Locker();
            lockerB.setName("lockerB");
            entityManager.persist(lockerB);

            final Member memberA = new Member();
            memberA.setUserName("memberA");
            memberA.changeLocker(lockerA);
            entityManager.persist(memberA);

            final Member memberB = new Member();
            memberB.setUserName("memberB");
            memberB.changeLocker(lockerB);
            entityManager.persist(memberB);

            System.out.println("-------------------------------------------");
            for (final Locker locker : new Locker[] {lockerA, lockerB}) {
                System.out.println(String.format("locker : %s | member : %s", locker.toString(), locker.getMember().toString()));
            }
            System.out.println("-------------------------------------------");

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
