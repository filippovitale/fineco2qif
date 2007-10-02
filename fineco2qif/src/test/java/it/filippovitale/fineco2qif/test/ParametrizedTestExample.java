package it.filippovitale.fineco2qif.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParametrizedTestExample {

	private int param;
	private int result;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 4 },
				{ 4, 16 }, { 5, 25 }, { 6, 36 }, { 7, 49 } });
	}

	public ParametrizedTestExample(int param, int result) {
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

}
