package com.chivapchichi.repository;

import com.chivapchichi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "select name, price from book", nativeQuery = true)
    List<Map<String, String>> findAllWithNameAndPrice();

    @Query(value = "select name, price from book where name like '%Windows%' or price > 20000 order by name, price desc", nativeQuery = true)
    List<Map<String, String>> findWindowsOrMore20000();

    @Query(value = "select b.* from book as b, purchase as p, shop as s where p.seller=s.id and p.book=b.id and b.storage=s.name and b.count>10 order by b.price", nativeQuery = true)
    List<Book> findBoughtBooksAndCount10();
}
