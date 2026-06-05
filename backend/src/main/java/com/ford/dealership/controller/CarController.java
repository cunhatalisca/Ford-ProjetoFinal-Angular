package com.ford.dealership.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ford.dealership.entity.Car;
import com.ford.dealership.service.CarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<Car> getAll() {
        return carService.findAll();
    }

    @GetMapping("/featured")
    public List<Car> getFeatured() {
        return carService.findFeatured();
    }

    @GetMapping("/latest")
    public List<Car> getLatest() {
        return carService.findLatest();
    }

    @GetMapping("/{id}")
    public Car getById(@PathVariable String id) {
        return carService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@RequestBody Car car) {
        return carService.create(car);
    }

    @PutMapping("/{id}")
    public Car update(@PathVariable String id, @RequestBody Car car) {
        return carService.update(id, car);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        carService.delete(id);
    }
}
