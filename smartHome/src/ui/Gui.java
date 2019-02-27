package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import models.SmartHomeInstances;
import tools.JenaEngine;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Model model;
	private String ns;
    private String rdf;


    private JPanel pnlRequests;
	private JLabel lblHour;
	private JLabel lblSeason;
	private JSpinner spinHour;
	private JSpinner spinMinute;
	private JComboBox cmbSeason;
	
	private JLabel lblDesiredTemperature;
	private JLabel lblDesiredLight;
	private JComboBox cmbDesiredTemperature;
	private JComboBox cmbDesiredLight;
	
	private JButton btnConfirmTemperature;

	private JPanel pnlRelActions;
	private JTextArea txtareaRelevantAct;

	private Resource desiredTemp;
	private Resource sensorTemp;
	private Resource relAction;
	private Property propIntensity;
	private Property propName;
	private Property propType;
	
	
	
	public Gui() {
		super();
		this.setTitle("Smart Home");
	    this.setSize(1200, 700);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		this.getContentPane().add(initUserRequestsPanel(), BorderLayout.WEST);

		this.getContentPane().add(initRelevantActionsPanel(), BorderLayout.SOUTH);

		initEventListeners();
		
		initDataModel();
		
	    this.setVisible(true);
	}
	
	private JPanel initUserRequestsPanel() {
		pnlRequests = new JPanel();
		
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
		
		btnConfirmTemperature = new JButton("Confirmer");
		
		pnlRequests.add(lblHour);
		pnlRequests.add(spinHour);
		pnlRequests.add(spinMinute);
		
		pnlRequests.add(lblSeason);
		pnlRequests.add(cmbSeason);

		pnlRequests.add(lblDesiredLight);
		pnlRequests.add(cmbDesiredLight);

		pnlRequests.add(lblDesiredTemperature);
		pnlRequests.add(cmbDesiredTemperature);
		
		pnlRequests.add(btnConfirmTemperature);
				
		return pnlRequests;
	}

	private JPanel initRelevantActionsPanel() {
		pnlRelActions = new JPanel(new BorderLayout());

		txtareaRelevantAct = new JTextArea("Relevant actions :\n");
        txtareaRelevantAct.setEditable(false);
        txtareaRelevantAct.setRows(10);

		pnlRelActions.add(txtareaRelevantAct, BorderLayout.CENTER);

		return pnlRelActions;
	}

	private void initDataModel() {
        model = JenaEngine.readModel("data/smartHome.owl");
        ns = model.getNsPrefixURI("");
        rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        model = JenaEngine.readInferencedModelFromRuleFile(model, "data/owlrules.txt");
        (new SmartHomeInstances()).createAll(model, ns);

        propName = model.getProperty(ns + "name");
        propIntensity = model.getProperty(ns + "intensity");
        propType = model.getProperty(rdf+"type");

        relAction = model.getResource(ns + "RelevantAction");
        desiredTemp = model.getResource(ns + "BedroomTempRequest");
        System.out.println(desiredTemp + " \"" + desiredTemp.getProperty(propName).getString() + "\"  -  " + desiredTemp.getProperty(propIntensity).getInt());
        sensorTemp = model.getResource(ns + "BedroomTempSensor");
        System.out.println(sensorTemp + " \"" + sensorTemp.getProperty(propName).getString() + "\"  -  " + sensorTemp.getProperty(propIntensity).getInt());
    }

	private void initEventListeners() {
		btnConfirmTemperature.addActionListener(e -> {
            System.out.println("Temperature désirée : " + cmbDesiredTemperature.getSelectedItem() +"°C");

            desiredTemp.removeAll(propIntensity);
            desiredTemp.addLiteral(propIntensity, cmbDesiredTemperature.getSelectedItem());

            //Calcul
            clearRelevantActionsPanel();
            model = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            refreshRelevantActionsPanel();
        });
	}

	private void clearRelevantActionsPanel() {
        List<QuerySolution> results = new ArrayList<QuerySolution>();
        getRelevantActions().forEachRemaining(results::add);
        Resource resource;
        for(QuerySolution qs : results) {
            resource = qs.getResource("act");
            if(resource != null) {
                System.out.println(resource.getProperty(propType).asTriple().getObject().getLocalName());
                if (resource.getProperty(propType).asTriple().getObject().getLocalName().equals("RelevantAction"))
                    model.remove(resource.getProperty(propType));
            }
        }
    }

    private void refreshRelevantActionsPanel() {
	    StringBuffer updateRelActions = new StringBuffer("Relevant actions :\n");
        ResultSet relAction = getRelevantActions();
        Resource resource, obj;
        QuerySolution act;
        while(relAction.hasNext()) {
            act = relAction.next();
            resource = act.getResource("act");
            obj = act.getResource("obj");
            updateRelActions.append(resource.getLocalName()+ " --> " + (obj!=null?obj.getLocalName():"") + "\n");
        }
        txtareaRelevantAct.setText(updateRelActions.toString());
    }

	private ResultSet getRelevantActions() {
		String query =
				"PREFIX ns: <http://www.semanticweb.org/camille/ontologies/2019/1/untitled-ontology-6#>" +
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
						"SELECT ?act ?obj " +
						"WHERE {" +
						"?act rdf:type ns:RelevantAction ." +
						" OPTIONAL { ?obj rdf:type ns:Object . ?obj ns:hasAction ?act } ." +
						"}";

		QueryExecution qe = QueryExecutionFactory.create(query,model);
		return qe.execSelect();
	}
	
	public static void main(String args[]){
       new Gui();
    }
}
