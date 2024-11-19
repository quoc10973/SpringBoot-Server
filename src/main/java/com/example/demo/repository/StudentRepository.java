package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Tìm 1 thằng = id của nó
    // find + tên thực thể + by + (type data)
    // findStudentById(long id)
    Student findStudentById(long id);

    //lấy danh sách những th student có isDeleted = false;
    List<Student> findStudentsByIsDeletedFalse();

    Page<Student> findAll(Pageable pageable);
}
