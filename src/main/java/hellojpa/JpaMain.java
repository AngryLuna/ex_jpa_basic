package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    private static EntityManagerFactory entityManagerFactory;

    public static void main(final String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        System.out.println("-------------------- test1 start --------------------");
        test1();
        System.out.println("--------------------- test1 end ---------------------");

        System.out.println();

        System.out.println("-------------------- test2 start --------------------");
        test2();
        System.out.println("--------------------- test2 end ---------------------");

        System.out.println();

        System.out.println("-------------------- test3 start --------------------");
        test3();
        System.out.println("--------------------- test3 end ---------------------");

        System.out.println();

        System.out.println("-------------------- test4 start --------------------");
        test4();
        System.out.println("--------------------- test4 end ---------------------");

        System.out.println();

        System.out.println("-------------------- test5 start --------------------");
        test5();
        System.out.println("--------------------- test5 end ---------------------");

        entityManagerFactory.close();
    }

    /**
     * CRUD 테스트
     */
    private static void test1() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            // create
            entityManager.persist(new Member(1L, "TestMember1"));
            entityManager.persist(new Member(2L, "TestMember2"));

            // read
            final Member updateMember = entityManager.find(Member.class, 1L);
            final Member deleteMember = entityManager.find(Member.class, 2L);

            // update
            updateMember.setUsername("updatedName1");

            // delete
            entityManager.remove(deleteMember);

            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    /**
     * 쿼리 실행시점 체크
     */
    private static void test2() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            System.out.println("========== before persist ==========");
            entityManager.persist(new Member(100L, "TestName100"));
            entityManager.persist(new Member(200L, "TestName200"));
            System.out.println("========== after persist ==========");

            System.out.println("========== before commit ==========");
            entityTransaction.commit();
            System.out.println("========== after commit ==========");
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    /**
     * 준영속 상태 테스트
     */
    private static void test3() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            // 비영속
            final Member member = new Member(300L, "TestName300");

            // 영속
            entityManager.persist(member);

            // 준영속
            entityManager.detach(member);

            // 커밋
            entityTransaction.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    /**
     * 캐시 조회 테스트
     */
    private static void test4() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            final Member member = new Member(300L, "TestName300");

            entityManager.persist(member);

            // 1차 캐시에서 조회하기 때문에 select 쿼리문 실행하지 않는다
            final Member findMember1 = entityManager.find(Member.class, 300L);
            System.out.println(String.format("[%d] %s", findMember1.getId(), findMember1.getUsername()));

            // 1차 캐시에 없기 때문에 select 쿼리문 실행
            final Member findMember2 = entityManager.find(Member.class, 100L);
            System.out.println(String.format("[%d] %s", findMember2.getId(), findMember2.getUsername()));

            // 위 코드에 의해 검색 후 1차 캐시에 저장 되었기 때문에 select 쿼리문 실행하지 않는다
            final Member findMember3 = entityManager.find(Member.class, 100L);
            System.out.println(String.format("[%d] %s", findMember3.getId(), findMember3.getUsername()));

            // 1차 캐시에 저장된 동일한 객체 (영속 엔티티 동일성 보장)
            System.out.println(findMember2 == findMember3);
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    /**
     * flush 테스트
     */
    private static void test5() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            entityManager.persist(new Member(400L, "TestName400"));

            System.out.println("========== before flush ==========");
            entityManager.flush();
            System.out.println("========== after flush ==========");

            System.out.println("========== before commit ==========");
            entityTransaction.commit();
            System.out.println("========== after commit ==========");
        } catch (final Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
