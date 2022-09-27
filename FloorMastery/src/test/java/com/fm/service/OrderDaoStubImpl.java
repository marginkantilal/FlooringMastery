package com.fm.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fm.dao.OrderDao;
import com.fm.model.Order;
import com.fm.model.Product;
import com.fm.model.States;

public class OrderDaoStubImpl implements OrderDao{


	private static final String DELIMITER = ",";
    public Order tempOrder;
    private static final String STATES_TAX_FILE = "SampleFileData/Data/Taxes.txt";
    private static final String PRODUCT_FILE = "SampleFileData/Data/Products.txt";
    Map<String, States> states = new HashMap<>();
    Map<String, Product> products = new HashMap<>();


    public OrderDaoStubImpl(Order tempOrder) {
		this.tempOrder = tempOrder;
	}



	public OrderDaoStubImpl() {
    	tempOrder = new Order("Mighty Ltd");
    	tempOrder.setOrderNumber(1);
    	tempOrder.setState("WA");
    	tempOrder.setTaxRate(new BigDecimal("9.25"));
    	tempOrder.setProductType("Carpet");
    	tempOrder.setArea(new BigDecimal("132"));
        tempOrder.setCostPerSquareFoot(new BigDecimal("2.25"));
        tempOrder.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        tempOrder.setMaterialCost(new BigDecimal("679.800"));
        tempOrder.setLaborCost(new BigDecimal("627.00"));
        tempOrder.setTax(new BigDecimal("120.879"));
        tempOrder.setTotal(new BigDecimal("1427.70"));
    }



	@Override
	public Order addOrder(Order order, String path) throws OrderPersistenceException {
		if(order.getOrderNumber() == tempOrder.getOrderNumber()){
            return tempOrder;
        } else{
            return null;
        }
	}

	@Override
	public Order editOrder(Order newOrder, String date) throws OrderPersistenceException {
		 if(newOrder.getOrderNumber() == tempOrder.getOrderNumber()){
	            return tempOrder;
	        } else {
	            return null;
	        }
	}

	@Override
	public void exportOrder(String path) throws OrderPersistenceException {

	}

	@Override
	public List<Order> getAllOrder(String date) throws OrderPersistenceException {
		 List<Order> orderList = new ArrayList<>();
	        orderList.add(tempOrder);
	        return orderList;
	}

	@Override
	public Order getOrder(int orderNumber, String date) throws OrderPersistenceException {
		 if(orderNumber == tempOrder.getOrderNumber()){
	            return tempOrder;
	        } else{
	            return null;
	        }
	}

	@Override
	public Map<String, Product> productLog() throws ProductPersistenceException {
		loadProduct();
        return products;	}

	private void loadProduct() throws ProductPersistenceException {
		Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch(FileNotFoundException e){
            throw new ProductPersistenceException("Could not load flooring product data into memory.", e);
        }

        String currentLine = scanner.nextLine();
        Product currentProduct;

        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }

        scanner.close();
	}



	private Product unmarshallProduct(String productAsText) {
		String[] productTokens = productAsText.split(DELIMITER);
        Product product = new Product(productTokens[0]);
        product.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
        product.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));
        return product;
	}



	@Override
	public Order removeOrder(int orderNumber, String date)
			throws OrderPersistenceException, IOException {
		if(orderNumber == tempOrder.getOrderNumber()){
            return tempOrder;
        }
        else{
            return null;
        }	}

	@Override
	public Map<String, States> stateLog() throws StatePersistenceException {
		loadState();
        return states;
	}



	private void loadState() throws StatePersistenceException {
		 Scanner scanner;
	        try{
	            scanner = new Scanner(new BufferedReader(new FileReader(STATES_TAX_FILE)));
	        } catch(FileNotFoundException e){
	            throw new StatePersistenceException("Could not load flooring tax data into memory.", e);
	        }

	        String currentLine = scanner.nextLine();
	        States currentState;

	        while(scanner.hasNextLine()) {
	            currentLine = scanner.nextLine();
	            currentState = unmarshallState(currentLine);
	            states.put(currentState.getStateAbbreviation(), currentState);
	        }

	        scanner.close();
	}

	private States unmarshallState(String stateAsText) {
		String[] stateTokens = stateAsText.split(DELIMITER);
        States state = new States(stateTokens[0]);
        state.setStateName(stateTokens[1]);
        state.setTaxRate(new BigDecimal(stateTokens[2]));
        return state;
	}



}
