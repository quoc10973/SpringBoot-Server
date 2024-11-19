package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
