package com.chivapchichi.repository;

import com.chivapchichi.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query(value = "select name from shop where area='Сормовский' or area='Советский'", nativeQuery = true)
    List<String> findInSormovoAndSovetskii();

    @Query(value = "select s.* from shop as s, purchase as p, customer as c where p.seller=s.id and p.customer=c.id and s.area<>'Автозаводский' and (c.discount>=0.1 and c.discount<=0.15)", nativeQuery = true)
    List<Shop> findWhereBuyBooks();
}
