package dev.insaneduck.alphanovetest.repository;

import java.util.Optional;

public class BookQueryHelper {

    protected static String getBookQuery(Integer id, Optional<String> title, Optional<String> available) {
        String query = "select b from Book b where b.id =:" + id;
        if (title.isPresent()) {
            query = query + " and b.title=:" + title;
        }
        if (available.isPresent()) {
            query = query + " and b.available=:" + available;
        }
        return query;
    }
}
