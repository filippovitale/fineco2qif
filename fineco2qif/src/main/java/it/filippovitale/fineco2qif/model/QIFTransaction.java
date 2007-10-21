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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QIFTransaction {

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Date date;
    private Double amount;

    private ClearedStatus clearedStatus;
    private String payee;
    private String memo;
    private String account;
    private String category;
    private List<QIFSplit> splits = new ArrayList<QIFSplit>();

// -------------------------------------------------------------------------

    public static enum ClearedStatus {
        RECONCILIED(TRANSACTION_CLEARED_RECONCILIED),
        CLEARED(TRANSACTION_CLEARED_CLEARED);

        private final String clearedStatusRepresentation;

        ClearedStatus(String clearedStatusRepresentation) {
            this.clearedStatusRepresentation = clearedStatusRepresentation;
        }

        public String toString() {
            return clearedStatusRepresentation;
        }
    }

// -------------------------------------------------------------------------

    public QIFTransaction(Date date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public String toString() {
        StringBuffer qifTransactionRepresentation = new StringBuffer();

        qifTransactionRepresentation.append(TRANSACTION_DATE_PREFIX);
        qifTransactionRepresentation.append(dateFormat.format(date));
        qifTransactionRepresentation.append(EOL);

        qifTransactionRepresentation.append(TRANSACTION_AMOUNT_PREFIX); // TODO investigate: amount or total?
        qifTransactionRepresentation.append(decimalFormat.format(amount));
        qifTransactionRepresentation.append(EOL);

        if (clearedStatus != null) {
            qifTransactionRepresentation.append(clearedStatus);
            qifTransactionRepresentation.append(EOL);
        }

        if (payee != null) {
            qifTransactionRepresentation.append(TRANSACTION_PAYEE_PREFIX);
            qifTransactionRepresentation.append(payee);
            qifTransactionRepresentation.append(EOL);
        }

        if (memo != null) {
            qifTransactionRepresentation.append(TRANSACTION_MEMO_PREFIX);
            qifTransactionRepresentation.append(memo);
            qifTransactionRepresentation.append(EOL);
        }

        if (category != null) {
            qifTransactionRepresentation.append(TRANSACTION_CATEGORY_PREFIX);
            if (account != null) {
                qifTransactionRepresentation.append(account);
                qifTransactionRepresentation.append(":");
            }
            qifTransactionRepresentation.append(category);
            qifTransactionRepresentation.append(EOL);
        } else {
            if (account != null) {
                qifTransactionRepresentation.append(TRANSACTION_CATEGORY_PREFIX);
                qifTransactionRepresentation.append("[");
                qifTransactionRepresentation.append(account);
                qifTransactionRepresentation.append("]");
                qifTransactionRepresentation.append(EOL);
            }
        }

        //for (QIFSplit split : splits) {               // TODO QIFSplit representation
        //	qifTransactionRepresentation.append(split);
        //}

        qifTransactionRepresentation.append(TRANSACTION_FOOTER).append(EOL);

        return qifTransactionRepresentation.toString();
    }

    // -------------------------------------------------------------------------

    public List<QIFSplit> getSplits() {
        return splits;
    }

    public void setSplits(List<QIFSplit> splits) {
        this.splits = splits;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ClearedStatus getClearedStatus() {
        return clearedStatus;
    }

    public void setClearedStatus(ClearedStatus clearedStatus) {
        this.clearedStatus = clearedStatus;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
