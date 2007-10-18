package it.filippovitale.fineco2qif.model;

public enum QIFStatementIdentifier {
	BANK_ACCOUNT_STATEMENT("!Type:Bank\r\n"),
	CREDIT_CARD_STATEMENT("!Type:CCard\r\n");
	
	private final String qifRepresentation;
	QIFStatementIdentifier(String qifRepresentation) {
		this.qifRepresentation = qifRepresentation;
	}
	public String toString() {
		return qifRepresentation;
	}
}
