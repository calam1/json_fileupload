package com.upload.common.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: clam
 * Date: 2/20/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sender {

  private JmsTemplate jmsTemplate;
  private Queue queueName;

  public static final AtomicLong counter = new AtomicLong(0L);

  public void sendMessage(final String message) {
    jmsTemplate.send(queueName, new MessageCreator() {

      @Override
      public Message createMessage(Session arg0) throws JMSException {
        long count = counter.incrementAndGet();
       	System.out.println("Sender count is: " + count);	
        TextMessage msg = arg0.createTextMessage();
        msg.setText(message);
        return msg;
      }
    });
  }

  public JmsTemplate getJmsTemplate() {
    return jmsTemplate;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public Queue getQueueName() {
    return queueName;
  }

  public void setQueueName(Queue queue) {
    this.queueName = queue;
  }

}
