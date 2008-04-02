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

import static it.filippovitale.fineco2qif.model.QIFConstantString.*;

import java.util.ArrayList;
import java.util.List;


public class QIFStatement {

    private Type type;
	private String accountName = null;
    private List<QIFTransaction> transactions = new ArrayList<QIFTransaction>();

    // -------------------------------------------------------------------------

    public static enum Type {
        BANK_ACCOUNT(STATEMENT_HEADER_BANK_ACCOUNT),
        CREDIT_CARD(STATEMENT_HEADER_CREDIT_CARD);

        private final String qifRepresentation;

        Type(String qifRepresentation) {
            this.qifRepresentation = qifRepresentation;
        }

        public String toString() {
            return qifRepresentation;
        }
    }

    // -------------------------------------------------------------------------

    public QIFStatement(Type type) {
        this.type = type;
    }

    public QIFStatement(Type type, String accountName) {
        this.type = type;
        this.accountName = accountName;
    }

    public String toString() {
        StringBuffer qifStatementRepresentation = new StringBuffer();

        if(accountName!=null) {
        	qifStatementRepresentation.append(STATEMENT_HEADER_ACCOUNT_NAME);
            qifStatementRepresentation.append(STATEMENT_HEADER_ACCOUNT_NAME_PREFIX).append(accountName).append(EOL);
            qifStatementRepresentation.append(STATEMENT_HEADER_ACCOUNT_NAME_FOOTER).append(EOL);
        }
        
        qifStatementRepresentation.append(type);
        for (QIFTransaction transaction : transactions) {
            qifStatementRepresentation.append(transaction);
        }

        return qifStatementRepresentation.toString();
    }

    // -------------------------------------------------------------------------

    public Type getIdentifier() {
        return type;
    }

    public void setIdentifier(Type type) {
        this.type = type;
    }

    public List<QIFTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<QIFTransaction> transactions) {
        this.transactions = transactions;
    }
}
