package jpql.execute;

import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Function {
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
            member1.setTeam(team1);

            final Member member2 = new Member();
            member2.setUserName("Member2");
            member2.setTeam(team1);

            final Member member3 = new Member();
            member3.setUserName("  Member3  ");
            member3.setTeam(team2);

            entityManager.persist(team1);
            entityManager.persist(team2);
            entityManager.persist(team3);
            entityManager.persist(member1);
            entityManager.persist(member2);
            entityManager.persist(member3);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        concat();
        substring();
        trim();
        lowerUpper();
        length();
        locate();
        size();
        customFunction();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void concat() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- concat ----------");
            final List<String> result = entityManager.createQuery("select concat(m.userName, '_test') from Member m", String.class)
                    .getResultList();

            for (final String name : result) {
                System.out.println(name);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void substring() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- substring ----------");
            final List<String> result = entityManager.createQuery("select substring(m.userName, 3, 5) from Member m", String.class)
                    .getResultList();

            for (final String name : result) {
                System.out.println(name);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void trim() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- trim ----------");
            final List<String> result = entityManager.createQuery("select trim(m.userName) from Member m", String.class)
                    .getResultList();

            for (final String name : result) {
                System.out.println(String.format("[%s]", name));
            }
        } finally {
            entityManager.close();
        }
    }

    private static void lowerUpper() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- lower ----------");
            final List<String> result1 = entityManager.createQuery("select lower(m.userName) from Member m", String.class)
                    .getResultList();

            for (final String name : result1) {
                System.out.println(name);
            }

            System.out.println("---------- upper ----------");
            final List<String> result2 = entityManager.createQuery("select upper(m.userName) from Member m", String.class)
                    .getResultList();

            for (final String name : result2) {
                System.out.println(name);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void length() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- length ----------");
            final List<Integer> result1 = entityManager.createQuery("select length(m.userName) from Member m", Integer.class)
                    .getResultList();

            for (final int length : result1) {
                System.out.println(length);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void locate() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- locate ----------");
            final List<Integer> result = entityManager.createQuery("select locate(' Mem', m.userName) from Member m", Integer.class)
                    .getResultList();

            for (final int locate : result) {
                System.out.println(locate);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void size() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- size ----------");
            final List<Integer> result = entityManager.createQuery("select size(t.members) from Team t", Integer.class)
                    .getResultList();

            for (final int size : result) {
                System.out.println(size);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void customFunction() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- customFunction ----------");
            final List<String> result = entityManager.createQuery("select function('group_concat', m.userName) from Member m", String.class)
                    .getResultList();

            for (final String str : result) {
                System.out.println(str);
            }
        } finally {
            entityManager.close();
        }
    }
}
