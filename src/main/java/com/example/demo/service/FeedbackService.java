package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.Koi;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.forgotPass.FeedbackRequest;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountRepository accountRepository;

    public Feedback createNewFeedback(FeedbackRequest feedbackRequest) {
        Account shop = accountRepository.findById(feedbackRequest.getShopId())
                .orElseThrow(() -> new NotFoundException("Shop not found"));
        Feedback feedback = new Feedback();
        feedback.setContent(feedbackRequest.getContent());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setCustomer(authenticationService.getCurrentAccount());
        feedback.setShop(shop);
        return feedbackRepository.save(feedback);
    }


}
