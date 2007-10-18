package it.filippovitale.fineco2qif.model;

import java.util.ArrayList;
import java.util.List;

public class QIFTransaction {

	private List<QIFsplit> splits = new ArrayList<QIFsplit>();

	// -------------------------------------------------------------------------
	
	public QIFTransaction() {
	}
	
	public String toString() {
		StringBuffer qifTransactionRepresentation = new StringBuffer();

		//qifTransactionRepresentation.append(????); // TODO QIFTransaction representation
		for (QIFsplit split : splits) {
			qifTransactionRepresentation.append(split);
		}
		
		return qifTransactionRepresentation.toString();
	}

	// -------------------------------------------------------------------------

	public List<QIFsplit> getSplits() {
		return splits;
	}

	public void setSplits(List<QIFsplit> splits) {
		this.splits = splits;
	}

}
