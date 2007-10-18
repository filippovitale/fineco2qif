package it.filippovitale.fineco2qif;

import it.filippovitale.fineco2qif.model.QIFStatement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("fineco2qif.xml");
		QIFStatement statement = (QIFStatement) ctx.getBean("statement");

		System.out.println(statement.toString());
	}

}
