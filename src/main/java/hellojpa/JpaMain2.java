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
            team.setName("TestTeam1");

            entityManager.persist(team);

            final TeamMember member = new TeamMember();
            member.setUserName("TestTeamMember1");
            member.setTeamId(team.getId());

            entityManager.persist(member);

            final TeamMember findMember = entityManager.find(TeamMember.class, member.getId());
            final Team findTeam = entityManager.find(Team.class, findMember.getTeamId());

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
