package jpql.execute;

import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class Path {
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

            final Member member1 = new Member();
            member1.setUserName("Member1");
            member1.setAge(10);
            member1.setTeam(team1);

            final Member member2 = new Member();
            member2.setUserName("Member2");
            member2.setAge(20);
            member2.setTeam(team1);

            final Member member3 = new Member();
            member3.setUserName("Member3");
            member3.setAge(30);

            entityManager.persist(team1);
            entityManager.persist(team2);
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

        singleValuePath();
        collectionValuePath();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void singleValuePath() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- singleValuePath ----------");
            // 단일 값 연관 경로의 경우 묵시적 inner join 발생, 탐색 가능
            final List<String> result = entityManager.createQuery("select m.team.name from Member m", String.class)
                    .getResultList();

            for (final String name : result) {
                System.out.println(name);
            }
        } finally {
            entityManager.close();
        }
    }

    private static void collectionValuePath() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- collectionValuePath ----------");
            // 컬렉션 값 연관 경로의 경우 묵시적 inner join 발생, 탐색 불가능
            final Collection result = entityManager.createQuery("select t.members from Team t", Collection.class)
                    .getResultList();

            result.forEach(member -> System.out.println(member.toString()));
        } finally {
            entityManager.close();
        }
    }
}
