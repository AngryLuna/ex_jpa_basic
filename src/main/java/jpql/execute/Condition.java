package jpql.execute;

import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Condition {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hello");

    public static void main(final String[] args) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {
            final Team team1 = new Team();
            team1.setName("Team1");

            final Team team2 = new Team();
            team2.setName("Team2");

            final Team team3 = new Team();
            team3.setName("Team3");

            final Member member1 = new Member();
            member1.setUserName("Member1");
            member1.setAge(10);
            member1.setTeam(team1);

            final Member member2 = new Member();
            member2.setUserName("Member2");
            member2.setAge(20);
            member2.setTeam(team2);

            final Member member3 = new Member();
            member3.setUserName("Member3");
            member3.setAge(60);
            member3.setTeam(team3);

            entityManager.persist(team1);
            entityManager.persist(team2);
            entityManager.persist(team3);
            entityManager.persist(member1);
            entityManager.persist(member2);
            entityManager.persist(member3);
            entityManager.persist(new Member());

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        caseCondition();
        coalesce();
        nullIf();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void caseCondition() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- case basic ----------");
            final String query1 =
                    "select m.userName, m.age, " +
                            "case when m.age <= 10 then '학생 요금' " +
                            "     when m.age >= 60 then '경로 요금' " +
                            "     else '일반 요금' " +
                            "end " +
                    "from Member m";

            final List<Object[]> result1 = entityManager.createQuery(query1)
                    .getResultList();

            for (final Object[] objects : result1) {
                for (final Object object : objects) {
                    System.out.print(object + " ");
                }
                System.out.println();
            }

            System.out.println("---------- case exact matching ----------");
            final String query2 =
                    "select t.name, " +
                            "case t.name" +
                            "    when 'Team1' then '인센티브 110%' " +
                            "    when 'Team2' then '인센티브 120%' " +
                            "    else '인센티브 105%' " +
                            "end " +
                            "from Team t";

            final List<Object[]> result2 = entityManager.createQuery(query2)
                    .getResultList();

            for (final Object[] objects : result2) {
                for (final Object object : objects) {
                    System.out.print(object + " ");
                }
                System.out.println();
            }
        } finally {
            entityManager.close();
        }
    }

    private static void coalesce() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- coalesce ----------");
            final List<String> result = entityManager.createQuery("select coalesce(m.userName, 'No Name') from Member m", String.class)
                    .getResultList();

            for (final String name : result) {
                System.out.println(name);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void nullIf() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- nullIf ----------");
            final List<String> result1 = entityManager.createQuery("select nullif(m.userName, 'Member1') from Member m", String.class)
                    .getResultList();

            for (final String name : result1) {
                System.out.println(name);
            }
        } finally {
            entityManager.close();
        }
    }
}
