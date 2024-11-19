package com.example.demo.service;

import com.example.demo.entity.Pet;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public List<Pet> getAllPets(){
        List<Pet> list = petRepository.findAll();
        return list;
    }

    public Pet createPet(Pet pet){
        try{
            Pet newPet = petRepository.save(pet);
            return newPet;
        }catch (Exception e){
            throw new DuplicateEntity("This pet is existed!!");
        }

    }

    public Pet updatePet(Pet pet, long Id){

            Pet oldPet = petRepository.findPetById(Id);
            if(oldPet == null){
                throw new NotFoundException("Not found!");
            }
        try{
            oldPet.setPetId(pet.getPetId());
            oldPet.setWeight(pet.getWeight());
            oldPet.setType(pet.getType());
            return petRepository.save(oldPet);
        }catch (Exception e){
            throw new DuplicateEntity("This pet ID already existed!! Try another one");
        }

    }

}
