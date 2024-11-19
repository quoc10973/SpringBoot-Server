package com.example.demo.api;


import com.example.demo.entity.Pet;
import com.example.demo.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetAPI {
    @Autowired
    private PetService petService;

    @PostMapping("/api/pet")
    public ResponseEntity createPet(@Valid @RequestBody Pet pet) {
        Pet newPet = petService.createPet(pet);
        return  ResponseEntity.ok(newPet);
    }

    @GetMapping("/api/pet")
    public ResponseEntity getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }


    @DeleteMapping("/api/pet/{id}")
    public ResponseEntity<?> deletePet(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
    }
}
