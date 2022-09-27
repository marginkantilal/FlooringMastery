package com.fm.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fm.model.Order;
import com.fm.service.OrderPersistenceException;

public class OrderDaoImplTest {


	 OrderDao testDao;

    public OrderDaoImplTest() {
	    }

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("resource")
	@BeforeEach
	public void setUp() throws IOException {

		  testDao = new OrderDaoImpl();
	        new FileWriter("SampleFileData/Testing/Orders_" + "UnitTest" + ".txt");
	}


	@Test
	public void testGetAllOrders() throws OrderPersistenceException {
	        Order orderOne = new Order("Mighty LTD");
	        orderOne.setOrderNumber(1);
	        orderOne.setState("KY");
	        orderOne.setTaxRate(new BigDecimal("6.00"));
	        orderOne.setProductType("Wood");
	        orderOne.setArea(new BigDecimal("500"));
	        orderOne.setCostPerSquareFoot(new BigDecimal("5.15"));
	        orderOne.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
	        orderOne.setMaterialCost(new BigDecimal("2575.00"));
	        orderOne.setLaborCost(new BigDecimal("2375.00"));
	        orderOne.setTax(new BigDecimal("297.00"));
	        orderOne.setTotal(new BigDecimal("5247.00"));

	        Order orderTwo = new Order("Limited Express");
	        orderTwo.setOrderNumber(2);
	        orderTwo.setState("WA");
	        orderTwo.setTaxRate(new BigDecimal("9.25"));
	        orderTwo.setProductType("Wood");
	        orderTwo.setArea(new BigDecimal("101.1"));
	        orderTwo.setCostPerSquareFoot(new BigDecimal("5.15"));
	        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
	        orderTwo.setMaterialCost(new BigDecimal("520.66"));
	        orderTwo.setLaborCost(new BigDecimal("480.22"));
	        orderTwo.setTax(new BigDecimal("92.58"));
	        orderTwo.setTotal(new BigDecimal("1093.47"));
	        String filePath = "SampleFileData/Testing/Orders_" + "UnitTest" + ".txt";
	        testDao.addOrder(orderOne, filePath);
	        testDao.addOrder(orderTwo, filePath);

	        List<Order> allOrders = testDao.getAllOrder(filePath);

	        assertNotNull(allOrders);
	        assertEquals(2, allOrders.size());

	        assertTrue(allOrders.contains(orderOne));
	        assertTrue(allOrders.contains(orderTwo));
	    }


    @Test
    public void testEditOrder() throws OrderPersistenceException {
    	Order orderOne = new Order("Mighty LTD");
        orderOne.setOrderNumber(1);
        orderOne.setState("KY");
        orderOne.setTaxRate(new BigDecimal("6.00"));
        orderOne.setProductType("Wood");
        orderOne.setArea(new BigDecimal("500"));
        orderOne.setCostPerSquareFoot(new BigDecimal("5.15"));
        orderOne.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        orderOne.setMaterialCost(new BigDecimal("2575.00"));
        orderOne.setLaborCost(new BigDecimal("2375.00"));
        orderOne.setTax(new BigDecimal("297.00"));
        orderOne.setTotal(new BigDecimal("5247.00"));


        //New Data to updates to previous order
        Order orderTwo = new Order("London Units"); //changed name
        orderTwo.setOrderNumber(1);
        orderTwo.setState("KY");
        orderTwo.setTaxRate(new BigDecimal("6.00"));
        orderTwo.setProductType("Wood");
        orderTwo.setArea(new BigDecimal("500"));
        orderTwo.setCostPerSquareFoot(new BigDecimal("5.15"));
        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        orderTwo.setMaterialCost(new BigDecimal("2575.00"));
        orderTwo.setLaborCost(new BigDecimal("2375.00"));
        orderTwo.setTax(new BigDecimal("297.00"));
        orderTwo.setTotal(new BigDecimal("5247.00"));

        String filePath = "SampleFileData/Testing/Orders_" + "UnitTest" + ".txt";
        testDao.addOrder(orderOne, filePath);
        Order prevOrder = testDao.editOrder(orderTwo, filePath);

        List<Order> orderList = testDao.getAllOrder(filePath);

        assertNotNull(orderList);
        assertEquals(1, orderList.size());
        assertEquals(orderOne, prevOrder);
        assertTrue(orderList.contains(orderTwo));


    }

