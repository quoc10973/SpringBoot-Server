package com.example.demo.api;

import com.example.demo.entity.Feedback;
import com.example.demo.model.forgotPass.FeedbackRequest;
import com.example.demo.service.FeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*") //cho phép tất cả code truy cập
@SecurityRequirement(name = "api") //bắt buộc có, nên nhớ
public class FeedbackAPI {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/api/feedback")
    public ResponseEntity createFeedback(@RequestBody FeedbackRequest feedbackRequest){
        Feedback feedback = feedbackService.createNewFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }
}
