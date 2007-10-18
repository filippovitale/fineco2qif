package it.filippovitale.fineco2qif.test.model;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import it.filippovitale.fineco2qif.model.QIFStatementIdentifier;

import org.junit.Test;

public class QIFStatementIdentifierTest {

	@Test
	public void assertEveryRepresentationStartWithExclamation() {
		for (QIFStatementIdentifier si : QIFStatementIdentifier.values()) {
			assertThat(si.toString(), containsString("!Type:"));
		}
	}


}
