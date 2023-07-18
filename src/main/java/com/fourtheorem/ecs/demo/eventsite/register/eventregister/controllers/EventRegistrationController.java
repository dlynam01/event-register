package com.fourtheorem.ecs.demo.eventsite.register.eventregister.controllers;

import com.fourtheorem.ecs.demo.eventsite.register.eventregister.payment.NotifyForPayment;
import com.fourtheorem.ecs.demo.eventsite.register.eventregister.repositories.DynamoDbRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RequestMapping("register")
@RestController
public class EventRegistrationController {

    @Autowired
    DynamoDbRepo repo;

    @Autowired
    NotifyForPayment payment;

    @PostMapping("event/{eventId}/date/{date}")
    public String registerForEvent(@PathVariable("eventId") String eventId,
                                   @PathVariable("date") String date) {
        log.info("Registering for event {}",eventId);
        int memberId = 10;
        String registerForEvent = repo.registerForEvent(eventId, memberId, date);
        String paymentMessageId = payment.processPaymentCommand(memberId, eventId);
        return String.format("%s - messageId: %s", registerForEvent, paymentMessageId);
    }

    private String getDate() {
        Date date=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
