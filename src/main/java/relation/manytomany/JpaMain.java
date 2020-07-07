package relation.manytomany;

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

            final Product productA = new Product();
            productA.setName("productA");
            entityManager.persist(productA);

            final Product productB = new Product();
            productB.setName("productB");
            entityManager.persist(productB);

            final Product productC = new Product();
            productC.setName("productC");
            entityManager.persist(productC);

            final Member memberA = new Member();
            memberA.setUserName("memberA");
            memberA.addProducts(productA, productB);
            entityManager.persist(memberA);

            final Member memberB = new Member();
            memberB.setUserName("memberB");
            memberB.addProducts(productA, productB, productC);
            entityManager.persist(memberB);

            for (final Product product : new Product[] {productA, productB, productC}) {
                System.out.println(String.format("---------- %s ----------", product.toString()));
                for (final Member member : product.getMembers()) {
                    System.out.println(member.toString());
                }
                System.out.println("--------------------------------------");
            }

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
