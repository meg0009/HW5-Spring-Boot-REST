package com.chivapchichi.repository;

import com.chivapchichi.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "select distinct date_part('month', date) from purchase", nativeQuery = true)
    List<Integer> findAllMonths();

    @Query(value = "select c.last_name, s.name from purchase as p, customer as c, shop as s where p.seller=s.id and p.customer=c.id", nativeQuery = true)
    List<Map<String, String>> findCustomerNameAndShopName();

    @Query(value = "select p.date, c.last_name, c.discount, b.name, p.count from purchase as p, customer as c, book as b where p.customer=c.id and p.book=b.id", nativeQuery = true)
    List<Map<String, String>> findDateCNameCDiscountBNameCount();

    @Query(value = "select p.id, c.last_name, p.date from purchase as p, customer as c where p.customer=c.id and p.price>=60000", nativeQuery = true)
    List<Map<String, String>> findPIdCNamePDatePPriceMore60000();

    @Query(value = "select c.last_name, c.area, p.date from purchase as p, customer as c, shop as s where p.seller=s.id and p.customer=c.id and date_part('month', date)>=3 and c.area=s.area order by c.last_name", nativeQuery = true)
    List<Map<String, String>> findPurchasesInCustomerArea();
}
