package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            final Team team = new Team();
            team.setName("TestTeam");

            entityManager.persist(team);

            final TeamMember member = new TeamMember();
            member.setUserName("TestTeamMember");
            member.setTeam(team);

            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            final TeamMember findMember = entityManager.find(TeamMember.class, member.getId());
            final Team findTeam = findMember.getTeam();

            System.out.println(String.format("[%d] %s", findTeam.getId(), findTeam.getName()));

            entityTransaction.commit();
            System.out.println("커밋");
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
            System.out.println("클로즈");
        }

        entityManagerFactory.close();
    }
}
