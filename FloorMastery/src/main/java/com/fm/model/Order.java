package com.fm.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

	private int orderNumber;
	private String customerName;
	private String state;
	private BigDecimal taxRate;
	private String productType;
	private BigDecimal area;
	private BigDecimal costPerSquareFoot;
	private BigDecimal laborCostPerSquareFoot;
	private BigDecimal materialCost;
	private BigDecimal laborCost;
	private BigDecimal tax;
	private BigDecimal total;
    private LocalDate orderDate = LocalDate.now();


    public Order(String customerName) {
        this.customerName = customerName;
        orderNumber = 0;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		return Objects.equals(area, other.area) && Objects.equals(costPerSquareFoot, other.costPerSquareFoot)
				&& Objects.equals(customerName, other.customerName) && Objects.equals(laborCost, other.laborCost)
				&& Objects.equals(laborCostPerSquareFoot, other.laborCostPerSquareFoot)
				&& Objects.equals(materialCost, other.materialCost) && Objects.equals(orderDate, other.orderDate)
				&& orderNumber == other.orderNumber && Objects.equals(productType, other.productType)
				&& Objects.equals(state, other.state) && Objects.equals(tax, other.tax)
				&& Objects.equals(taxRate, other.taxRate) && Objects.equals(total, other.total);
	}

    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 43 * hash + this.orderNumber;
	        hash = 43 * hash + Objects.hashCode(this.customerName);
	        hash = 43 * hash + Objects.hashCode(this.state);
	        hash = 43 * hash + Objects.hashCode(this.taxRate);
	        hash = 43 * hash + Objects.hashCode(this.productType);
	        hash = 43 * hash + Objects.hashCode(this.area);
	        hash = 43 * hash + Objects.hashCode(this.costPerSquareFoot);
	        hash = 43 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
	        hash = 43 * hash + Objects.hashCode(this.materialCost);
	        hash = 43 * hash + Objects.hashCode(this.laborCost);
	        hash = 43 * hash + Objects.hashCode(this.tax);
	        hash = 43 * hash + Objects.hashCode(this.total);
	        return hash;
	    }



	 @Override
    public String toString() {
        return "orderNumber=" + orderNumber + ", customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costPerSquareFoot=" + costPerSquareFoot + ", laborCostPerSquareFoot=" + laborCostPerSquareFoot + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", tax=" + tax + ", total=" + total;
    }


	public String toStringForAddingOrder() {
        return "customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costPerSquareFoot=" + costPerSquareFoot + ", laborCostPerSquareFoot=" + laborCostPerSquareFoot + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", tax=" + tax + ", total=" + total;
    }



}
