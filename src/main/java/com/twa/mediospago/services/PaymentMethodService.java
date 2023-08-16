package com.twa.mediospago.services;

import com.twa.mediospago.exceptions.NotFoundException;
import com.twa.mediospago.models.PaymentMethod;
import com.twa.mediospago.repositories.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod save(PaymentMethod paymentMethod) {
        if(paymentMethod.getId() != null)
            paymentMethod.setId(null);
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod update(PaymentMethod paymentMethod) {
        if(paymentMethod.getId() == null)
            throw new RuntimeException("No se ha enviado el id del PaymentMethod");
        return paymentMethodRepository.save(paymentMethod);
    }

    public String delete(Integer id) {
        Optional<PaymentMethod> paymentMethod = get(id);
        paymentMethodRepository.delete(paymentMethod.get());
        return "Se ha eliminado el paymentMethod!";
    }

    public Optional<PaymentMethod> get(Integer id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        if(paymentMethod.isEmpty())
            throw new NotFoundException("No existe un paymentMethod con ese id");
        return paymentMethod;
    }

    public List<PaymentMethod> listAll() {
        return paymentMethodRepository.findAll();
    }
}
