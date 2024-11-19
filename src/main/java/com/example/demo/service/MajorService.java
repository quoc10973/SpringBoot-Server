package com.example.demo.service;

import com.example.demo.entity.Major;
import com.example.demo.model.MajorRequest;
import com.example.demo.repository.MajorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MajorService {
    @Autowired
    MajorRepository majorRepository;

    @Autowired
    ModelMapper modelMapper;

    public Major createMajor(MajorRequest majorRequest) {
        try{
            Major major = modelMapper.map(majorRequest, Major.class);
           return majorRepository.save(major);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
