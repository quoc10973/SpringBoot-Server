package com.example.demo.model;

import com.example.demo.entity.Student;
import lombok.Data;

import java.util.List;

@Data
public class StudentResponse {
    List<Student> content;
    int pageNumbers;
    int totalElements;
    int totalPages;
}

