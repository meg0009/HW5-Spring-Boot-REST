package com.chivapchichi.repository;

import com.chivapchichi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select distinct area from customer", nativeQuery = true)
    List<String> findAllArea();

    @Query(value = "select last_name, discount from customer where area='Нижегородский'", nativeQuery = true)
    List<Map<String, String>> findInNArea();
}
