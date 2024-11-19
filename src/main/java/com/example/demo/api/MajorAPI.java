package com.example.demo.api;

import com.example.demo.entity.Major;
import com.example.demo.model.MajorRequest;
import com.example.demo.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class MajorAPI {

    @Autowired
    MajorService majorService;
    @PostMapping("/api/major")
    public ResponseEntity<Major> createMajor(@RequestBody MajorRequest majorRequest) {
        Major major = majorService.createMajor(majorRequest);
        return ResponseEntity.ok(major);
    }
}
