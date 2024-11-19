package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity // để đánh dấu đây là 1 entity lưu xuống database, Data JPA giúp mình làm việc với DB, nếu chưa có => tự tạo dùm table
public class Student {

    //Khóa chính
    @Id //đánh dấu đây là primary key
     @GeneratedValue(strategy = GenerationType.IDENTITY) //auto generate ra cột Id
    long Id;

    String name;


    @Column(unique = true) //duy nhất, không được trùng
    String studentCode;

    float Score;

    boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "major_id")
    @JsonIgnore
    Major major;

    @ManyToMany
            @JoinTable(
                    name = "student_class",
                    joinColumns = @JoinColumn(name = "student_id"),
                    inverseJoinColumns = @JoinColumn(name = "class_id")
            )
    List<ClassEntity> classEntities = new ArrayList<>();


}
