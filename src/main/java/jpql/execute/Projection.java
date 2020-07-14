package jpql.execute;

import jpql.*;

import javax.persistence.*;
import java.util.List;

public class Projection {
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
            member2.setTeam(team2);

            final Order order = new Order();
            order.setOrderAmount(1);
            order.setAddress(new Address("City1", "Street1", "111-222"));

            entityManager.persist(team1);
            entityManager.persist(team2);
            entityManager.persist(member1);
            entityManager.persist(member2);
            entityManager.persist(order);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityProjection();
        entityJoinProjection();
        embeddedTypeProjection();
        scalarTypeProjection();

        ENTITY_MANAGER_FACTORY.close();
    }

    /**
     * 특정 엔티티 검색
     */
    private static void entityProjection() {
        System.out.println("---------- entityProjection ----------");

        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            final List<Member> result = entityManager.createQuery("select m from Member m", Member.class)
                    .getResultList();

            for (final Member member : result) {
                System.out.println(member.toString());
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * 특정 엔티티에 매핑된 다른 엔티티 검색
     */
    private static void entityJoinProjection() {
        System.out.println("---------- entityJoinProjection ----------");

        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            final List<Team> result = entityManager.createQuery("select m.team from Member m", Team.class)
                    .getResultList();

            for (final Team team : result) {
                System.out.println(team.toString());
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * 특정 엔티티의 임베디드 타입 검색
     */
    private static void embeddedTypeProjection() {
        System.out.println("---------- embeddedTypeProjection ----------");

        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            final List<Address> result = entityManager.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            for (final Address address : result) {
                System.out.println(address.toString());
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * 여러 값 검색
     */
    private static void scalarTypeProjection() {
        System.out.println("---------- scalarTypeProjection ----------");

        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            // Object[] 타입으로 검색
            System.out.println("!!!!! Object[] !!!!!");
            final List<Object[]> result1 = entityManager.createQuery("select m.userName, m.age from Member m")
                    .getResultList();

            for (final Object[] objects : result1) {
                System.out.println(String.format("%s [%d]", objects[0], objects[1]));
            }

            // new 명령어로 검색
            System.out.println("!!!!! MemberDto !!!!!");
            final List<MemberDto> result2 = entityManager.createQuery("select new jpql.MemberDto(m.userName, m.age) from Member m", MemberDto.class)
                    .getResultList();

            for (final MemberDto dto : result2) {
                System.out.println(dto.toString());
            }
        } finally {
            entityManager.close();
        }
    }
}
