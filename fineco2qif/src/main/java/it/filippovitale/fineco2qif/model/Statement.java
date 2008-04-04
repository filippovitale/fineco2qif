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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public abstract class Statement {

	protected HSSFSheet sheet;
	protected static final Logger log = Logger.getLogger(Statement.class);

    
    public Statement(HSSFSheet sheet) {
    	this.sheet = sheet;

    	if(sheet==null) {
            log.warn("The sheet is null!");
		} else {
	    	populateMetadata();
	    	populateTransaction();
		}    	
    }

    protected abstract void populateMetadata();
	protected abstract void populateTransaction();
    

}
