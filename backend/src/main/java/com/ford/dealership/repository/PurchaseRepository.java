package com.ford.dealership.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ford.dealership.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {
}
