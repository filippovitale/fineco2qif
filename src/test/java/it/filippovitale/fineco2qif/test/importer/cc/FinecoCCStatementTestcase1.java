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
package it.filippovitale.fineco2qif.test.importer.cc;

import java.io.File;
import java.io.IOException;

import it.filippovitale.fineco2qif.logic.ExcelSheetAnalysisLogic;
import it.filippovitale.fineco2qif.model.StatementType;
import static org.junit.Assert.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.Test;

public class FinecoCCStatementTestcase1 {

	private static final String BANK_TESTCASE_FILENAME = "src/test/resources/xls/Movimenti-testcase1.xls";
	private static final String VISA_TESTCASE_FILENAME = "src/test/resources/xls/VISA-testcase2.xls";
    
    
    @Test
    public void assertBankFileIsReadable() throws IOException {
		String testcaseFilename = (new File(BANK_TESTCASE_FILENAME)).getCanonicalPath();
        HSSFSheet sheet = ExcelSheetAnalysisLogic.getSheetFromFile(testcaseFilename, StatementType.BANK_STATEMENT.sheetName);
    	assertNotNull(sheet);
    }

    @Test
    public void assertCCFileIsReadable() throws IOException {
		String testcaseFilename = (new File(VISA_TESTCASE_FILENAME)).getCanonicalPath();
		HSSFSheet sheet = ExcelSheetAnalysisLogic.getSheetFromFile(testcaseFilename, StatementType.CC_STATEMENT.sheetName);
    	assertNotNull(sheet);
    }

}
