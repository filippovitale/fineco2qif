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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Vademecum {

	private int param;
	private int result;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 4 },
				{ 4, 16 }, { 5, 25 }, { 6, 36 }, { 7, 49 } });
	}

	public Vademecum(int param, int result) {
		this.param = param;
		this.result = result;
	}

	// -------------------------------------------------------------------------

	@BeforeClass
	public static void systemSetup() {
		// run once
	}

	@AfterClass
	public static void systemShutdown() {
		// run once
	}

	@Before
	public void beforeEveryTest() {
		// run before every test
	}

	@After
	public void afterEveryTest() {
		// run after every test
	}

	// -------------------------------------------------------------------------

	@Test
	public void square() {
		int paramsquare = param * param;
		assertEquals(result, paramsquare);
	}

	@Test
	public void square2() {
		int paramsquare = param * (param - 1) + param;
		assertEquals(result, paramsquare);
	}
	
	@Test(timeout=20, expected=RuntimeException.class)
	@Ignore("not implemented yet") 
    public void importCreditcardSample1() {
		throw new RuntimeException("not implemented yet"); 
    }
	
}
