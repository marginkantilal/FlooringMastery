package com.fm.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.fm.model.Order;
import com.fm.model.Product;
import com.fm.model.States;
import com.fm.service.OrderPersistenceException;
import com.fm.service.ProductPersistenceException;
import com.fm.service.StatePersistenceException;



public class OrderDaoImpl implements OrderDao {

	    private static final String DELIMITER = ",";
	    private static final String TAX_FILE = "SampleFileData/Data/Taxes.txt";
	    private static final String PRODUCT_FILE = "SampleFileData/Data/Products.txt";
	    private String ORDER_FILE;



	    Map<String, States> states = new HashMap<>();
	    Map<String, Product> products = new HashMap<>();
	    List<Order> ordersAsList = new ArrayList<>();



	    @Override
	    public Order addOrder(Order order, String path) throws OrderPersistenceException  {
	        ORDER_FILE = path;
	        try {
	            loadOrdersAsListForAddingOrder();
	        } catch (IOException ex) {
	            throw new OrderPersistenceException("Error: Cannot open file.");
	        }
	        order.setOrderNumber(setOrderNumber());
	        Order prevOrder = null;
	        if(ordersAsList.add(order)){
	            prevOrder = order;
	        }

	        try {
	            writeOrder(ordersAsList);
	        } catch (IOException ex) {
	            throw new OrderPersistenceException("Error: Cannot open file.");
	        }
	        return prevOrder;
	    }

