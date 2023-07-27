package dev.insaneduck.alphanovetest.repository;

import dev.insaneduck.alphanovetest.entites.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(nativeQuery = false, value = "select b from Book b where b.title=:title")
    Optional<List<Book>> findBooksByTitle(@Param("title") String title);
    @Query(value = "select b from Book b where b.id =:id and b.title=:title")
    Optional<List<Book>> findBooksByAvailabilityAndId(@Param("id")Integer id, @Param("title") String title);
    @Query(nativeQuery = false, value = "select b from Book b where b.title like %:title% and b.series like %:series%")
    Optional<List<Book>> findBooksByTitleAndSeries(@Param("title") String title, @Param("series") String series);
    @Query(nativeQuery = false, value = "select b from Book b where b.title like %:title and b.edition=:edition")
    Optional<List<Book>> findBooksByTitleAndEdition(@Param("title")String title, @Param("edition")Integer edition);
    @Query(nativeQuery = false, value = "select b from Book b where b.title like %:title%")
    Optional<List<Book>> findBooksByTitleContains(@Param("title") String title);
    @Query(nativeQuery = false, value = "select b from Book b where b.author=:author")
    Optional<List<Book>> findBooksByAuthor(@Param("author") String author);
    @Query(nativeQuery = false, value = "select b from Book b where b.author like %:author%")
    Optional<List<Book>> findBooksByAuthorContains(@Param("author") String author);
    @Query(nativeQuery = false, value = "select b from Book b where b.isbn=:isbn")
    Optional<Book> findBookByIsbn(@Param("isbn") String isbn);
    @Query(nativeQuery = false, value = "select b from Book b where b.publicationYear<:publication_year")
    Optional<List<Book>> findBooksByPublicationYearIsBefore(@Param("publication_year") Integer publicationYear);
    @Query(nativeQuery = false, value = "select b from Book b where b.publicationYear=:publication_year")
    Optional<List<Book>> findBooksByPublicationYear(@Param("publication_year") Integer publicationYear);
    @Query(nativeQuery = false, value = "select b from Book b where b.publicationYear>:publication_year")
    Optional<List<Book>> findBooksByPublicationYearIsAfter(@Param("publication_year") Integer publicationYear);
    @Query(nativeQuery = false, value = "select b from Book b where b.genre like %:genre%")
    Optional<List<Book>> findBooksByGenre(@Param("genre") String genre);
    @Query(nativeQuery = false, value = "select b from Book b where b.description=:description")
    Optional<List<Book>> findBooksByDescription(@Param("description")String description);
    @Query(nativeQuery = false, value = "select b from Book b where b.language=:language")
    Optional<List<Book>> findBooksByLanguage(@Param("language")String language);
    @Query(nativeQuery = false, value = "select b from Book b where b.availability=:availability")
    Optional<List<Book>> findBooksByAvailability(@Param("availability")Boolean availability);
    @Query(nativeQuery = false, value = "select b from Book b where b.rating>:rating")
    Optional<List<Book>> findBooksByRatingGreaterThan(@Param("rating") Double rating);
    @Query(nativeQuery = false, value = "select b from Book b where b.series=:series")
    Optional<List<Book>> findBooksBySeries(@Param("series") String series);
}
