package com.fifa.fifaWorldCup_NotificationService.service;

import com.fifa.fifaWorldCup_NotificationService.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got Message from order-placed topic {}", orderPlacedEvent);

        //Send email to the customer
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("fifaWorldCupTicketSupport@gmail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail()); //orderPlacedEvent.getEmail().toString()
            messageHelper.setSubject(String.format("Your Order With Order Number %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Hi %s %s
                    
                    Your order with order number %s is now placed successfully.
                    
                    Enjoy the beautiful game.
                    
                    Best Regards,
                    FIFA World Cup Organizing Committee                    
                    """, orderPlacedEvent.getFirstName(), orderPlacedEvent.getLastName(), orderPlacedEvent.getOrderNumber()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notification email sent!");
        } catch (MailException e) {
            throw new RuntimeException("Exception occurred when sending mail to " + orderPlacedEvent.getEmail() + "for order number: "+ orderPlacedEvent.getOrderNumber(), e);
        }
    }

}
