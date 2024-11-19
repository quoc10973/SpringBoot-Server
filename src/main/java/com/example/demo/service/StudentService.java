package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Student;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.StudentRequest;
import com.example.demo.model.StudentResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //đánh dấu cho spring biết đây là lớp xử lý logic
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    public StudentRequest createNewStudent(StudentRequest studentRequest){
        try{
            Student student = modelMapper.map(studentRequest, Student.class);
            //thong qua duoc filter roi
            //luu lai
            Account accountRequest = authenticationService.getCurrentAccount();
            student.setAccount(accountRequest);
            studentRepository.save(student);
            return studentRequest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public StudentResponse getAllStudent(int page, int size){
        Page studentPage = studentRepository.findAll(PageRequest.of(page, size)); //lấy tất cả lên
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setContent(studentPage.getContent());
        studentResponse.setPageNumbers(studentPage.getNumber());
        studentResponse.setTotalElements(studentPage.getNumberOfElements());
        studentResponse.setTotalPages(studentPage.getTotalPages());
        return studentResponse;

    }

    public Student updateStudent(Student student, long Id) {
        //find student có id cần update
        Student oldStudent = studentRepository.findStudentById(Id);

            if (oldStudent == null) {
                throw new NotFoundException("Student not found");
            }
        try {
            oldStudent.setName(student.getName());
            oldStudent.setStudentCode(student.getStudentCode());
            oldStudent.setScore(student.getScore());
            return studentRepository.save(oldStudent);
        } catch (Exception e) {
            throw new DuplicateEntity("Student Code already existed! Try again!");

        }
    }

        public Student deleteStudent(long Id){
            Student oldStudent = studentRepository.findStudentById(Id);
            if(oldStudent == null){
                throw new NotFoundException("Student not found");
            }
            oldStudent.setDeleted(true);
            return studentRepository.save(oldStudent);
        }
}
