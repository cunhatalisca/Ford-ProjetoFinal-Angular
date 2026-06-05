package com.ford.dealership.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ford.dealership.entity.Car;
import com.ford.dealership.exception.BusinessException;
import com.ford.dealership.exception.ResourceNotFoundException;
import com.ford.dealership.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(String id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carro nao encontrado: " + id));
    }

    public List<Car> findFeatured() {
        return carRepository.findByDestaqueTrue();
    }

    public List<Car> findLatest() {
        return carRepository.findTop4ByOrderByUpdatedAtDesc();
    }

    public Car create(Car car) {
        if (car.getId() == null || car.getId().isBlank()) {
            car.setId(UUID.randomUUID().toString().substring(0, 8));
        }
        Instant now = Instant.now();
        if (car.getCreatedAt() == null) {
            car.setCreatedAt(now);
        }
        car.setUpdatedAt(now);
        if (car.getDestaque() == null) {
            car.setDestaque(false);
        }
        return carRepository.save(car);
    }

    public Car update(String id, Car car) {
        Car existing = findById(id);
        car.setId(existing.getId());
        car.setCreatedAt(existing.getCreatedAt());
        car.setUpdatedAt(Instant.now());
        return carRepository.save(car);
    }

    public void delete(String id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Carro nao encontrado: " + id);
        }
        carRepository.deleteById(id);
    }

    /** Decrementa o estoque ao concluir uma compra. */
    public Car decrementStock(String id) {
        Car car = findById(id);
        int estoque = car.getEstoque() == null ? 0 : car.getEstoque();
        if (estoque <= 0) {
            throw new BusinessException("Carro sem estoque disponivel: " + car.getNome());
        }
        car.setEstoque(estoque - 1);
        car.setUpdatedAt(Instant.now());
        return carRepository.save(car);
    }
}
