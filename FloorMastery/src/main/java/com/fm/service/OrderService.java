package com.fm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fm.model.Order;
import com.fm.model.Product;
import com.fm.model.States;

public interface OrderService {

	Order addOrder (Order order, String orderDate)throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException;
	Order editOrderDetails(int orderNumber, Order newOrder, String date) throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException;
	void exportOrderData() throws OrderPersistenceException;
	List<Order> getAllOrders(String date)throws OrderPersistenceException;
	Order getOrder(int orderNumber, String date) throws OrderPersistenceException;
	List<String>getProductList() throws ProductPersistenceException;
	Map<String, Product> getProductMap() throws ProductPersistenceException;
	List<String> getStatesList() throws StatePersistenceException;
	Map<String, States> getStateMap() throws StatePersistenceException;
	void orderTotalPricingCal(Order order)throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException;
	Order removeOrder(int orderNumber, String orderDate) throws OrderPersistenceException, IOException;

}
