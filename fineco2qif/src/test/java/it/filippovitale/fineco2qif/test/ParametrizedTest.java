package it.filippovitale.fineco2qif.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParametrizedTest {

    private int param;
    private int result;

    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {0, 0},
                {1, 1},
                {2, 4},
                {4, 16},		// OK : 4² = 16
                {5, 25},
                {6, 36},
                //{7, 48}		// NOK : 7² = 49 not 48
                {7, 49}
        });
    }

    public ParametrizedTest(int param, int result) {
        this.param = param;
        this.result = result;
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
