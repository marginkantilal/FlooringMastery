package com.fm.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class States {

	private String stateAbbreviation;
	private String stateName;
	private BigDecimal taxRate;


	public States(String StateAbbreviation) {
        this.stateAbbreviation = StateAbbreviation;
    }

	@Override
	public String toString() {
		return "States {StateAbbreviation=" + stateAbbreviation + ", StateName=" + stateName + ", taxRate=" + taxRate
				+ "}";
	}

	public States(String stateAbbreviation, String stateName, BigDecimal taxRate) {
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
		this.taxRate = taxRate;
	}



}
