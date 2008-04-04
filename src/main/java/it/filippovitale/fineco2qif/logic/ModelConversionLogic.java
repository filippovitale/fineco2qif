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
package it.filippovitale.fineco2qif.logic;

import it.filippovitale.fineco2qif.model.BankStatement;
import it.filippovitale.fineco2qif.model.BankTransaction;
import it.filippovitale.fineco2qif.model.CCStatement;
import it.filippovitale.fineco2qif.model.CCTransaction;
import it.filippovitale.fineco2qif.model.QIFStatement;
import it.filippovitale.fineco2qif.model.QIFTransaction;
import it.filippovitale.fineco2qif.model.StatementType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ModelConversionLogic {
    private static final Logger log = Logger.getLogger(ModelConversionLogic.class);

	public static QIFStatement generateQIF(String accountName, CCStatement ccStatement) {
		QIFStatement ret;
		
		ret = new QIFStatement(QIFStatement.Type.CREDIT_CARD, accountName);
		List<QIFTransaction> qifTransactions = new ArrayList<QIFTransaction>();
		
		List<CCTransaction> ccTransactions = ccStatement.getTransactions();
		for (CCTransaction ccTransaction : ccTransactions) {
			QIFTransaction qifTransaction = new QIFTransaction(ccTransaction.getDate(), ccTransaction.getAmount(), ccTransaction.getMemo());
			qifTransaction.setCategory(ccTransaction.getCategory());
			qifTransactions.add(qifTransaction);
		}

		ret.setTransactions(qifTransactions);
		
		return ret;
	}

	public static QIFStatement generateQIF(String accountName, BankStatement bankStatement) {
		QIFStatement ret;
		
		ret = new QIFStatement(QIFStatement.Type.BANK_ACCOUNT, accountName);
		List<QIFTransaction> qifTransactions = new ArrayList<QIFTransaction>();
		
		List<BankTransaction> bankTransactions = bankStatement.getTransactions();
		for (BankTransaction bankTransaction : bankTransactions) {
			QIFTransaction qifTransaction = new QIFTransaction(bankTransaction.getDate(), bankTransaction.getAmount(), bankTransaction.getMemo());
			qifTransaction.setCategory(bankTransaction.getCategory());
			qifTransactions.add(qifTransaction);
		}
		
		ret.setTransactions(qifTransactions);
		
		return ret;
	}

	public static void convertAndSave(String inputFilename, String outputFilename, StatementType statementType) {
		// TODO analyse the XLS file and recognize the statementType
		HSSFSheet sheet = ExcelSheetAnalysisLogic.getSheetFromFile(inputFilename, statementType.sheetName);
		if(statementType == StatementType.CC_STATEMENT) {
			CCStatement ccStatement = new CCStatement(sheet);
			QIFStatement ccQifStatement = generateQIF(statementType.accountName, ccStatement);		
			saveOnFile(outputFilename, ccQifStatement);
		} else if(statementType == StatementType.BANK_STATEMENT) {
			BankStatement bankStatement = new BankStatement(sheet);
			QIFStatement bankQifStatement = generateQIF(statementType.accountName, bankStatement);		
			saveOnFile(outputFilename, bankQifStatement);
		}
	}

    private static void saveOnFile(String fileName, QIFStatement qifStatement) {
		try {
			FileOutputStream fileWriter = new FileOutputStream(fileName);
			Writer writer = new OutputStreamWriter(fileWriter, Charset.forName("US-ASCII")); // ISO-8859-1
			writer.write(qifStatement.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			String errorMsg = "can't save on file \"" + fileName + "\"";
			log.error(errorMsg, e);
			JOptionPane.showMessageDialog(null, errorMsg, errorMsg, JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
    }

}
