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
