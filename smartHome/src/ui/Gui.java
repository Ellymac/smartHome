package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

import com.hp.hpl.jena.rdf.model.Model;

import models.SmartHomeInstances;
import tools.JenaEngine;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Model model;
	private String ns;
	
	
	private JPanel pnlInformation;
	private JLabel lblHour;
	private JLabel lblSeason;
	private JSpinner spinHour;
	private JSpinner spinMinute;
	private JComboBox cmbSeason;
	
	private JLabel lblDesiredTemperature;
	private JLabel lblDesiredLight;
	private JComboBox cmbDesiredTemperature;
	private JComboBox cmbDesiredLight;
	
	private JButton btnConfirmValues;
	
	
	
	public Gui() {
		super();
		this.setTitle("Smart Home");
	    this.setSize(1200, 700);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		this.getContentPane().add(initInformationPanel(), BorderLayout.WEST);

		initEventListeners();
		
		initModel();
		
	    this.setVisible(true);
	}
	
	private JPanel initInformationPanel() {
		pnlInformation = new JPanel();
		
		lblHour = new JLabel("Heure : ");
		SpinnerNumberModel spinModelHour = new SpinnerNumberModel(12.0, 1.0, 24.0, 1.0);  
		spinHour = new JSpinner(spinModelHour);
		SpinnerNumberModel spinModelMinute = new SpinnerNumberModel(0.0, 0.0, 59.0, 1.0);  
		spinMinute = new JSpinner(spinModelMinute);
		
		lblSeason = new JLabel("Saison : ");
		cmbSeason = new JComboBox<String>();
		cmbSeason.addItem("Été");
		cmbSeason.addItem("Hiver");
		cmbSeason.addItem("Printemps");
		cmbSeason.addItem("Automne");
		
		lblDesiredLight = new JLabel("Luminosité désirée : ");
		cmbDesiredLight = new JComboBox<String>();
		cmbDesiredLight.addItem("Tamisée");
		cmbDesiredLight.addItem("Jour");
		cmbDesiredLight.addItem("Nuit");

		lblDesiredTemperature = new JLabel("Température désirée : ");
		cmbDesiredTemperature = new JComboBox<String>();
		for(int i = 0; i < 35; i++)
			cmbDesiredTemperature.addItem(i);
		cmbDesiredTemperature.setSelectedItem(20);
		
		btnConfirmValues = new JButton("Confirmer");
		
		pnlInformation.add(lblHour);
		pnlInformation.add(spinHour);
		pnlInformation.add(spinMinute);
		
		pnlInformation.add(lblSeason);
		pnlInformation.add(cmbSeason);

		pnlInformation.add(lblDesiredLight);
		pnlInformation.add(cmbDesiredLight);

		pnlInformation.add(lblDesiredTemperature);
		pnlInformation.add(cmbDesiredTemperature);
		
		pnlInformation.add(btnConfirmValues);
				
		return pnlInformation;
	}
	
	private void initEventListeners() {
		
	}
	
	private void initModel() {
		model = JenaEngine.readModel("data/smartHome.owl");
		ns = model.getNsPrefixURI("");
		model = JenaEngine.readInferencedModelFromRuleFile(model, "data/owlrules.txt");
		(new SmartHomeInstances()).createAll(model, ns);
	}
	
	public static void main(String args[]){
       new Gui();
    }
}
