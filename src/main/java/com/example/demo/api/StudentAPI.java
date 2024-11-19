package com.example.demo.api;

import com.example.demo.entity.Student;
import com.example.demo.exception.AccountException;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.StudentRequest;
import com.example.demo.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController //đánh dấu đây là controller api
@CrossOrigin("*") //cho phép tất cả code truy cập
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('TEACHER')")
public class StudentAPI {
    //thêm 1 sing viên mới
    // /api/student  => POST

    @Autowired
    StudentService studentService;

    @PostMapping("/api/student")
    public ResponseEntity createStudent(@Valid @RequestBody StudentRequest student){
        //@RequestBody lấy dữ liệu Student từ Front End để create, khi mình mark @RequestBody thì FE phải buộc gửi 1 object Student
           StudentRequest newStudent = studentService.createNewStudent(student);
           return ResponseEntity.ok(newStudent);
    }

    @GetMapping("/api/student")
    public ResponseEntity getAllStudent(@RequestParam int page, @RequestParam(defaultValue = "3") int size){
        return ResponseEntity.ok(studentService.getAllStudent(page, size));
    }

    @PutMapping("/api/student/{id}")
    public ResponseEntity updateStudent(@Valid @RequestBody Student student, @PathVariable long id ){
        Student newStudent = studentService.updateStudent(student,id);
        return ResponseEntity.ok(newStudent);
    }

    @DeleteMapping("/api/student/{id}")
    public ResponseEntity deleteStudent(@PathVariable long id ){
        Student newStudent = studentService.deleteStudent(id);
        return ResponseEntity.ok(newStudent);

    }
}
