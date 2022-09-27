package com.fm.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fm.model.States;
import com.fm.service.StatePersistenceException;

public interface StatesTaxDao {
	public List<States> getAllStates() throws StatePersistenceException;


	 States getStatesName(String states) throws StatePersistenceException;


     States getStateTax(BigDecimal tax) throws StatePersistenceException;

}
