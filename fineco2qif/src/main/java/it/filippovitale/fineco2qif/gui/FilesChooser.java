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
package it.filippovitale.fineco2qif.gui;

import it.filippovitale.fineco2qif.logic.ModelConversionLogic;
import it.filippovitale.fineco2qif.model.StatementType;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class FilesChooser extends JFrame {
    private static final String LOG4J_CONFIGURATION_FILENAME = "log4j.xml";
    private static final Logger log = Logger.getLogger(FilesChooser.class);

	private static final long serialVersionUID = 1L;
	
	private ResourceBundle labels;
	private JButton openBankButton;
	private JButton openCCButton;
	
	private File currentInputFile;
	private String currentInputFilename;
	private StatementType currentStatementType;
	private String currentOutputFilename;
	
	public FilesChooser() {
		super("FilesChooser Frame");
		int frameWidth = 340;
		int frameHeight = 100;
		setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frameWidth) / 2;
		int y = (screenSize.height - frameHeight) / 2;
		setLocation(x, y); 
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		try {
			labels = ResourceBundle.getBundle("LabelsBundle");
		} catch (Exception e) {
			String errorMsg = "can't load \"LabelsBundle\"";
			log.error(errorMsg, e);
			JOptionPane.showMessageDialog(null, errorMsg, errorMsg, JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		openBankButton = new JButton(labels.getString("button.open.bank.file"));
		openCCButton = new JButton(labels.getString("button.open.cc.file"));

		openBankButton.addActionListener(getOpenFileChoser(StatementType.BANK_STATEMENT));
		openCCButton.addActionListener(getOpenFileChoser(StatementType.CC_STATEMENT));

		c.add(openBankButton);
		c.add(openCCButton);
	}

	private ActionListener getOpenFileChoser(final StatementType statementType) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {openFileChoser(statementType);}
		};
		return actionListener;
	}

	private void openFileChoser(StatementType statementType) {
		openBankButton.setEnabled(false);
		openCCButton.setEnabled(false);
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		int option = chooser.showOpenDialog(FilesChooser.this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			currentInputFilename = selectedFile.getPath();
			log.debug("Input File : " + currentInputFilename);
			String proposedFilename = selectedFile.getPath();
			proposedFilename = proposedFilename.substring(0, proposedFilename.length() - 3) + "qif";
			currentInputFile = new File(proposedFilename);
			currentStatementType = statementType;
			saveFileChoser();
		}
		openBankButton.setEnabled(true);
		openCCButton.setEnabled(true);
	}

	private void saveFileChoser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(currentInputFile);
		int option = chooser.showSaveDialog(FilesChooser.this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			currentOutputFilename = selectedFile.getPath();
			log.debug("Output File : " + currentOutputFilename);
			convertAndSave();
		}
	}

	private void convertAndSave() {
		int confirmation = JOptionPane.showConfirmDialog(null, createInfoMessage(), "info", JOptionPane.YES_NO_OPTION);
		if(confirmation == JOptionPane.YES_OPTION) {
			ModelConversionLogic.convertAndSave(currentInputFilename, currentOutputFilename, currentStatementType);
			JOptionPane.showMessageDialog(null, labels.getString("message.conversion.done"), labels.getString("message.conversion.done"), JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	private String createInfoMessage() {
		StringBuffer msg = new StringBuffer();
		
		msg.append(labels.getString("message.info.statementtype"));
		msg.append(" : \t" + currentStatementType + "\n");
		msg.append(labels.getString("message.info.inputfile"));
		msg.append(" : \t" + currentInputFilename + "\n");
		msg.append(labels.getString("message.info.outputfile"));
		msg.append(" : \t" + currentOutputFilename + "\n");
		msg.append("\n");
		msg.append(labels.getString("message.conversion.confirm") + "\n");
		
		return msg.toString();
	}
	
    private static void init() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(LOG4J_CONFIGURATION_FILENAME);
        if (resource != null) {
            DOMConfigurator.configure(resource);
        } else {
            System.err.println("Log4J config file \"" + LOG4J_CONFIGURATION_FILENAME + "\" not found");
        }
        
        @SuppressWarnings("unused")
		ApplicationContext ctx = new GenericApplicationContext();
    }
    
	public static void main(String args[]) {
		init();
		FilesChooser sfc = new FilesChooser();
		sfc.setVisible(true);
	}
	
}
