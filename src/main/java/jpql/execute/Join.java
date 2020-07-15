package jpql.execute;

import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Join {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            final Team team1 = new Team();
            team1.setName("Team1");

            final Team thetaTeam = new Team();
            thetaTeam.setName("ThetaName");

            final Member member1 = new Member();
            member1.setUserName("Member1");
            member1.setAge(10);
            member1.setTeam(team1);

            final Member member2 = new Member();
            member2.setUserName("Member2");
            member2.setAge(20);

            final Member thetaMember = new Member();
            thetaMember.setUserName("ThetaName");
            thetaMember.setAge(30);
            thetaMember.setTeam(thetaTeam);

            entityManager.persist(team1);
            entityManager.persist(thetaTeam);
            entityManager.persist(member1);
            entityManager.persist(member2);
            entityManager.persist(thetaMember);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        innerJoin();
        outerJoin();
        thetaJoin();
        joinOn();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void innerJoin() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- innerJoin ----------");
            final List<Member> result = entityManager.createQuery("select m from Member m inner join m.team t", Member.class)
                    .getResultList();

            for (final Member member : result) {
                System.out.println(member.toString());
            }
        } finally {
            entityManager.close();
        }
    }

    private static void outerJoin() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- outerJoin ----------");
            final List<Member> result = entityManager.createQuery("select m from Member m left outer join m.team t", Member.class)
                    .getResultList();

            for (final Member member : result) {
                System.out.println(member.toString());
            }
        } finally {
            entityManager.close();
        }
    }

    private static void thetaJoin() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- thetaJoin ----------");
            final Long result = entityManager.createQuery("select count(m) from Member m, Team t where m.userName = t.name", Long.class)
                    .getSingleResult();

            System.out.println(String.format("Count : %d", result));
        } finally {
            entityManager.close();
        }
    }

    private static void joinOn() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- innerJoinOn ----------");
            final List<Member> result1 = entityManager.createQuery("select m from Member m inner join m.team t on t.name = 'Team1'", Member.class)
                    .getResultList();

            for (final Member member : result1) {
                System.out.println(member.toString());
            }

            System.out.println("---------- outerJoinOn ----------");
            final List<Object[]> result2 = entityManager.createQuery("select m, t from Member m left outer join Team t on t.name = m.userName")
                    .getResultList();

            for (final Object[] objects : result2) {
                System.out.println(objects[0] + " | " + objects[1]);
            }
        } finally {
            entityManager.close();
        }
    }
}
