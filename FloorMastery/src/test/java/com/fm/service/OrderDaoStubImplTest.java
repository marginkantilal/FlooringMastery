package com.fm.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fm.dao.OrderDao;
import com.fm.model.Order;

class OrderDaoStubImplTest {

	
	private OrderService service;
	
	
	public OrderDaoStubImplTest() throws OrderPersistenceException{
//		OrderDao dao = new OrderDaoStubImpl();
//		service = new OrderServiceImpl(dao);
		
		//Bean
			ApplicationContext ctx = new ClassPathXmlApplicationContext("testApplicationContext.xml");
			    service = ctx.getBean("service", OrderService.class);
	}
	
	@Test
	public void testGetAddOrder() throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException{
		Order tempOrder = new Order("Mighty Ltd");
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
        
        Order AddedOrder = service.addOrder(tempOrder, "");
        assertNotNull(AddedOrder);
        assertEquals(tempOrder.getOrderNumber(), AddedOrder.getOrderNumber());
        
        //Customer Name should be Mighty Ltd and State should be WA
        assertTrue(AddedOrder.getCustomerName() == "Mighty Ltd");
        assertTrue(AddedOrder.getState() == "WA");

        Order shouldBeNull = service.getOrder(2, "");
        
        assertNull(shouldBeNull);
        

	}
	
	 @Test
	    public void testRemoveOrder() throws OrderPersistenceException, IOException {
			Order tempOrder = new Order("Mighty Ltd");
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

	        
	        Order shouldBeMighty = service.removeOrder(1, "");
	        assertNotNull(shouldBeMighty);
	        assertEquals(tempOrder, shouldBeMighty);
	        
	        Order shouldBeNull = service.removeOrder(2, "");
	        assertNull(shouldBeNull);
	    }
	 
	 
	    
	    @Test
	    public void testEditOrder() throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException{
			Order tempOrder = new Order("Express Ltd");
	    	tempOrder.setOrderNumber(1);
	    	tempOrder.setState("TX");
	    	tempOrder.setTaxRate(new BigDecimal("4.45"));
	    	tempOrder.setProductType("Laminate");
	    	tempOrder.setArea(new BigDecimal("132"));
	    	
	        
	        Order updatedOrder = service.editOrderDetails(1, tempOrder, "");
	        
	        assertNotNull(updatedOrder);
	        assertEquals(updatedOrder.getOrderNumber(), updatedOrder.getOrderNumber());
	        
	        assertTrue(updatedOrder.getCustomerName() == "Mighty Ltd");
	        Order shouldBeNull = service.getOrder(2, "");
	        assertNull(shouldBeNull);
	    }


	    @Test
	    public void testGetAllOrder() throws OrderPersistenceException{
	    	
	    	Order tempOrder = new Order("Mighty Ltd");
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
	        
	        //Should have only one record
	         assertEquals(1, service.getAllOrders("").size());
	         
	         assertTrue(service.getAllOrders("").contains(tempOrder));
	    }
}
