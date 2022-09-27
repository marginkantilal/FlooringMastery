package com.fm.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fm.model.Order;
import com.fm.model.Product;
import com.fm.model.States;
import com.fm.service.OrderPersistenceException;
import com.fm.service.ProductPersistenceException;
import com.fm.service.StatePersistenceException;

public interface OrderDao {


		Order addOrder(Order order, String path) throws OrderPersistenceException;
	    Order editOrder(Order newOrder, String path) throws OrderPersistenceException;

	    void exportOrder(String path) throws OrderPersistenceException;
	    List<Order> getAllOrder(String path) throws OrderPersistenceException;
	    Order getOrder(int orderNumber, String path) throws OrderPersistenceException;

	    Map<String, Product> productLog() throws ProductPersistenceException;
	    Order removeOrder(int orderNumber, String path) throws OrderPersistenceException, IOException;
	    Map<String, States> stateLog() throws StatePersistenceException;
}
