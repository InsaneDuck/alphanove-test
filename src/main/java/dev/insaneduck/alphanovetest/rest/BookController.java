package dev.insaneduck.alphanovetest.rest;

import dev.insaneduck.alphanovetest.entites.Book;
import dev.insaneduck.alphanovetest.exception.ResourceNotFoundException;
import dev.insaneduck.alphanovetest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    BookRepository bookRepository;
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable(name = "id") Integer id){
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Resource with ID " + id + " not found"));
    }
}
