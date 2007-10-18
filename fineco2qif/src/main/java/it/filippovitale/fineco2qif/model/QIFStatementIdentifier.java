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
