package com.ford.dealership.config;

import java.io.InputStream;
import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.dealership.entity.Car;
import com.ford.dealership.entity.Payment;
import com.ford.dealership.entity.Purchase;
import com.ford.dealership.entity.User;
import com.ford.dealership.model.Cor;
import com.ford.dealership.repository.CarRepository;
import com.ford.dealership.repository.PurchaseRepository;
import com.ford.dealership.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Carrega os dados iniciais (usuarios, carros e compras) a partir de seed-data.json
 * — copia do antigo db/users.json do json-server — quando o banco esta vazio.
 * As senhas em texto puro do arquivo sao re-hasheadas com BCrypt na carga.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PurchaseRepository purchaseRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            log.info("Banco ja populado — seed ignorado.");
            return;
        }

        JsonNode root;
        try (InputStream is = new ClassPathResource("seed-data.json").getInputStream()) {
            root = objectMapper.readTree(is);
        }

        seedUsers(root.path("users"));
        seedCars(root.path("cars"));
        seedPurchases(root.path("purchases"));

        log.info("Seed concluido: {} usuarios, {} carros, {} compras.",
                userRepository.count(), carRepository.count(), purchaseRepository.count());
    }

    private void seedUsers(JsonNode users) {
        for (JsonNode node : users) {
            String rawPassword = node.path("password").asText("");
            User user = User.builder()
                    .id(node.path("id").asText())
                    .name(node.path("name").asText())
                    .email(node.path("email").asText())
                    .phone(node.path("phone").asText(null))
                    .password(passwordEncoder.encode(rawPassword))
                    .role(node.path("role").asText("usuario"))
                    .checkboxTermos(node.path("checkboxTermos").asBoolean(false))
                    .checkboxNewsLetter(node.path("checkboxNewsLetter").asBoolean(false))
                    .build();
            userRepository.save(user);
        }
    }

    private void seedCars(JsonNode cars) {
        for (JsonNode node : cars) {
            Car car = objectMapper.convertValue(node, Car.class);
            if (car.getCreatedAt() == null) {
                car.setCreatedAt(car.getUpdatedAt() != null ? car.getUpdatedAt() : Instant.now());
            }
            if (car.getUpdatedAt() == null) {
                car.setUpdatedAt(car.getCreatedAt());
            }
            carRepository.save(car);
        }
    }

    private void seedPurchases(JsonNode purchases) {
        for (JsonNode node : purchases) {
            String userId = node.path("user").path("id").asText(null);
            String carId = node.path("car").path("id").asText(null);

            User user = userId != null ? userRepository.findById(userId).orElse(null) : null;
            Car car = carId != null ? carRepository.findById(carId).orElse(null) : null;
            if (user == null || car == null) {
                log.warn("Compra {} ignorada: usuario/carro referenciado nao encontrado.",
                        node.path("id").asText());
                continue;
            }

            Cor selectedColor = node.has("selectedColor")
                    ? objectMapper.convertValue(node.get("selectedColor"), Cor.class)
                    : null;
            Payment payment = node.has("payment")
                    ? objectMapper.convertValue(node.get("payment"), Payment.class)
                    : null;
            Instant purchaseDate = node.hasNonNull("purchaseDate")
                    ? Instant.parse(node.get("purchaseDate").asText())
                    : Instant.now();

            Purchase purchase = Purchase.builder()
                    .id(node.path("id").asText())
                    .user(user)
                    .car(car)
                    .selectedColor(selectedColor)
                    .payment(payment)
                    .purchaseDate(purchaseDate)
                    .status(node.path("status").asText("pendente"))
                    .build();
            purchaseRepository.save(purchase);
        }
    }
}
