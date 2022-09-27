package com.fm.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fm.model.Product;
import com.fm.service.ProductPersistenceException;



public class ProductDaoImpl implements ProductDao{

    private final String DELIMITER = ",";
    private static final String PRODUCTS_FILE = "SampleFileData/Data/Products.txt";

    private Map<String, Product> products = new HashMap<>();
	public ProductDaoImpl() {

	}
	@Override
	public List<Product> getAllProduct() throws ProductPersistenceException {
		loadProducts();
		return new ArrayList<>(products.values());
	}

	@Override
	public Product getProduct(String productType) throws ProductPersistenceException {
		loadProducts();
        return products.get(productType);


	}

//
//	//Translate data from object in memory into a text file.
//	 private Product unmarshallProduct(String productAsText) {
//
//
//	        String [] productAsElements = productAsText.split(DELIMITER);
//
//	        String productType = productAsElements[0];
//	        Product productFromFile = new Product();
//	        productFromFile.setProductType(productType);
//
//
//		    BigDecimal costPerSquareFoot = new BigDecimal(productAsElements[1]);
//			productFromFile.setCostPerSquareFoot(costPerSquareFoot.setScale(2, RoundingMode.HALF_UP));
//
//
//			BigDecimal LaborCostPerSquareFoot = new BigDecimal(productAsElements[2]);
//			productFromFile.setLaborCostPerSquareFoot(LaborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP));
//
//	        return productFromFile;
//	    }
//
//		//Translate data from file to an object in memory
//	 private String marshallProduct(Product product) {
//	        //Get product details from product pojo
//		    String productAsText = product.getProductType() + DELIMITER;
//	        productAsText += product.getCostPerSquareFoot().toString() + DELIMITER;
//	        productAsText += product.getLaborCostPerSquareFoot().toString() + DELIMITER;
//	        return productAsText;
//	    }
//
//	//Put all unmarshalled data into the product
//	  private void loadProducts() throws FlooringMasteryProductPersistenceException {
//	        //Open File:
//	        Scanner scanner;
//
//	        try {
//	        	scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
//			}
//			catch(FileNotFoundException e) {
//				throw	 new FlooringMasteryProductPersistenceException("Could not locate the file", e);
//			}
//
//
//	        //Read from file
//	        String currentLine;
//	        Product currentProduct;
//
//	        while(scanner.hasNextLine()) {
//				currentLine = scanner.nextLine();
//				currentProduct = unmarshallProduct(currentLine);
//				products.put(currentProduct.getProductType(), currentProduct);
//			}
//
//	           //Clean up/close file
//	        scanner.close();
//	    }

	    private void loadProducts() throws ProductPersistenceException {
	        Scanner scanner;

	        try {
	            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
	        } catch (FileNotFoundException ex) {
	            throw new ProductPersistenceException("");
	        }
	        String currentLine;
	        String[] currentTokens;

	        while (scanner.hasNextLine()) {
	            currentLine = scanner.nextLine();
	            currentTokens = currentLine.split(DELIMITER);
	            Product currentProd = new Product(currentTokens[0]);
	            currentProd.setCostPerSquareFoot(new BigDecimal(currentTokens[1]));
	            currentProd.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[2]));
	            products.put(currentProd.getProductType(), currentProd);
	        }
	        scanner.close();

	    }

//	//Write marshaled data into the text file.
//			private void writeProduct() throws FlooringMasteryProductPersistenceException{
//
//				PrintWriter out;
//
//				try {
//					out = new PrintWriter(new FileWriter(PRODUCTS_FILE));
//				}
//				catch(Exception e) {
//					throw new FlooringMasteryProductPersistenceException("Could not save product data log", e);
//				}
//
//				String productAsText;
//				List<Product> productList = this.getAllProduct();
//				for(Product currentProduct : productList) {
//					productAsText = marshallProduct(currentProduct);
//					out.println(productAsText);
//					out.flush();
//				}
//				out.close();
//			}
//
}