    @Test
    public void testGetAddOrder() throws OrderPersistenceException{
    	Order tempOrder = new Order("Mighty LTD");
        tempOrder.setOrderNumber(1);
        tempOrder.setState("KY");
        tempOrder.setTaxRate(new BigDecimal("6.00"));
        tempOrder.setProductType("Wood");
        tempOrder.setArea(new BigDecimal("500"));
        tempOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        tempOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        tempOrder.setMaterialCost(new BigDecimal("2575.00"));
        tempOrder.setLaborCost(new BigDecimal("2375.00"));
        tempOrder.setTax(new BigDecimal("297.00"));
        tempOrder.setTotal(new BigDecimal("5247.00"));

        String filePath = "SampleFileData/Testing/Orders_" + "UnitTest" + ".txt";


        //Act
        testDao.addOrder(tempOrder, filePath);

        Order getOrder = testDao.getOrder(1, filePath);

        //Assert
        assertEquals(tempOrder, getOrder);
        assertTrue(getOrder != null);
        assertEquals(getOrder.getOrderNumber(), tempOrder.getOrderNumber());
        assertEquals(getOrder.getCustomerName(), tempOrder.getCustomerName());
        assertEquals(getOrder.getProductType(), tempOrder.getProductType());
        assertEquals(getOrder.getState(), tempOrder.getState());
        assertEquals(getOrder.getTaxRate() , tempOrder.getTaxRate());
        assertEquals(getOrder.getArea() , tempOrder.getArea());
        assertEquals(getOrder.getCostPerSquareFoot() , tempOrder.getCostPerSquareFoot());
        assertEquals(getOrder.getLaborCostPerSquareFoot() , tempOrder.getLaborCostPerSquareFoot());
        assertEquals(getOrder.getMaterialCost() , tempOrder.getMaterialCost());
        assertEquals(getOrder.getLaborCost() , tempOrder.getLaborCost());
        assertEquals(getOrder.getTax(), tempOrder.getTax());
        assertEquals(getOrder.getTotal(), tempOrder.getTotal());

    }

    @Test
    public void testRemoveOrder() throws OrderPersistenceException, IOException{
    	Order tempOrder = new Order("Mighty LTD");
        tempOrder.setOrderNumber(1);
        tempOrder.setState("KY");
        tempOrder.setTaxRate(new BigDecimal("6.00"));
        tempOrder.setProductType("Wood");
        tempOrder.setArea(new BigDecimal("500"));
        tempOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        tempOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        tempOrder.setMaterialCost(new BigDecimal("2575.00"));
        tempOrder.setLaborCost(new BigDecimal("2375.00"));
        tempOrder.setTax(new BigDecimal("297.00"));
        tempOrder.setTotal(new BigDecimal("5247.00"));

        String filePath = "SampleFileData/Testing/Orders_" + "UnitTest" + ".txt";
        //Act
        testDao.addOrder(tempOrder, filePath);

        List<Order> shouldOnlyHaveOneOrder = testDao.getAllOrder(filePath);
        assertNotNull(shouldOnlyHaveOneOrder);
        assertEquals(1, shouldOnlyHaveOneOrder.size());
        assertTrue(shouldOnlyHaveOneOrder.contains(tempOrder));

        //Performing remove order test

        Order removedOrder = testDao.removeOrder(1, filePath);
        assertEquals(tempOrder, removedOrder);

        shouldOnlyHaveOneOrder = testDao.getAllOrder(filePath);

        //List should be empty now
        assertTrue(shouldOnlyHaveOneOrder.isEmpty());


    }

    @Test
    public void testExportOrderFile() throws OrderPersistenceException{
        String filePath = "SampleFileData/TestDataExport/ExportedData_" + "UnitTest" + ".txt";
        File file = new File(filePath);
        testDao.exportOrder(filePath);
        assertTrue(file.exists());


    }

}
