package com.ford.dealership.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ford.dealership.dto.PurchaseRequest;
import com.ford.dealership.dto.PurchaseResponse;
import com.ford.dealership.dto.StatusUpdateRequest;
import com.ford.dealership.service.PurchaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse create(@Valid @RequestBody PurchaseRequest request, Authentication authentication) {
        // authentication.getName() == email (subject do JWT)
        return purchaseService.create(authentication.getName(), request);
    }

    @GetMapping
    public List<PurchaseResponse> getAll() {
        return purchaseService.findAll();
    }

    @PutMapping("/{id}/status")
    public PurchaseResponse updateStatus(@PathVariable String id, @Valid @RequestBody StatusUpdateRequest request) {
        return purchaseService.updateStatus(id, request.getStatus());
    }
}
