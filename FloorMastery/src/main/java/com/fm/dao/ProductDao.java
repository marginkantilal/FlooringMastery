package com.fm.dao;

import java.util.List;

import com.fm.model.Product;
import com.fm.service.ProductPersistenceException;

public interface ProductDao {

	List<Product> getAllProduct() throws ProductPersistenceException;
    Product getProduct (String productType) throws ProductPersistenceException;



}
