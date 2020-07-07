package relation.onetomany;

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

            final Member memberA1 = new Member();
            memberA1.setUserName("memberA1");
            entityManager.persist(memberA1);

            final Member memberA2 = new Member();
            memberA2.setUserName("memberA2");
            entityManager.persist(memberA2);

            final Member memberB1 = new Member();
            memberB1.setUserName("memberB1");
            entityManager.persist(memberB1);

            final Member memberB2 = new Member();
            memberB2.setUserName("memberB2");
            entityManager.persist(memberB2);

            final Member memberB3 = new Member();
            memberB3.setUserName("memberB3");
            entityManager.persist(memberB3);

            final Team teamA = new Team();
            teamA.setName("teamA");
            teamA.addMembers(memberA1, memberA2);
            entityManager.persist(teamA);

            final Team teamB = new Team();
            teamB.setName("teamB");
            teamB.addMembers(memberB1, memberB2, memberB3);
            entityManager.persist(teamB);

            System.out.println("--------------------------------------------");
            for (final Member member : new Member[] {memberA1, memberA2, memberB1, memberB2, memberB3}) {
                System.out.println(String.format("member : %s | team : %s", member.toString(), member.getTeam().toString()));
            }
            System.out.println("--------------------------------------------");

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
