package com.ford.dealership.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ford.dealership.dto.PurchaseRequest;
import com.ford.dealership.dto.PurchaseResponse;
import com.ford.dealership.entity.Car;
import com.ford.dealership.entity.Purchase;
import com.ford.dealership.entity.User;
import com.ford.dealership.exception.ResourceNotFoundException;
import com.ford.dealership.repository.PurchaseRepository;
import com.ford.dealership.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private static final String STATUS_PENDENTE = "pendente";

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CarService carService;

    @Transactional
    public PurchaseResponse create(String userEmail, PurchaseRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado: " + userEmail));

        // Decrementa o estoque (valida disponibilidade) e usa o carro atualizado como snapshot.
        Car car = carService.decrementStock(request.getCarId());

        Purchase purchase = Purchase.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .user(user)
                .car(car)
                .selectedColor(request.getSelectedColor())
                .payment(request.getPayment())
                .purchaseDate(Instant.now())
                .status(STATUS_PENDENTE)
                .build();

        return PurchaseResponse.from(purchaseRepository.save(purchase));
    }

    public List<PurchaseResponse> findAll() {
        return purchaseRepository.findAll().stream()
                .map(PurchaseResponse::from)
                .toList();
    }

    public PurchaseResponse updateStatus(String id, String status) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra nao encontrada: " + id));
        purchase.setStatus(status);
        return PurchaseResponse.from(purchaseRepository.save(purchase));
    }
}
