package proxy.cascade;

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

            final Parent parent = new Parent();
            parent.setName("testParent");

            final Child child1 = new Child();
            child1.setName("child1");

            final Child child2 = new Child();
            child2.setName("child2");

            parent.addChild(child1);
            parent.addChild(child2);

            entityManager.persist(parent); // CASCADE 설정이 되어 있어서 Child도 같이 persist 됨

            entityManager.flush();
            entityManager.clear();

            final Parent findParent = entityManager.find(Parent.class, parent.getId());
            findParent.getChildren().remove(0); // orphanRemoval 설정이 되어 있어서 컬렉션에서 제거하는 경우 delete 쿼리가 실행 됨

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
