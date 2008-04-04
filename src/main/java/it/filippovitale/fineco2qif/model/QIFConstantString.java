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

public class QIFConstantString {

    public static final String EOL = "\r\n";

    public static final String STATEMENT_HEADER_ACCOUNT_NAME = "!Account" + EOL;
    public static final String STATEMENT_HEADER_ACCOUNT_NAME_PREFIX = "N";
    public static final String STATEMENT_HEADER_ACCOUNT_NAME_FOOTER = "^";

    public static final String STATEMENT_HEADER_BANK_ACCOUNT = "!Type:Bank" + EOL;
    public static final String STATEMENT_HEADER_CREDIT_CARD = "!Type:CCard" + EOL;

    public static final String TRANSACTION_DATE_PREFIX = "D";
    public static final String TRANSACTION_AMOUNT_PREFIX = "T";
    public static final String TRANSACTION_PAYEE_PREFIX = "P";
    public static final String TRANSACTION_MEMO_PREFIX = "M";
    public static final String TRANSACTION_CATEGORY_PREFIX = "L";

    public static final String TRANSACTION_CLEARED_RECONCILIED = "C*";
    public static final String TRANSACTION_CLEARED_CLEARED = "CX";

    public static final String TRANSACTION_FOOTER = "^";

}
