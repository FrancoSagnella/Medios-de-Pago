package com.twa.mediospago.controllers;

import com.twa.mediospago.models.PaymentMethod;
import com.twa.mediospago.services.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crud/paymentMethod")
@AllArgsConstructor
public class CrudPaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    /* Ejemplo request para create
    {
        "name": "Decidir"
    }
    */
    @PostMapping("/create")
    public ResponseEntity<PaymentMethod> createPaymentMethod(@Valid @RequestBody PaymentMethod paymentMethod){
        return ResponseEntity.ok(paymentMethodService.save(paymentMethod));
    }

    /* Ejemplo request para update
    {
        "id": 1,
        "name": "Decidir"
    }
     */
    @PostMapping("/update")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@Valid @RequestBody PaymentMethod paymentMethod){
        return ResponseEntity.ok(paymentMethodService.update(paymentMethod));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<PaymentMethod>> getPaymentMethod(@PathVariable("id") Integer id){
        return ResponseEntity.ok(paymentMethodService.get(id));
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<PaymentMethod>> listAllPaymentMethods(){
        return ResponseEntity.ok(paymentMethodService.listAll());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable("id") Integer id){
        return ResponseEntity.ok(paymentMethodService.delete(id));
    }
}
