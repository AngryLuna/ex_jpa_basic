package valuetype.embedded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(final String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            final Member member1 = new Member();
            member1.setUsername("Member1");
            member1.setHomeAddress(new Address("HomeCity", "HomeStreet", "111-222"));
            member1.setWorkAddress(new Address("WorkCity", "WorkStreet", "333-444"));
            member1.setWorkPeriod(new Period(LocalDateTime.now().minusYears(2L), LocalDateTime.now()));

            // 임베디드 타입으로 지정된 필드값이 null인 경우 매핑된 DB 컬럼에는 모두 null 값으로 insert 됨
            final Member member2 = new Member();
            member2.setUsername("Member2");

            entityManager.persist(member1);
            entityManager.persist(member2);

            final Address oldHomeAddress = member1.getHomeAddress();
            final Address oldWorkAddress = member1.getWorkAddress();
            member1.setHomeAddress(new Address("NewHomeCity", oldHomeAddress.getStreet(), oldHomeAddress.getZipCode()));
            member1.setWorkAddress(new Address("NewWorkCity", oldWorkAddress.getStreet(), oldWorkAddress.getZipCode()));

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
