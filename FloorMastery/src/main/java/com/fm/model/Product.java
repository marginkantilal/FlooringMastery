package com.fm.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

	 private String productType;
	    private BigDecimal costPerSquareFoot;
	    private BigDecimal laborCostPerSquareFoot;

	    public Product(String productType) {
	        this.productType = productType;
	    }


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Product other = (Product) obj;
		return Objects.equals(laborCostPerSquareFoot, other.laborCostPerSquareFoot)
				&& Objects.equals(costPerSquareFoot, other.costPerSquareFoot)
				&& Objects.equals(productType, other.productType);
	}



	@Override
	public int hashCode() {
		return Objects.hash(laborCostPerSquareFoot, costPerSquareFoot, productType);
	}




	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}


	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}


	/**
	 * @return the costPerSquareFoot
	 */
	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}


	/**
	 * @param costPerSquareFoot the costPerSquareFoot to set
	 */
	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}


	/**
	 * @return the laborCostPerSquareFoot
	 */
	public BigDecimal getLaborCostPerSquareFoot() {
		return laborCostPerSquareFoot;
	}


	/**
	 * @param laborCostPerSquareFoot the laborCostPerSquareFoot to set
	 */
	public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
		laborCostPerSquareFoot = laborCostPerSquareFoot;
	}


	@Override
	public String toString() {
		return "Product [productType=" + productType + ", costPerSquareFoot=" + costPerSquareFoot
				+ ", LaborCostPerSquareFoot=" + laborCostPerSquareFoot + "]";
	}


}
