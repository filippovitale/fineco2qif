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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

public class CCTransaction {

    private static final Logger log = Logger.getLogger(CCTransaction.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0#", new DecimalFormatSymbols(Locale.ENGLISH));

    Date date;
    Double amount;
    String memo;
    String category;

    public CCTransaction(String date, String amount, String memo, String category) {
		this.date = getDate(date);
		this.amount = getAmount(amount);
		this.memo = removeSpaces(memo);
    	this.category = category;
	}
    
    public String toString() {
    	return dateFormat.format(date) + " -\t" + (new DecimalFormat("0.00")).format(amount) + "\t- " + (category!=null ? "(" + category + ")" : "") + " " + memo;
    }

	private static Date getDate(String date) {
		Date ret = new Date();
		try {
			ret = dateFormat.parse(date);
		} catch (ParseException e) {
			log.error("date \"" + date + "\" not recognized");
		}
		return ret;
	}

    private static double getAmount(String amount) {
    	double ret = 0.0;
		try {
			ret = decimalFormat.parse(amount).doubleValue();
		} catch (ParseException e) {
			log.error("amount \"" + amount + "\" not recognized");
		}
		return ret;
	}

	private static String removeSpaces(String stringWithSpaces) {
    	String ret = stringWithSpaces.trim();
    	
    	// TODO
    	// converti tab in sp
    	// cicla rimpiazzando "  " in " " fino a quando non ci sono + "  "
    	
    	return ret;
    }

    // -------------------------------------------------------------------------

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
