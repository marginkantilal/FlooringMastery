package com.fm.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fm.dao.OrderDao;
import com.fm.model.Order;
import com.fm.model.Product;
import com.fm.model.States;


public class OrderServiceImpl implements OrderService {

	private OrderDao dao;


	public OrderServiceImpl(OrderDao dao) {
		this.dao = dao;

	}

	@Override
	public Order addOrder(Order order, String orderDate)
			throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException {
		//Validating customer name
		validateCustomerData(order);

		//All the total info
		orderTotalPricingCal(order);

		//Order file path
		String path = "SampleFileData/Orders/Orders_" + orderDate + ".txt";
		//Calling dao to proceed adding order
		return dao.addOrder(order, path);
	}



	@Override
	public Order editOrderDetails(int orderNumber, Order newOrder, String date) throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException {
		Order oldOrder = getOrder(orderNumber, date);

		//Validating order
		if(oldOrder == null){
			throw new OrderPersistenceException("ERROR: Could not edit order. Order #" + orderNumber + " does not exists.");
		}

		//validates customer names
		validateCustomerData(newOrder);

		// get new price info
		orderTotalPricingCal(newOrder);
		String path = "SampleFileData/Orders/Orders_" + date + ".txt";
		return dao.editOrder(newOrder, path);

	}
	@Override
	public void exportOrderData() throws OrderPersistenceException {
		String path = "SampleFileData/Backup/DataExport.txt";
		dao.exportOrder(path);
	}



	@Override
	public List<Order> getAllOrders(String date) throws OrderPersistenceException {
		String path = "SampleFileData/Orders/Orders_" + date + ".txt";
		return dao.getAllOrder(path);
	}



	//get order
	@Override
	public Order getOrder(int orderNumber, String date)
			throws OrderPersistenceException {
		String path = "SampleFileData/Orders/Orders_" + date + ".txt";
		return dao.getOrder(orderNumber, path);
	}



	//Getting all the products that are available
	@Override
	public List<String> getProductList() throws ProductPersistenceException {
		Map<String, Product>products = dao.productLog();
		List<String>productTypeList = new ArrayList<>();
		for (Map.Entry<String, Product> productEntry : products.entrySet()) {
			productTypeList.add(productEntry.getValue().getProductType());
		}

		return productTypeList;
	}

	@Override
	public Map<String, Product> getProductMap() throws ProductPersistenceException {
		Map<String, Product>products = dao.productLog();
		return products;
	}

	@Override
	public List<String> getStatesList() throws StatePersistenceException {
		Map<String, States> states = dao.stateLog();
		List<String> list = new ArrayList<>();
		//Map through all states
		for (Map.Entry<String, States> stateEntry : states.entrySet()) {
			//Add all the available in a list
			list.add(stateEntry.getValue().getStateAbbreviation());

		}
		return list;
	}

	@Override
	public Map<String, States> getStateMap() throws StatePersistenceException {
		Map<String, States> states = dao.stateLog();
		return states;
	}

	@Override
	public void orderTotalPricingCal(Order order) throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException {

		Map<String, States> states = dao.stateLog();
		Map<String, Product> products = dao.productLog();

		//Getting tax rate by its state
		order.setTaxRate(states.get(order.getState()).getTaxRate().setScale(2, RoundingMode.HALF_EVEN));

		//CostPerSquareFoot
		order.setCostPerSquareFoot(products.get(order.getProductType()).getCostPerSquareFoot().setScale(2,  RoundingMode.HALF_EVEN));
		order.setLaborCostPerSquareFoot(products.get(order.getProductType()).getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_EVEN));


		//	MaterialCost = (Area * CostPerSquareFoot)
		BigDecimal materialCost = order.getArea().multiply(order.getCostPerSquareFoot());

		//	LaborCost = (Area * LaborCostPerSquareFoot)
		BigDecimal laborCost = order.getArea().multiply(order.getLaborCostPerSquareFoot());
		//	Tax = (MaterialCost + LaborCost) * (TaxRate/100)
		BigDecimal tax = (materialCost.add(laborCost)).multiply((order.getTaxRate().divide(new BigDecimal("100"))));
		//	Total = (MaterialCost + LaborCost + Tax)
		BigDecimal total =materialCost.add(laborCost.add(tax));

		//setting the prices
		order.setMaterialCost(materialCost.setScale(2,  RoundingMode.HALF_EVEN));
		order.setLaborCost(laborCost.setScale(2, RoundingMode.HALF_EVEN));
		order.setTax(tax.setScale(2, RoundingMode.HALF_EVEN));
		order.setTotal(total.setScale(2, RoundingMode.HALF_EVEN));
	}



	//Removing order by getting order number and the date of order
	@Override
	public Order removeOrder(int orderNumber, String orderDate) throws OrderPersistenceException, IOException {
		String path = "SampleFileData/Orders/Orders_" + orderDate + ".txt";
		return dao.removeOrder(orderNumber, path);
	}



	private void validateCustomerData(Order order)throws OrderPersistenceException {

		if(order.getCustomerName() == null
				||order.getCustomerName().trim().length() == 0
				|| order.getArea() == null || order.getArea().doubleValue()< 100)
		{
			throw new OrderPersistenceException("Error: All fields [Customer name, Area] are required.");

		}

		char[] chars = order.getCustomerName().toCharArray();
		for(char c : chars){
			if(Character.isLetterOrDigit(c) || c == '.' || c == ',' || c == ' '){
				continue;
			}
			else{
				throw new OrderPersistenceException("Error: the allowed inputs are [0-9], [a-z], [A-Z], commas, and periods .");
			}
		}

	}


}
