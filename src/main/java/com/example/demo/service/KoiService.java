package com.example.demo.service;

import com.example.demo.entity.Koi;
import com.example.demo.repository.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiService {

    @Autowired
    KoiRepository koiRepository;

    public Koi create(Koi koi){
        return koiRepository.save(koi);
    }

    public List<Koi> getAll(){
        return koiRepository.findAll();
    }
}
