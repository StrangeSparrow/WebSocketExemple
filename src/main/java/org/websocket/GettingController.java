package org.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component
public class GettingController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Mess mess) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting("Hello " + mess.getName());
    }

    @Scheduled(fixedRate = 3000)
    public void scheduledMessage() {
        Destination status = Destination.builder()
                .cd("20102")
                .status(true)
                .build();
        template.convertAndSend("/topic/greetings", status);
    }

    @Scheduled(fixedRate = 7000)
    public void scheduledMessage1() {
        Destination status = Destination.builder()
                .cd("70102")
                .status(false)
                .build();
        template.convertAndSend("/topic/additional", status);
    }
}
