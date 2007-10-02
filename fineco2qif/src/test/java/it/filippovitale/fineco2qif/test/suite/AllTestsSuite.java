package it.filippovitale.fineco2qif.test.suite;

import it.filippovitale.fineco2qif.test.MainTest;
import it.filippovitale.fineco2qif.test.Vademecum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { MainTest.class, Vademecum.class })
public class AllTestsSuite {
}
