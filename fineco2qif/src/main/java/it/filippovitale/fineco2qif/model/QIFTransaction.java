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
