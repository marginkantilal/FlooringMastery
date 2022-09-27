package com.fm.app;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fm.controller.FlooringMasteryController;
import com.fm.service.OrderPersistenceException;
import com.fm.service.ProductPersistenceException;
import com.fm.service.StatePersistenceException;


public class App {
    public static void main(String[] args) throws OrderPersistenceException, ProductPersistenceException, StatePersistenceException, IOException {

    		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    		FlooringMasteryController controller = context.getBean("controller", FlooringMasteryController.class);

    	    controller.run();

    }
}