	    private String dateFormat(String date) {
			 String newDate = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4);
		     return newDate;
		}



		@Override
		public Order editOrder(Order newOrder, String path) throws OrderPersistenceException {
			 ORDER_FILE = path;
		        try {
		            loadOrdersAsList();
		        } catch (IOException ex) {
		            throw new OrderPersistenceException("Error: Cannot open file.");
		        }

		        Order prevOrder = null;
		        for(Order oldOrder : ordersAsList){
		            if(oldOrder.getOrderNumber() == newOrder.getOrderNumber()){
		                prevOrder = ordersAsList.set(ordersAsList.indexOf(oldOrder), newOrder);
		            }
		        }

		        try {
		            writeOrderFromList(ordersAsList);
		        } catch (IOException ex) {
		            throw new OrderPersistenceException("Error: Cannot open file.");
		        }
		        return prevOrder;

		}

		private void emptyFileHeader() throws OrderPersistenceException,IOException {
			PrintWriter writeFile;
	        writeFile = new PrintWriter(new FileWriter(ORDER_FILE));
	        writeFile.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
	        		+ "MaterialCost,LaborCost,Tax,Total");
	        writeFile.flush();
	        writeFile.close();

		}



		@Override
		public void exportOrder(String path) throws OrderPersistenceException {
			String orderPath = "SampleFileData\\Orders";
	        String javaOrderPath = orderPath.replace("\\", "/");
	        File directoryPath = new File(javaOrderPath);
	        String[] orderFile = directoryPath.list();
	        ordersAsList.clear();
	        List<String> textData = new ArrayList<>();

	        for (String element : orderFile) {
	            String[] fileName1 = element.split("_");
	            String str1 = fileName1[1];
	            String[] fileName2 = str1.split("\\.");
	            String date = fileName2[0];
	            String formattedDate = dateFormat(date);
	            ORDER_FILE = "SampleFileData/Orders/Orders_" + date + ".txt";
	            try {
	                loadOrdersAsList();
	            } catch (IOException ex) {
	                throw new OrderPersistenceException("Error: Cannot open file.");
	            }
	            for(Order order : ordersAsList){
	                String textOrder = marshallOrderForExport(order, formattedDate);
	                textData.add(textOrder);
	            }
	        }

	        ORDER_FILE = path;
	        try {
	            writeOrderFromListForExport(textData);
	        } catch (IOException ex) {
	            throw new OrderPersistenceException("Error: Cannot open file.");
	        }

		}



		@Override
		public List<Order> getAllOrder(String path) throws OrderPersistenceException {

			ORDER_FILE = path;
	        try {
	            loadOrdersAsList();
	        } catch (IOException ex) {
	            throw new OrderPersistenceException("Error: Cannot open file.");
	        }
	        List<Order> orderList = new ArrayList<>();
	        for(Order order : ordersAsList){
	            orderList.add(order);
	        }

	        return orderList;
		}

		@Override
	    public Order getOrder(int orderNumber, String path) throws OrderPersistenceException{
	        ORDER_FILE = path;
	        try {
	            loadOrdersAsList();
	        } catch (IOException ex) {
	            throw new OrderPersistenceException("Error: Cannot open file.");
	        }

	        for(Order order : ordersAsList){
	            if(order.getOrderNumber() == orderNumber){
	                return order;
	            }
	        }
	        return null;
	    }

		private void loadOrdersAsList() throws OrderPersistenceException, IOException {
	    	 ordersAsList.clear();
	         Scanner scanner;
	         try{
	             scanner = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
	         } catch(FileNotFoundException e){
	             throw new OrderPersistenceException("Error: there is no file for the date");
	         }
	         String currentLine;
	         Order currentOrder;

	         try{
	             currentLine = scanner.nextLine();
	         } catch(NoSuchElementException e){
	             emptyFileHeader();
	         }
	         while(scanner.hasNextLine()) {
	             currentLine = scanner.nextLine();
	             currentOrder = unmarshallOrder(currentLine);
	             ordersAsList.add(currentOrder);
	         }

	         scanner.close();

		}

		private void loadOrdersAsListForAddingOrder() throws OrderPersistenceException, IOException { {
			 ordersAsList.clear();
		        Scanner scanner;
		        try{
		            scanner = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
		        } catch(FileNotFoundException e){
		            emptyFileHeader();
		            scanner = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
		        }
		        Order currentOrder;
		        String currentLine;
		        try{
		            currentLine = scanner.nextLine();
		        } catch(NoSuchElementException e){
		            emptyFileHeader();
		        }
		        while(scanner.hasNextLine()) {
		            currentLine = scanner.nextLine();
		            currentOrder = unmarshallOrder(currentLine);
		            ordersAsList.add(currentOrder);
		        }

		        scanner.close();
		    }



		}

		public void loadProduct() throws ProductPersistenceException {
			 Scanner scanner;
		        try{
		            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
		        } catch(FileNotFoundException e){
		            throw new ProductPersistenceException("Could not load product data into memory.", e);
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

		private void loadState() throws StatePersistenceException {
			Scanner scanner;
	        try{
	            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
	        } catch(FileNotFoundException e){
	            throw new StatePersistenceException("Could not load states data into memory.", e);
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

		//Translate data from object in memory into a text file.
		 private String marshallOrderForExport(Order order, String date) throws OrderPersistenceException{

		        String orderAsText = order.getOrderNumber() + DELIMITER;

		        orderAsText += order.getCustomerName() + DELIMITER;

		        orderAsText += order.getState() + DELIMITER;

		        orderAsText += order.getTaxRate() + DELIMITER;

		        orderAsText += order.getProductType() + DELIMITER;

		        orderAsText += order.getArea() + DELIMITER;

		        orderAsText += order.getCostPerSquareFoot() + DELIMITER;

		        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;

		        orderAsText += order.getMaterialCost() + DELIMITER;

		        orderAsText += order.getLaborCost() + DELIMITER;

		        orderAsText += order.getTax() + DELIMITER;

		        orderAsText += order.getTotal() + DELIMITER;

		        orderAsText += date;

		        return orderAsText;
		    }

		 private String marshallOrder(Order order) throws OrderPersistenceException{
			 String orderAsText = order.getOrderNumber() + DELIMITER;

		        orderAsText += order.getCustomerName() + DELIMITER;

		        orderAsText += order.getState() + DELIMITER;

		        orderAsText += order.getTaxRate() + DELIMITER;

		        orderAsText += order.getProductType() + DELIMITER;

		        orderAsText += order.getArea() + DELIMITER;

		        orderAsText += order.getCostPerSquareFoot() + DELIMITER;

		        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;

		        orderAsText += order.getMaterialCost() + DELIMITER;

		        orderAsText += order.getLaborCost() + DELIMITER;

		        orderAsText += order.getTax() + DELIMITER;

		        orderAsText += order.getTotal();

		        return orderAsText;

		 }

				@Override
		public Map<String, Product> productLog() throws ProductPersistenceException {
			loadProduct();
			return products;
		}

		@Override
		public Order removeOrder(int orderNumber, String path) throws OrderPersistenceException, IOException {
			ORDER_FILE = path;
	        try {
	            loadOrdersAsList();
	        } catch (IOException ex) {
	            throw new OrderPersistenceException("Error: Cannot open file.");
	        }
	        Order removedOrder = null;

	        for(int i = 0; i < ordersAsList.size(); i++){
	            if(ordersAsList.get(i).getOrderNumber() == orderNumber){
	                removedOrder = ordersAsList.get(i);
	                ordersAsList.remove(i);
	            }
	        }

	        writeOrderFromList(ordersAsList);
	        return removedOrder;
		}

		private int setOrderNumber() throws OrderPersistenceException {
			if(ordersAsList.isEmpty()){
	            return 1;
	        }
	        else{
	            int maxNum = 0;
	            for(Order order : ordersAsList){
	                if(order.getOrderNumber() > maxNum){
	                    maxNum = order.getOrderNumber();
	                }
	            }
	            return maxNum + 1;
	        }
		}

		@Override
		public Map<String, States> stateLog() throws StatePersistenceException {
			 loadState();
		     return states;
		}

		private Order unmarshallOrder(String orderAsText) {
			 String[] orderTokens = orderAsText.split(DELIMITER);
		        Order order = new Order(orderTokens[1]);
		        order.setOrderNumber(Integer.parseInt(orderTokens[0]));
		        order.setState(orderTokens[2]);
		        order.setTaxRate(new BigDecimal(orderTokens[3]));
		        order.setProductType(orderTokens[4]);
		        order.setArea(new BigDecimal(orderTokens[5]));
		        order.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
		        order.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
		        order.setMaterialCost(new BigDecimal(orderTokens[8]));
		        order.setLaborCost(new BigDecimal(orderTokens[9]));
		        order.setTax(new BigDecimal(orderTokens[10]));
		        order.setTotal(new BigDecimal(orderTokens[11]));
		        return order;					}

		private Product unmarshallProduct(String productAsText){
	        String[] productTokens = productAsText.split(DELIMITER);
	        Product product = new Product(productTokens[0]);
	        product.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
	        product.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));
	        return product;
	    }


	    private States unmarshallState(String currentLine) {
			String[] stateTokens = currentLine.split(DELIMITER);
	        States state = new States(stateTokens[0]);
	        state.setStateName(stateTokens[1]);
	        state.setTaxRate(new BigDecimal(stateTokens[2]));
	        return state;
		}

		private void writeOrder(List<Order> ordersAsList)throws OrderPersistenceException, IOException {
			 PrintWriter scanner;
		        try {
		            scanner = new PrintWriter(new FileWriter(ORDER_FILE));
		        } catch (FileNotFoundException e) {
		            throw new OrderPersistenceException( "-_- Could not load order data into memory.", e);
		        }

		        scanner.println("OrderNumber,CustomerName,State,TaxRate,ProductType,"
		        		+ "Area,CostPerSquareFoot,"
		        		+ "LaborCostPerSquareFoot,"
		        		+ "MaterialCost,"
		        		+ "LaborCost,Tax,"
		        		+ "Total");
		        for(Order order : ordersAsList){
		            scanner.println(marshallOrder(order));
		        }
		        scanner.close();

		}

		private void writeOrderFromList(List<Order> ordersAsList2) throws OrderPersistenceException, IOException {
			PrintWriter scanner;
	        try {
	            scanner = new PrintWriter(new FileWriter(ORDER_FILE));
	        } catch (FileNotFoundException e) {
	            throw new OrderPersistenceException( "-_- Could not load order data into memory.", e);
	        }

	        scanner.println("OrderNumber,CustomerName,State,TaxRate,ProductType, Area,"
	        		+ "CostPerSquareFoot,"
	        		+ "LaborCostPerSquareFoot,"
	        		+ "MaterialCost,LaborCost,"
	        		+ "Tax,Total");
	        for(Order order : ordersAsList){
	            scanner.println(marshallOrder(order));
	        }
	        scanner.close();

		}




		  private void writeOrderFromListForExport(List<String> textData) throws OrderPersistenceException, IOException {
		        PrintWriter scanner;
		        try {
		            scanner = new PrintWriter(new FileWriter(ORDER_FILE));
		        } catch (FileNotFoundException e) {
		            throw new OrderPersistenceException( "-_- Could not load order data into memory.", e);
		        }

		        scanner.println("OrderNumber, CustomerName, State, TaxRate, ProductType, Area, CostPerSquareFoot, "
		        		+ "LaborCostPerSquareFoot, MaterialCost, LaborCost, Tax, Total, OrderDate");
		        for(String order : textData){
		            scanner.println(order);
		        }
		        scanner.close();
		    }

}
