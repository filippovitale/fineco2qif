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

import java.util.ResourceBundle;

public enum StatementType {

	BANK_STATEMENT("Movimenti Conto Corrente", "Conto Fineco"),
	CC_STATEMENT("Movimenti", "VISA Fineco");

	private static ResourceBundle labels;
	
	static {
		try {
			labels = ResourceBundle.getBundle("LabelsBundle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public final String sheetName;
	public final String accountName;
	StatementType(String sheetName, String accountName) {
		this.sheetName = sheetName;
		this.accountName = accountName;
	}
	
	public String toString() {
		if(labels!=null) {
			if(this == BANK_STATEMENT) {
				return labels.getString("tostring.bankstatement");
			} else if(this == BANK_STATEMENT) {
				return labels.getString("tostring.ccstatement");
			}
		}
		return "";
	}

}