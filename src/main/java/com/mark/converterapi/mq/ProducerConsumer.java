package com.mark.converterapi.mq;

import java.util.Objects;
import java.util.UUID;
import javax.jms.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerConsumer {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public <T> String request(T request, String queue) {
        try {
            String msg = objectMapper.writer().writeValueAsString(request);
            Message message = jmsTemplate.execute(new SessionCallbackImpl(msg, queue, jmsTemplate.getDestinationResolver()), true);
            return ((TextMessage) Objects.requireNonNull(message)).getText();
        } catch (JsonProcessingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }


    private record SessionCallbackImpl(String msg, String queue, DestinationResolver destinationResolver) implements SessionCallback<Message> {
        private static final int TIMEOUT = 5000;

        public Message doInJms(final Session session) throws JMSException {
            MessageConsumer consumer = null;
            MessageProducer producer = null;
            try {
                final String correlationId = UUID.randomUUID().toString();
                final Destination requestQueue = destinationResolver.resolveDestinationName(session, queue + "request", false);
                final Destination replyQueue = destinationResolver.resolveDestinationName(session, queue + "response", false);
                consumer = session.createConsumer(replyQueue, "JMSCorrelationID = '" + correlationId + "'");
                final TextMessage textMessage = session.createTextMessage(msg);
                textMessage.setJMSCorrelationID(correlationId);
                textMessage.setJMSReplyTo(replyQueue);
                producer = session.createProducer(requestQueue);
                producer.send(requestQueue, textMessage);
                Message received = consumer.receive(TIMEOUT);
                if (received instanceof TextMessage receivedTextMessage) {
                    String text = receivedTextMessage.getText();
                    System.out.println("receivedTextMessage.getText() = " + text);
                }
                return received;
            } finally {
                JmsUtils.closeMessageConsumer(consumer);
                JmsUtils.closeMessageProducer(producer);
            }
        }
    }
}