package com.upload.common.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: clam
 * Date: 2/20/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Listener implements MessageListener {

  public static final AtomicLong counter = new AtomicLong(0L);

  @Override
  public void onMessage(Message message) {
    if (message instanceof TextMessage) {
      long count = counter.incrementAndGet(); 
      System.out.println( "LISTENER - COUNTER: " + count);
    }
    else {
      throw new IllegalArgumentException("Message must be of type TextMessage");
    }
  }
}
