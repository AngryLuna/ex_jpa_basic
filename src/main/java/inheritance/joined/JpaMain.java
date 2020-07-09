package inheritance.joined;

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

            final Album album = new Album();
            album.setName("testAlbum");
            album.setPrice(7500);
            album.setArtist("testArtist");
            entityManager.persist(album);

            final Book book = new Book();
            book.setName("testBook");
            book.setPrice(5000);
            book.setAuthor("testAuthor");
            book.setIsbn("testIsbn");
            entityManager.persist(book);

            final Movie movie = new Movie();
            movie.setName("testMovie");
            movie.setPrice(10000);
            movie.setActor("testActor");
            movie.setDirector("testDirector");
            entityManager.persist(movie);

            entityManager.flush();
            entityManager.clear();

            final Album findAlbum = entityManager.find(Album.class, album.getId());
            final Book findBook = entityManager.find(Book.class, book.getId());
            final Movie findMovie = entityManager.find(Movie.class, movie.getId());

            System.out.println(findAlbum.toString());
            System.out.println(findBook.toString());
            System.out.println(findMovie.toString());

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
