package jpql.execute;

import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class FetchJoin {
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
            member2.setTeam(team1);

            final Member member3 = new Member();
            member3.setUserName("Member3");
            member3.setAge(30);
            member3.setTeam(team2);

            final Member member4 = new Member();
            member4.setUserName("Member4");
            member4.setAge(40);

            entityManager.persist(team1);
            entityManager.persist(team2);
            entityManager.persist(team3);
            entityManager.persist(member1);
            entityManager.persist(member2);
            entityManager.persist(member3);
            entityManager.persist(member4);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        ManyToOne();
        OneToMany();
        paging();

        ENTITY_MANAGER_FACTORY.close();
    }

    private static void ManyToOne() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- ManyToOne ----------");
            final List<Member> result = entityManager.createQuery("select m from Member m inner join fetch m.team", Member.class)
                    .getResultList();

            for (final Member member : result) {
                System.out.println(String.format("%s | %s", member.toString(), member.getTeam().toString()));
            }
        } finally {
            entityManager.close();
        }
    }

    private static void OneToMany() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- OneToMany ----------");
            final List<Team> result = entityManager.createQuery("select distinct t from Team t inner join fetch t.members", Team.class)
                    .getResultList();

            for (final Team team : result) {
                System.out.println(team.toString());
                for (final Member member : team.getMembers()) {
                    System.out.println(String.format("    %s", member.toString()));
                }
            }
        } finally {
            entityManager.close();
        }
    }

    private static void paging() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            System.out.println("---------- ManyToOne Paging ----------");
            // 다대일 fetch join 페이징 사용은 문제 없음
            final List<Member> result1 = entityManager.createQuery("select m from Member m inner join fetch m.team", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (final Member member : result1) {
                System.out.println(String.format("%s | %s", member.toString(), member.getTeam().toString()));
            }

            System.out.println("---------- OneToMany Paging ----------");
            // 일대다 fetch join 페이징 사용시 DB에서는 페이징을 사용하지 않은 전체 레코드 조회
            // 하이버네이트에서 경고 로그 출력하고 메모리상에서 페이징 처리하여 출력 (위험!!!)
            final List<Team> result2 = entityManager.createQuery("select t from Team t inner join fetch t.members", Team.class)
                    .setFirstResult(1)
                    .setMaxResults(1)
                    .getResultList();

            for (final Team team : result2) {
                System.out.println(team.toString());
                for (final Member member : team.getMembers()) {
                    System.out.println(String.format("    %s", member.toString()));
                }
            }

            System.out.println("---------- OneToMany FetchSize ----------");
            // 최초 페이징 검색시 join을 사용하지 않은 결과로 조회하여 페이징
            // 배치 사이즈를 설정하여 lazy 검색시 배치 사이즈만큼 한번에 하위 테이블을 in 검색하는 방법
            // persistence.xml hibernate.default_batch_fetch_size 옵션에 지정하면 글로벌 설정
            // @BatchSize(size = n) 어노테이션으로 특정 엔티티 필드마다 설정 가능
            final List<Team> result3 = entityManager.createQuery("select t from Team t", Team.class)
                    .setFirstResult(1)
                    .setMaxResults(1)
                    .getResultList();

            for (final Team team : result3) {
                System.out.println(team.toString());
                for (final Member member : team.getMembers()) {
                    System.out.println(String.format("    %s", member.toString()));
                }
            }
        } finally {
            entityManager.close();
        }
    }
}
