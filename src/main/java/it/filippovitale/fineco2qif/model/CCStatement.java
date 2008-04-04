/*
 * Copyright 2007 Filippo Vitale (http://www.filippovitale.it)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.filippovitale.fineco2qif.model;

import it.filippovitale.fineco2qif.logic.ExcelSheetAnalysisLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class CCStatement extends Statement {
    public static final int		NUMERO_CC_ROW = 0;
    public static final short	NUMERO_CC_COLUMN = 0;
    public static final String	NUMERO_CC_PREFIX = "Carta di credito n. ";

    public static final int		OWNER_CC_ROW = 1;
    public static final short	OWNER_CC_COLUMN = 0;
    public static final String	OWNER_CC_PREFIX = "Intestata a: ";

    public static final int		TRANSACTION_START_ROW = 5;
    public static final short	TRANSACTION_DATE_COLUMN = 1;
    public static final short	TRANSACTION_AMOUNT_COLUMN = 5;
    public static final short	TRANSACTION_MEMO_COLUMN = 2;
    // public static final short	TRANSACTION_OPERATION_DATE_COLUMN = 0; // TODO concatenate with memo

	private static final String TRANSACTION_PHONE_SKYPE = "SKYPE";
	private static final String TRANSACTION_PHONE_WIND = "Telefon";
	private static final String TRANSACTION_ENT_RYANAIR = "RYANAIR";
	private static final String TRANSACTION_MARKET_IPERCOOP = "IPERCOOP";
	private static final String TRANSACTION_MARKET_SUPERM = "SUPERM";
	private static final String TRANSACTION_COMPUTER_ARUBA = "ARUBA";
	private static final String TRANSACTION_PETROL_SHELL = "SHELL";
	
	private static final String ACCOUNT_NAME_PHONE = "Telefono";
	private static final String ACCOUNT_NAME_ENT = "Intrattenimento";
	private static final String ACCOUNT_NAME_MARKET = "Supermercato";
	private static final String ACCOUNT_NAME_COMPUTER = "Computer";
	private static final String ACCOUNT_NAME_PETROL = "Carburante";
	private static final String ACCOUNT_NAME_DEFAULT = "Uscite";


    private String ccNumber;
    private String ccOwner;
    private List<CCTransaction> transactions;
    
    public CCStatement(HSSFSheet sheet) {
		super(sheet);
	}

    protected void populateMetadata() {
    	String ccNumberCellValue = ExcelSheetAnalysisLogic.getCellStringValue(sheet, NUMERO_CC_ROW, NUMERO_CC_COLUMN);
		if(ccNumberCellValue.startsWith(NUMERO_CC_PREFIX)) {
			ccNumber = ccNumberCellValue.substring(NUMERO_CC_PREFIX.length());
			log.debug("credit card number = " + ccNumber);
		}
    	
		String ccOwnerCellValue = ExcelSheetAnalysisLogic.getCellStringValue(sheet, OWNER_CC_ROW, OWNER_CC_COLUMN);
		if(ccOwnerCellValue.startsWith(OWNER_CC_PREFIX)) {
			ccOwner = ccOwnerCellValue.substring(OWNER_CC_PREFIX.length());
			log.debug("credit card owner = " + ccOwner);
		}
    }
    
    protected void populateTransaction() {
    	transactions = new ArrayList<CCTransaction>();
		
		for (int i = 0; i <= sheet.getLastRowNum() - TRANSACTION_START_ROW ; i++) {
			HSSFRow cellRow = sheet.getRow(TRANSACTION_START_ROW + i);
			String date = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_DATE_COLUMN);
			String amount = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_AMOUNT_COLUMN);
			String memo = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_MEMO_COLUMN);
			
			amount = amount==null ? "0.0" : amount;
			String negativeAmount = amount.startsWith("-") ? amount.substring(1) : "-" + amount;
			
			memo = memo!=null ? memo.trim() : "";

			String gnucashCategory = null;
			if(memo.contains(TRANSACTION_PHONE_SKYPE) || memo.contains(TRANSACTION_PHONE_WIND)) {
				gnucashCategory = ACCOUNT_NAME_PHONE;
			} else if(memo.contains(TRANSACTION_ENT_RYANAIR)) {
				gnucashCategory = ACCOUNT_NAME_ENT;
			} else if(memo.contains(TRANSACTION_MARKET_IPERCOOP) || memo.contains(TRANSACTION_MARKET_SUPERM)) {
				gnucashCategory = ACCOUNT_NAME_MARKET;
			} else if(memo.contains(TRANSACTION_COMPUTER_ARUBA)) {
				gnucashCategory = ACCOUNT_NAME_COMPUTER;
			} else if(memo.contains(TRANSACTION_PETROL_SHELL)) {
				gnucashCategory = ACCOUNT_NAME_PETROL;
			} else {
				gnucashCategory = ACCOUNT_NAME_DEFAULT;
			}
			
			CCTransaction ccTransaction = new CCTransaction(date, negativeAmount, memo, gnucashCategory);
			transactions.add(ccTransaction);
			log.debug("transaction[" + i + "] = " + ccTransaction);
		}
    }

    // -------------------------------------------------------------------------

    public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcOwner() {
		return ccOwner;
	}

	public void setCcOwner(String ccOwner) {
		this.ccOwner = ccOwner;
	}

	public List<CCTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<CCTransaction> transactions) {
		this.transactions = transactions;
	}
    
}
