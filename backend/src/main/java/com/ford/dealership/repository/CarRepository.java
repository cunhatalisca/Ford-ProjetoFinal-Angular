package com.ford.dealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ford.dealership.entity.Car;

public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findByDestaqueTrue();

    List<Car> findTop4ByOrderByUpdatedAtDesc();
}
