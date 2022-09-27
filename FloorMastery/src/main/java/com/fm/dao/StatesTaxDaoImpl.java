package com.fm.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fm.model.States;
import com.fm.service.StatePersistenceException;

public class StatesTaxDaoImpl implements StatesTaxDao{

	    private final String DELIMITER = ",";
	    private static final String TAXES_FILE = "SampleFileData/Data/Taxes.txt";



	    private Map<String, States> states = new HashMap<>();




	@Override
	public List<States> getAllStates() throws StatePersistenceException {
		return null;
	}


		@Override
		public States getStatesName(String statesName) throws StatePersistenceException {
			loadStates();
			return  states.get(statesName);
		}

			@Override
		public States getStateTax(BigDecimal tax) throws StatePersistenceException {
			loadStates();
			return states.get(tax) ;
		}

		  private void loadStates() throws StatePersistenceException {
		        //Open File:
		        Scanner scanner;

		        try {
		        	scanner = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
				}
				catch(FileNotFoundException e) {
					throw	 new StatePersistenceException("Could not locate the file", e);
				}


		        //Read from file
		        String currentLine;
		        States currentStates;

		        while(scanner.hasNextLine()) {
					currentLine = scanner.nextLine();
					currentStates = unmarshallStatesTax(currentLine);
					states.put(currentStates.getStateAbbreviation(), currentStates);
				}

		           //Clean up/close file
		        scanner.close();
		    }

		//Translate data from file to an object in memory
				 private String marshallStatesTax(States states) {
				        //Get  details from states
					    String statesAsText = states.getStateAbbreviation() + DELIMITER;
					    statesAsText += states.getStateName().toString() + DELIMITER;
				        statesAsText += states.getTaxRate().toString() + DELIMITER;
				        return statesAsText;
				    }




				//Translate data from object in memory into a text file.
				 private States unmarshallStatesTax(String statesAsText) {

				        String [] statesAsElements = statesAsText.split(DELIMITER);

				        String statesTX = statesAsElements[0];
				        States statesFromFile = new States(statesTX);
				        statesFromFile.setStateAbbreviation(statesTX);

				        statesFromFile.setStateName(statesAsElements[1]);

						BigDecimal taxRate = new BigDecimal(statesAsElements[2]);
						statesFromFile.setTaxRate(taxRate.setScale(2, RoundingMode.HALF_UP));

				        return statesFromFile;
				    }


				private void writeStates() throws StatePersistenceException{

					PrintWriter out;

					try {
						out = new PrintWriter(new FileWriter(TAXES_FILE));
					}
					catch(Exception e) {
						throw new StatePersistenceException("Could not save product data log", e);
					}

					String taxesAsText;
					List<States> statesList = this.getAllStates();
					for(States currentStates : statesList) {
						taxesAsText = marshallStatesTax(currentStates);
						out.println(taxesAsText);
						out.flush();
					}
					out.close();
				}

}

