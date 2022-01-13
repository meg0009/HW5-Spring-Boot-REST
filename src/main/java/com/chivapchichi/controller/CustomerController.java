package com.chivapchichi.controller;

import com.chivapchichi.exception.ResourceNotFoundException;
import com.chivapchichi.model.Customer;
import com.chivapchichi.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Customer customer = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found for id: " + id)
        );
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @DeleteMapping("/customers/{id}")
    public Map<String, Boolean> deleteShop(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        Customer customer = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found for id: " + id)
        );

        repository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PatchMapping("/customers/{id}")
    public ResponseEntity<Customer> updateShop(@PathVariable(value = "id") Integer id, @RequestBody Map<String, String> info)
            throws ResourceNotFoundException {
        Customer customer = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found for id: " + id)
        );
        info.forEach(
                (change, value) -> {
                    switch (change) {
                        case "lastName": customer.setLastName(value); break;
                        case "area": customer.setArea(value); break;
                        case "discount": customer.setDiscount(Double.parseDouble(value)); break;
                    }
                }
        );
        repository.save(customer);
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> rewriteCustomer(@PathVariable(value = "id") Integer id, @RequestBody Customer customer)
            throws ResourceNotFoundException {
        Customer customerInRepository = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found for id: " + id)
        );
        customer.setId(customerInRepository.getId());
        repository.save(customer);
        return ResponseEntity.ok().body(customer);
    }


    // Вывод всех различных районов, в которых проживают покупатели
    @GetMapping("/customers/areas")
    @Operation(summary = "#2, b")
    public List<String> getAllAreas() {
        return repository.findAllArea();
    }

    // Вывод фамилий и размера скидки всех покупателей, проживающих в Нижегородском районе
    @GetMapping("/customer/nizhegorodskii")
    @Operation(summary = "#3, a")
    public List<Map<String, String>> getAllNizhegorodskii(){
        return repository.findInNArea();
    }
}
