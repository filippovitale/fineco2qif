package it.filippovitale.fineco2qif.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class MainTest {
	@BeforeClass public static void systemSetup() {
		// run once
	}

	@Before public void beforeEveryTest() {
		// run once
	}

	@After public void afterEveryTest() {
		// run once
	}

	@Test
	public void prova() {
		assertThat(true, is(true));
		assert true;
		assertThat(false, is(not(true)));
		assert !true;
	}


	@Ignore("not now, please") 
	@Test
    public void divideByZeroNotNow() {
        int a = 1/0;
        a++;
    }

	@Test(timeout=20, expected=ArithmeticException.class)
    public void divideByZero() {
		// it should take less than 20ms
        int a = 1/0;
        a++;
    }

	@AfterClass public static void systemShutdown() {
		// run once
	}

}
