package valuetype.collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(final String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            final Member member = new Member();
            member.setUsername("Member1");
            member.setHomeAddress(new Address("HomeCity1", "HomeStreet1", "111-222"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("햄버거");

            member.getAddressHistory().add(new Address("OldCity1", "OldStreet1", "333-444"));
            member.getAddressHistory().add(new Address("OldCity2", "OldStreet2", "555-666"));

            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            System.out.println("===========================================");
            final Member findMember = entityManager.find(Member.class, member.getId()); // 컬렉션 필드는 지연로딩, 순수 Member 필드값만 검색
            System.out.println(findMember.getAddressHistory().getClass());

            System.out.println("---------- addressHistory ----------");
            for (final Address address : findMember.getAddressHistory()) { // 실제로 값을 사용하는 시점에 select 쿼리 실행
                System.out.println(address.toString());
            }
            System.out.println("------------------------------------");

            System.out.println("---------- favoriteFoods ----------");
            for (final String food : findMember.getFavoriteFoods()) { // 실제로 값을 사용하는 시점에 select 쿼리 실행
                System.out.println(food);
            }
            System.out.println("-----------------------------------");

            System.out.println("===========================================");

            // 주소 수정
            final Address oldHomeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("NewHomeCity", oldHomeAddress.getStreet(), oldHomeAddress.getZipCode()));

            // 선호 음식 수정
            findMember.getFavoriteFoods().remove("햄버거");
            findMember.getFavoriteFoods().remove("피자");
            findMember.getFavoriteFoods().remove("양식"); // 기존 Set에 없는 데이터
            findMember.getFavoriteFoods().add("한식");

            // 주소 내역 수정
            findMember.getAddressHistory().remove(new Address("OldCity1", "OldStreet1", "333-444")); // 기존 내역 삭제
            findMember.getAddressHistory().add(new Address("NewCity", "NewStreet", "555-666")); // 신규 내역 추가

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
