package com.chivapchichi.controller;

import com.chivapchichi.exception.ResourceNotFoundException;
import com.chivapchichi.model.Shop;
import com.chivapchichi.repository.ShopRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest")
public class ShopController {

    @Autowired
    ShopRepository repository;

    @GetMapping("/shops")
    public List<Shop> getAllShops() {
        return repository.findAll();
    }

    @GetMapping("/shops/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Shop shop = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found for id: " + id)
        );
        return ResponseEntity.ok().body(shop);
    }

    @PostMapping("/shops")
    public Shop createShop(@RequestBody Shop shop) {
        return repository.save(shop);
    }

    @DeleteMapping("/shops/{id}")
    public Map<String, Boolean> deleteShop(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException{
        Shop shop = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found for id: " + id)
        );

        repository.delete(shop);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PatchMapping("/shops/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable(value = "id") Integer id, @RequestBody Map<String, String> info)
            throws ResourceNotFoundException {
        Shop shop = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found for id: " + id)
        );
        info.forEach(
                (change, value) -> {
                    switch (change) {
                        case "name": shop.setName(value); break;
                        case "area": shop.setArea(value); break;
                        case "commissions": shop.setCommissions(Double.parseDouble(value)); break;
                    }
                }
        );
        repository.save(shop);
        return ResponseEntity.ok().body(shop);
    }

    @PutMapping("/shops/{id}")
    public ResponseEntity<Shop> rewriteShop(@PathVariable(value = "id") Integer id, @RequestBody Shop shop)
            throws ResourceNotFoundException {
        Shop shopInRepository = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shop not found for id: " + id)
        );
        shop.setId(id);
        repository.save(shop);
        return ResponseEntity.ok().body(shop);
    }

    // Вывод названия магазинов Сормовского или Советского районов
    @GetMapping("/shops/sormovskii-and-sovetskii")
    @Operation(summary = "#3, b")
    public List<String> getAllSormovoAndSovetskii() {
        return repository.findInSormovoAndSovetskii();
    }

    // Вывести магазины, расположенные в любом районе, кроме Автозаводского, где покупали книги те, у кого скидка от 10 до 15 %
    @GetMapping("/shops/where-buy-books")
    @Operation(summary = "#4, c")
    public List<Shop> getWhereBuyBooks() {
        return repository.findWhereBuyBooks();
    }
}
