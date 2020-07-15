package jpql.execute;

import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class UseEntity {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    private static final Team TEAM_1 = new Team();
    private static final Team TEAM_2 = new Team();
    private static final Member MEMBER_1 = new Member();
    private static final Member MEMBER_2 = new Member();

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            TEAM_1.setName("Team1");
            TEAM_2.setName("Team2");

            MEMBER_1.setUserName("Member1");
            MEMBER_1.setTeam(TEAM_1);
            MEMBER_2.setUserName("Member2");
            MEMBER_2.setTeam(TEAM_2);

            entityManager.persist(TEAM_1);
            entityManager.persist(TEAM_2);
            entityManager.persist(MEMBER_1);
            entityManager.persist(MEMBER_2);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        primaryKey();
        foreignKey();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void primaryKey() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- PrimaryKey 1 ----------");
            final Member result1 = entityManager.createQuery("select m from Member m where m = :memberEntity", Member.class)
                    .setParameter("memberEntity", MEMBER_1)
                    .getSingleResult();

            System.out.println(result1);

            System.out.println("---------- PrimaryKey 2 ----------");
            final Member result2 = entityManager.createQuery("select m from Member m where m.id = :memberId", Member.class)
                    .setParameter("memberId", MEMBER_2.getId())
                    .getSingleResult();

            System.out.println(result2);
        } finally {
            entityManager.close();
        }
    }

    private static void foreignKey() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- Foreign Key 1 ----------");
            final List<Member> result1 = entityManager.createQuery("select m from Member m where m.team = :teamEntity", Member.class)
                    .setParameter("teamEntity", TEAM_1)
                    .getResultList();

            for (final Member member : result1) {
                System.out.println(member);
            }

            System.out.println("---------- Foreign Key 2 ----------");
            final List<Member> result2 = entityManager.createQuery("select m from Member m where m.team.id = :teamId", Member.class)
                    .setParameter("teamId", TEAM_2.getId())
                    .getResultList();

            for (final Member member : result2) {
                System.out.println(member);
            }
        } finally {
            entityManager.close();
        }
    }
}
