package com.upload.common.messaging;

import com.upload.common.database.ProductDAO;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: clam
 * Date: 2/20/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Listener implements MessageListener {

    public static final AtomicLong counter = new AtomicLong(0L);//just for manual testing
    private ProductDAO productDAO;
    private ExecutorService executorService;

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void onMessage(final Message message) {
        if (message instanceof TextMessage) {

            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return productDAO.add(((TextMessage) message).getText());
                }
            });

            try {
                long count = counter.incrementAndGet();
                System.out.println("Count is: " + count + " Future.get() returns: " + future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
