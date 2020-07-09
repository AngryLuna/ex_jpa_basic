package proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class LazyLoadingMain {
    public static void main(final String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            final LazyLoadingTeam team = new LazyLoadingTeam();
            team.setName("testTeam");
            entityManager.persist(team);

            final LazyLoadingMember member = new LazyLoadingMember();
            member.setUserName("testMember");
            member.setTeam(team);
            team.getMembers().add(member);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            System.out.println("---------- find Member ----------");
            final LazyLoadingMember findMember = entityManager.find(LazyLoadingMember.class, member.getId()); // team 정보는 제외한 순수 member 정보만 검색
            System.out.println(findMember.getTeam().getClass()); // 초기화 안된 상태라 팀 정보는 Proxy 객체
            System.out.println(findMember.toString());
            System.out.println("------------ init team ----------");
            System.out.println(findMember.getTeam().toString());
            System.out.println("---------------------------------");

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
