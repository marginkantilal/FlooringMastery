package com.fm.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.fm.dao.OrderDao;
import com.fm.model.Order;

class ServiceImplTest {
	
	private OrderService service;
	
	
	public ServiceImplTest() throws OrderPersistenceException{
		OrderDao dao = new OrderDaoStubImpl();
		service = new OrderServiceImpl(dao);		
		//	ApplicationContext ctx = new ClassPathXmlApplicationContext("testApplicationContext.xml");
		//	    service = ctx.getBean("service", OrderService.class);
	}
	
	@Test
	public void testGetAddOrder() throws OrderPersistenceException, StatePersistenceException, ProductPersistenceException{
		Order tempOrder = new Order("Mighty LTD");
        tempOrder.setOrderNumber(1);
        tempOrder.setState("KY");
        tempOrder.setTaxRate(new BigDecimal("6.00"));
        tempOrder.setProductType("Wood");
        tempOrder.setArea(new BigDecimal("500"));
        
        Order AddedOrder = service.addOrder(tempOrder, "");
        assertNotNull(AddedOrder);
        assertEquals(tempOrder.getOrderNumber(), AddedOrder.getOrderNumber());
        
        assertTrue(AddedOrder.getCustomerName() == "Mighty LTD");
        assertTrue(AddedOrder.getArea() == new BigDecimal("500"));
        Order shouldBeNull = service.getOrder(2, "");
        assertNull(shouldBeNull);
        

       

	}


}
