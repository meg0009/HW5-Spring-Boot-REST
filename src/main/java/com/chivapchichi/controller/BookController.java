package com.chivapchichi.controller;

import com.chivapchichi.exception.ResourceNotFoundException;
import com.chivapchichi.model.Book;
import com.chivapchichi.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest")
public class BookController {

    @Autowired
    private BookRepository repository;

    @GetMapping("/books")
    public List<Book> getAllBook() {
        return repository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Book book = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found for id: " + id)
        );
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        return repository.save(book);
    }

    @DeleteMapping("/books/{id}")
    public Map<String, Boolean> deleteBook(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Book book = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found for id: " + id)
        );

        repository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Integer id, @RequestBody Map<String, String> info)
            throws ResourceNotFoundException {
        Book book = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found for id: " + id)
        );
        info.forEach(
                (change, value) -> {
                    switch (change) {
                        case "name": book.setName(value); break;
                        case "price": book.setPrice(Double.parseDouble(value)); break;
                        case "storage": book.setStorage(value); break;
                        case "count": book.setCount(Integer.parseInt(value)); break;
                    }
                }
        );
        repository.save(book);
        return ResponseEntity.ok().body(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> rewriteBook(@PathVariable(value = "id") Integer id, @RequestBody Book book)
            throws ResourceNotFoundException {
        Book bookInRepository = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found for id: " + id)
        );
        book.setId(bookInRepository.getId());
        repository.save(book);
        return ResponseEntity.ok().body(book);
    }

    // Вывод всех различных названий и стоимостей книг
    @GetMapping("/books/names-and-prices")
    @Operation(summary = "#2, a")
    public List<Map<String, String>> getAllWithNameAndPrice() {
        return repository.findAllWithNameAndPrice();
    }

    // Вывод названий и стоимости книг, в которых встречается слово Windows, или стоящих более 20000 руб. Вывод результатов организовать по названию и убыванию цены книг
    @GetMapping("/books/windows-or-more-20000")
    @Operation(summary = "#3, c")
    public List<Map<String, String>> getWindowsOrMore20000() {
        return repository.findWindowsOrMore20000();
    }

    // Вывести данные по покупке книг (название, район складирования, количество), приобретенных в районе складирования и содержащихся в запасе более 10 штук. Включить данные о стоимости и отсортировать по возрастанию
    @GetMapping("/books/bought-books-with-count-more-10")
    @Operation(summary = "#4, d")
    public List<Book> getBoughtBooks() {
        return repository.findBoughtBooksAndCount10();
    }
}
