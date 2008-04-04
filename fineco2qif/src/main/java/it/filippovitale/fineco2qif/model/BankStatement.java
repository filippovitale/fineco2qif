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

public class BankStatement extends Statement {
    public static final int		BANK_ACCOUNT_NUMBER_ROW = 0;
    public static final short	BANK_ACCOUNT_NUMBER_COLUMN = 0;
    public static final String	BANK_ACCOUNT_NUMBER_PREFIX = "Conto Corrente n. ";

    public static final int		BANK_ACCOUNT_OWNER_ROW = 1;
    public static final short	BANK_ACCOUNT_OWNER_COLUMN = 0;
    public static final String	BANK_ACCOUNT_OWNER_PREFIX = "Intestazione Conto: ";

    public static final int		TRANSACTION_START_ROW = 6;
    // public static final short	TRANSACTION_OPERATION_DATE_COLUMN = 0;
    public static final short	TRANSACTION_DATE_COLUMN = 1;
    public static final short	TRANSACTION_AMOUNT_INCRESE_COLUMN = 2;
    public static final short	TRANSACTION_AMOUNT_DECRESE_COLUMN = 3;
    public static final short	TRANSACTION_MEMO_COLUMN = 4;
    public static final short	TRANSACTION_CATEGORY_COLUMN = 5;
    
    public static final String	TRANSACTION_TRANSFER_BANK_FEE = "SERVIZIO BASE";
    public static final String	TRANSACTION_TRANSFER_BANK_FEE_GOV = "IMPOSTA DI BOLLO";
    public static final String	TRANSACTION_TRANSFER_BANK_FEE_BONUS = "BONUS MENSILE";
    public static final String	TRANSACTION_TRANSFER_ATM_WITHDRAW = "PRELIEVI BANCOMAT";
    public static final String	TRANSACTION_TRANSFER_PHONE_BILL = "RICARICA TELEFONICA";
    public static final String	TRANSACTION_TRANSFER_CREDIT_CARD = "UTILIZZO CARTA DI CREDITO";

    public static final String	ACCOUNT_NAME_BANK_FEE = "Servizi Bancari";
    public static final String	ACCOUNT_NAME_ATM_WITHDRAW = "Cash";
    public static final String	ACCOUNT_NAME_PHONE_BILL = "Telefono";
    public static final String	ACCOUNT_NAME_CREDIT_CARD = "VISA Fineco";

    private String bankNumber;
    private String bankOwner;
    private List<BankTransaction> transactions;
    
    public BankStatement(HSSFSheet sheet) {
    	super(sheet);
    }
    
    protected void populateMetadata() {
    	String bankNumberCellValue = ExcelSheetAnalysisLogic.getCellStringValue(sheet, BANK_ACCOUNT_NUMBER_ROW, BANK_ACCOUNT_NUMBER_COLUMN);
		if(bankNumberCellValue.startsWith(BANK_ACCOUNT_NUMBER_PREFIX)) {
			bankNumber = bankNumberCellValue.substring(BANK_ACCOUNT_NUMBER_PREFIX.length());
			bankNumber = bankNumber!=null ? bankNumber.trim() : "";
			log.debug("credit card number = " + bankNumber);
		}
    	
		String bankOwnerCellValue = ExcelSheetAnalysisLogic.getCellStringValue(sheet, BANK_ACCOUNT_OWNER_ROW, BANK_ACCOUNT_OWNER_COLUMN);
		if(bankOwnerCellValue.startsWith(BANK_ACCOUNT_OWNER_PREFIX)) {
			bankOwner = bankOwnerCellValue.substring(BANK_ACCOUNT_OWNER_PREFIX.length());
			bankOwner = bankOwner!=null ? bankOwner.trim() : "";
			log.debug("credit card owner = " + bankOwner);
		}
    }
    
    protected void populateTransaction() {
    	transactions = new ArrayList<BankTransaction>();
		for (int i = 0; i <= sheet.getLastRowNum() - TRANSACTION_START_ROW ; i++) {
			HSSFRow cellRow = sheet.getRow(TRANSACTION_START_ROW + i);

			String date = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_DATE_COLUMN);
			String amount = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_AMOUNT_INCRESE_COLUMN);
			if("".equals(amount)) {
				amount = "-" + ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_AMOUNT_DECRESE_COLUMN);
			}
			String memo = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_MEMO_COLUMN);

			String finecoCategory = ExcelSheetAnalysisLogic.getCellStringValue(cellRow, TRANSACTION_CATEGORY_COLUMN);
			finecoCategory = finecoCategory!=null ? finecoCategory.trim() : "";

			String gnucashCategory = null;
			if(finecoCategory.contains(TRANSACTION_TRANSFER_BANK_FEE) || finecoCategory.contains(TRANSACTION_TRANSFER_BANK_FEE_GOV) || finecoCategory.contains(TRANSACTION_TRANSFER_BANK_FEE_BONUS)) {
				gnucashCategory = ACCOUNT_NAME_BANK_FEE;
			} else if(finecoCategory.contains(TRANSACTION_TRANSFER_ATM_WITHDRAW)) {
				gnucashCategory = ACCOUNT_NAME_ATM_WITHDRAW;
			} else if(finecoCategory.contains(TRANSACTION_TRANSFER_PHONE_BILL)) {
				gnucashCategory = ACCOUNT_NAME_PHONE_BILL;
			} else if(finecoCategory.contains(TRANSACTION_TRANSFER_CREDIT_CARD)) {
				gnucashCategory = ACCOUNT_NAME_CREDIT_CARD;
			}
			
			BankTransaction bankTransaction = new BankTransaction(date, amount, memo, gnucashCategory);

			transactions.add(bankTransaction);
			log.debug("transaction[" + i + "] = " + bankTransaction);
		}
    }

    // -------------------------------------------------------------------------

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBankOwner() {
		return bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public List<BankTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<BankTransaction> transactions) {
		this.transactions = transactions;
	}

}
