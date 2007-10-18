package it.filippovitale.fineco2qif.model;

import java.util.ArrayList;
import java.util.List;

public class QIFStatement {

	private QIFStatementIdentifier identifier;
	private List<QIFTransaction> transactions = new ArrayList<QIFTransaction>();

	// -------------------------------------------------------------------------
	
	public QIFStatement(QIFStatementIdentifier identifier) {
		this.identifier = identifier;
	}
	
	public String toString() {
		StringBuffer qifStatementRepresentation = new StringBuffer();

		qifStatementRepresentation.append(identifier);
		for (QIFTransaction transaction : transactions) {
			qifStatementRepresentation.append(transaction);
		}
		
		return qifStatementRepresentation.toString();
	}

	
	// -------------------------------------------------------------------------

	public QIFStatementIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(QIFStatementIdentifier identifier) {
		this.identifier = identifier;
	}

	public List<QIFTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<QIFTransaction> transactions) {
		this.transactions = transactions;
	}

}
