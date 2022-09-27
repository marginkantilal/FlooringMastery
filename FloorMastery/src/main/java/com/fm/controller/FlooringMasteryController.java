package com.fm.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.fm.model.Order;
import com.fm.model.Product;
import com.fm.model.States;
import com.fm.service.OrderPersistenceException;
import com.fm.service.ProductPersistenceException;
import com.fm.service.OrderService;
import com.fm.service.StatePersistenceException;
import com.fm.view.FlooringMasteryView;

public class FlooringMasteryController {


	private OrderService service;
	private FlooringMasteryView view;



	public FlooringMasteryController(OrderService service, FlooringMasteryView view) {
		super();
		this.service = service;
		this.view = view;
	}
	public void run() throws OrderPersistenceException, ProductPersistenceException, StatePersistenceException, IOException {
		boolean flag = true;
		int menuSelection = 0;


		while (flag) {
			menuSelection = getMenuSelection();
			switch (menuSelection) {
				case 1:
				{
					try{
						displayOrders();
					}
					catch(OrderPersistenceException ex) {
						displayError(ex);
					}
				}
				break;
				case 2:

				{
					try {
						addOrder();
					}
					catch (OrderPersistenceException ex) {
						displayError(ex);
					}
				}
				break;
				case 3:
				{
					try {
						editOrderDetails();
					}
					catch(OrderPersistenceException ex){
						displayError(ex);

					}
				}
				break;
				case 4:
				{
					try {
						removeOrder();
					}
					catch(OrderPersistenceException ex){
						displayError(ex);

					}
				}
				break;
				case 5:
				{
					try {
					exportOrderData();
					}
					catch
						(OrderPersistenceException ex) {
						displayError(ex);
					}
				}
				break;
				case 6:
					flag = false;
					break;
				default:
					unknownCommand();
			}
		}
		displayExitMessage();

	}



	//Receive menu selection for user
	private int getMenuSelection() {
		return view.displayMenu();
	}



	private void addOrder() throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException, IOException {
		view.displayAddOrderBanner();
		List<String> states = service.getStatesList();
		List<String> products = service.getProductList();
		Map<String, Product> productMap = service.getProductMap();
		Map<String, States> statesMap = service.getStateMap();


		Order order = view.getOrderInfo(states, products, statesMap, productMap);

		String date = null;
		while(true) {
			try {
				date = view.getDate();

			} catch (Exception e) {
				view.inValidDate();
			}

			if(date.compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy")))<0) {
				view.displayErrorDate();
				continue;
			}
			break;
		}
		view.displayOrderForAdding(order);

		char ch = view.conformationOrder();
		if(ch == 'n' && !(ch =='y')){
			view.displayFailedAddingOrder();

		} else{
			service.addOrder(order, date);
			view.displayAddedOrderSuccess();
		}



	}


	public void displayError(Exception e){
		view.displayError(e);
	}

	private void displayExitMessage() {
		view.exitMessage();
	}


	private void displayOrders() throws OrderPersistenceException {
		String date = null;
		while (true) {
			try {
				date = view.getDate();
				break;
			}
			catch(Exception e) {
				view.inValidDate();
			}
		}


		List<Order> order =  service.getAllOrders(date);
		view.displayOrders(order);

	}

	private void editOrderDetails() throws StatePersistenceException, ProductPersistenceException, OrderPersistenceException {
		List<String> stateList = service.getStatesList();
		List<String> productList = service.getProductList();
		Map<String, Product> productMap = service.getProductMap();
		Map<String, States> statesMap = service.getStateMap();
		view.displayEditOrderBanner();
		int orderNum = view.getOrderNum();

		String date = null;
		while(true){
			try {
				date = view.getDate();
				break;
			}catch(Exception e) {
				view.inValidDate();
			}
		}

		Order oldOrder = service.getOrder(orderNum, date);
		view.displayOrder(oldOrder);
		if(oldOrder == null) {
			view.noFoundOrdersMessage();
		}else {
			Order order = view.getOrderInfoForEditOrder(stateList, productList, oldOrder);
			order.setTaxRate(statesMap.get(order.getState()).getTaxRate());
			order.setCostPerSquareFoot(productMap.get(order.getProductType()).getCostPerSquareFoot());
			order.setLaborCostPerSquareFoot(productMap.get(order.getProductType()).getLaborCostPerSquareFoot());
			service.editOrderDetails(orderNum, order, date);
			System.out.println(order);
			view.displayUpdateSuccess();


		}
	}



	private void exportOrderData() throws StatePersistenceException, OrderPersistenceException {

		service.exportOrderData();
		view.displayExportSuccessBanner();
	}


	private void removeOrder() throws OrderPersistenceException, IOException {
		view.displayRemoveOrderBanner();
		int orderNum = view.getOrderNum();
		String date = null;

		while(true) {
			try {
				date = view.getDate();
				break;
			}
			catch(Exception e) {
				view.inValidDate();
			}
		}


		Order oldOrder = service.getOrder(orderNum, date);
		if(oldOrder==null){
			view.noOrderMessage();
		}char ch = view.deleteConfirmationOrder();
		if(ch == 'y'){
			Order removedOrder = service.removeOrder(orderNum, date);
			view.displayRemoveSuccess(removedOrder);
		} else{
			view.displayRemoveFailed();
		}
	}

	private void unknownCommand() {
		view.displayUnknownCommandMessage();
	}

}
