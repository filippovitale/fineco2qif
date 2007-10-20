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
package it.filippovitale.fineco2qif;

import it.filippovitale.fineco2qif.model.QIFStatement;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

public class Main {
    private static final String LOG4J_CONFIGURATION_FILENAME = "log4j.xml";
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        init();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("fineco2qif.xml");
        QIFStatement statement = (QIFStatement) ctx.getBean("bankaccountStatement");

        log.info("-------------------- BEGIN --------------------\n" + statement.toString());
        log.info("--------------------  END  --------------------");
    }

    private static void init() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(LOG4J_CONFIGURATION_FILENAME);
        if (resource != null) {
            DOMConfigurator.configure(resource);
        }
    }

}
