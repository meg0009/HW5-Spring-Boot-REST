package com.chivapchichi.controller;

import com.chivapchichi.exception.ResourceNotFoundException;
import com.chivapchichi.model.Purchase;
import com.chivapchichi.repository.PurchaseRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rest")
public class PurchaseController {

    @Autowired
    private PurchaseRepository repository;

    @GetMapping("/purchases")
    public List<Purchase> getAllPurchase() {
        return repository.findAll();
    }

    @GetMapping("/purchases/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Purchase purchase = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found for id: " + id)
        );
        return ResponseEntity.ok().body(purchase);
    }

    @PostMapping("/purchases")
    public Purchase createPurchase(@RequestBody Purchase purchase) {
        return repository.save(purchase);
    }

    @DeleteMapping("/purchases/{id}")
    public Map<String, Boolean> deletePurchase(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Purchase purchase = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found by id: " + id)
        );

        repository.delete(purchase);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PatchMapping("/purchases/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable(value = "id") Integer id, @RequestBody Map<String, String> info)
            throws ResourceNotFoundException {
        Purchase purchase = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found by id: " + id)
        );
        info.forEach(
                (change, value) -> {
                    switch (change) {
                        case "date": purchase.setDate(LocalDate.parse(value)); break;
                        case "seller": purchase.setSeller(Integer.parseInt(value)); break;
                        case "customer": purchase.setCustomer(Integer.parseInt(value)); break;
                        case "book": purchase.setBook(Integer.parseInt(value)); break;
                        case "count": purchase.setCount(Integer.parseInt(value)); break;
                        case "price": purchase.setPrice(Double.parseDouble(value)); break;
                    }
                }
        );
        repository.save(purchase);
        return ResponseEntity.ok().body(purchase);
    }

    @PutMapping("/purchases/{id}")
    public ResponseEntity<Purchase> rewritePurchase(@PathVariable(value = "id") Integer id, @RequestBody Purchase purchase)
            throws ResourceNotFoundException {
        Purchase purchaseInRepository = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Purchase not found for id: " + id)
        );
        purchase.setId(purchaseInRepository.getId());
        repository.save(purchase);
        return ResponseEntity.ok().body(purchase);
    }

    // Вывод всех различных месяцев, когда производились покупки
    @GetMapping("/purchases/months")
    @Operation(summary = "#2, c")
    public List<String> getAllMonths() {
        List<Integer> list = repository.findAllMonths();
        DateFormatSymbols df = new DateFormatSymbols();
        List<String> res = list.stream().map((x) -> df.getMonths()[x - 1]).collect(Collectors.toList());
        return res;
    }

    // Вывести фамилию покупателя и название магазина, где производилась покупка
    @GetMapping("/purchases/customer-name-and-shop-name")
    @Operation(summary = "#4, a")
    public List<Map<String, String>> getCustomerNameAndShopName() {
        return repository.findCustomerNameAndShopName();
    }

    // Вывести дату, фамилию покупателя, скидку, название и количество купленных книг
    @GetMapping("/purchases/date-lastname-discount-bookName-count")
    @Operation(summary = "#4, b")
    public List<Map<String, String>> getDateCNameCDiscountBNameCount() {
        return repository.findDateCNameCDiscountBNameCount();
    }

    //Вывести номер заказа, фамилию покупателя и дату для покупок, в которых было продано книг на сумму не меньшую чем 60000 руб
    @GetMapping("/purchases/purchaseId-lastNameCustomer-datePurchase-priceMore60000")
    @Operation(summary = "#4, a")
    public List<Map<String, String>> getPIdCNamePDatePPriceMore60000() {
        return repository.findPIdCNamePDatePPriceMore60000();
    }

    // Вывести покупки, сделанные покупателем в своем районе не ранее марта месяца. Вывести фамилию покупателя, район, дату. Произвести сортировку
    @GetMapping("/purchases/purchases-made-in-customer-area")
    @Operation(summary = "#4, b")
    public List<Map<String, String>> getPurchasesMakeInCustomerArea() {
        return repository.findPurchasesInCustomerArea();
    }
}
