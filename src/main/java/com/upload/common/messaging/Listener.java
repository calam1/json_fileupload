package com.upload.common.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * User: clam
 * Date: 2/20/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Listener implements MessageListener {
  public static long counter = 0;//this count won't be accurate just gives me a ball park for peace of mind

  @Override
  public void onMessage(Message message) {
    if (message instanceof TextMessage) {
      counter++;
      try {
        System.out.println( "LISTENER - COUNTER: " + counter + " : " + ((TextMessage) message).getText());
      }
      catch (JMSException ex) {
        throw new RuntimeException(ex);
      }
    }
    else {
      throw new IllegalArgumentException("Message must be of type TextMessage");
    }
  }
}
