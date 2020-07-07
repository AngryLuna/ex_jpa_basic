package relation.manytoone;

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

            final Team teamA = new Team();
            teamA.setName("teamA");
            entityManager.persist(teamA);

            final Team teamB = new Team();
            teamB.setName("teamB");
            entityManager.persist(teamB);

            final Member memberA1 = new Member();
            memberA1.setUserName("memberA1");
            memberA1.changeTeam(teamA);
            entityManager.persist(memberA1);

            final Member memberA2 = new Member();
            memberA2.setUserName("memberA2");
            memberA2.changeTeam(teamA);
            entityManager.persist(memberA2);

            final Member memberB1 = new Member();
            memberB1.setUserName("memberB1");
            memberB1.changeTeam(teamB);
            entityManager.persist(memberB1);

            final Member memberB2 = new Member();
            memberB2.setUserName("memberB2");
            memberB2.changeTeam(teamB);
            entityManager.persist(memberB2);

            final Member memberB3 = new Member();
            memberB3.setUserName("memberB3");
            memberB3.changeTeam(teamB);
            entityManager.persist(memberB3);

            System.out.println(String.format("---------- %s ----------", teamA.toString()));
            for (final Member member : teamA.getMembers()) {
                System.out.println(member.toString());
            }
            System.out.println("-----------------------------------");

            System.out.println(String.format("---------- %s ----------", teamB.toString()));
            for (final Member member : teamB.getMembers()) {
                System.out.println(member.toString());
            }
            System.out.println("-----------------------------------");

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
