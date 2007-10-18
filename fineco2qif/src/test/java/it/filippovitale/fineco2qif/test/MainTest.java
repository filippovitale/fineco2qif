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
package it.filippovitale.fineco2qif.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class MainTest {

	@Test
	@Ignore("not implemented yet") 
	public void assertThatTest() {
		assertThat(true, is(true));
		assert true;
		assertThat(false, is(not(true)));
		assert !true;
	}

	@Test(timeout=20, expected=RuntimeException.class)
	@Ignore("not implemented yet") 
    public void importCreditcardSample1() {
		throw new RuntimeException("not implemented yet"); 
    }

}
