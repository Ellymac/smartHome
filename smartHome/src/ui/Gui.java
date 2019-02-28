package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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

    private JPanel pnl0;
    private JTabbedPane tabpnlRooms;

    private JPanel tabBedroom;
    private JPanel tabLivingroom;
    private JPanel tabBathroom;
    private JPanel tabKitchen;


    private ButtonGroup rbgPnlBedroom;
    private ButtonGroup rbgPnlLivingroom;
    private ButtonGroup rbgPnlBathroom;
    private ButtonGroup rbgPnlKitchen;

    private JLabel lblTempRequested;
    private JLabel lblTempBedroom;
    private JLabel lblTempLivingroom;
    private JLabel lblTempBathroom;
    private JLabel lblTempKitchen;

    private JRadioButton rbRadWakeup;
    private JSpinner spinHourWakeup;
    private JSpinner spinMinuteWakeup;
    private JButton btnTempBedroom;
    private JButton btnTempLivingroom;
    private JButton btnTempBathroom;
    private JButton btnTempKitchen;

    private JLabel lblPoints;

    private JLabel lblHour;
	private JSpinner spinHour;
	private JSpinner spinMinute;

	private JComboBox cmbDesiredTempBedroom;
    private JComboBox cmbDesiredTempKitchen;
    private JComboBox cmbDesiredTempBathroom;
    private JComboBox cmbDesiredTempLivingroom;

	private JPanel pnlRelActions;
	private JTextArea txtareaRelevantAct;

	private Resource desiredTempBedroom;
    private Resource desiredTempBathroom;
    private Resource desiredTempLivingroom;
    private Resource desiredTempKitchen;
    private Resource currentClock;
    private Resource wakeupClock;

    private Resource sensorTemp;
	private Property propIntensity;
	private Property propName;
	private Property propType;
	private Property propHour;
	private Property propMinute;
	private Property propIsIn;
	
	public Gui() {
		super();
		this.setTitle("Smart Home");
	    this.setSize(800, 400);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().add(initTabView());
        this.getContentPane().add(initRelevantActionsPanel(), BorderLayout.SOUTH);

		initEventListeners();
		
		initDataModel();

		initModelInView();
		
	    this.setVisible(true);
	}

	private JPanel initTabView() {
        pnl0 = new JPanel();
        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        pnl0.setLayout( gbPanel0 );

        lblHour = new JLabel("Heure : ");
        SpinnerNumberModel spinModelHour = new SpinnerNumberModel(12.0, 1.0, 24.0, 1.0);
        spinHour = new JSpinner(spinModelHour);
        SpinnerNumberModel spinModelMinute = new SpinnerNumberModel(0.0, 0.0, 59.0, 1.0);
        spinMinute = new JSpinner(spinModelMinute);
        pnl0.add(lblHour);
        pnl0.add(spinHour);
        pnl0.add(spinMinute);

        tabpnlRooms = new JTabbedPane( );

        initTabBedroom();

        initTabLivingroom();

        initTabKitchen();

        initTabBathroom();

        tabpnlRooms.addTab("Chambre",tabBedroom);
        tabpnlRooms.addTab("Salle de bains", tabBathroom);
        tabpnlRooms.addTab("Cuisine", tabKitchen);
        tabpnlRooms.addTab("Salon", tabLivingroom);

        gbcPanel0.gridx = 0;
        gbcPanel0.gridy = 3;
        gbcPanel0.gridwidth = 20;
        gbcPanel0.gridheight = 12;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( tabpnlRooms, gbcPanel0 );
        pnl0.add( tabpnlRooms );

        return pnl0;
    }

    private void initTabBedroom() {
	    tabBedroom = new JPanel();
        GridBagLayout gbTabBedroom = new GridBagLayout();
        GridBagConstraints gbcTabBedroom = new GridBagConstraints();
        tabBedroom.setLayout( gbTabBedroom );

        rbgPnlBedroom = new ButtonGroup();
        GridBagLayout gbPnlBedroom = new GridBagLayout();
        GridBagConstraints gbcPnlBedroom = new GridBagConstraints();
        tabBedroom.setLayout( gbPnlBedroom );

        lblTempRequested = new JLabel( "Température désirée : "  );
        gbcPnlBedroom.gridx = 0;
        gbcPnlBedroom.gridy = 0;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 1;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( lblTempRequested, gbcPnlBedroom );
        tabBedroom.add( lblTempRequested );

        cmbDesiredTempBedroom = new JComboBox<String>();
        for(int i = 0; i < 35; i++)
            cmbDesiredTempBedroom.addItem(i);
        cmbDesiredTempBedroom.setSelectedItem(20);
        gbcPnlBedroom.gridx = 1;
        gbcPnlBedroom.gridy = 0;
        gbcPnlBedroom.weightx = 0;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( cmbDesiredTempBedroom, gbcPnlBedroom );
        tabBedroom.add( cmbDesiredTempBedroom );

        lblTempBedroom = new JLabel( "°C"  );
        gbcPnlBedroom.gridx = 18;
        gbcPnlBedroom.gridy = 0;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 1;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( lblTempBedroom, gbcPnlBedroom );
        tabBedroom.add( lblTempBedroom );

        rbRadWakeup = new JRadioButton( "Réveil"  );
        rbRadWakeup.setSelected( true );
        rbgPnlBedroom.add( rbRadWakeup );
        gbcPnlBedroom.gridx = 0;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( rbRadWakeup, gbcPnlBedroom );
        tabBedroom.add( rbRadWakeup );

        spinHourWakeup = new JSpinner( );
        gbcPnlBedroom.gridx = 1;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( spinHourWakeup, gbcPnlBedroom );
        tabBedroom.add( spinHourWakeup );

        lblPoints = new JLabel(":" );
        gbcPnlBedroom.gridx = 2;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( lblPoints, gbcPnlBedroom );
        tabBedroom.add( lblPoints );

        spinMinuteWakeup = new JSpinner( );
        gbcPnlBedroom.gridx = 3;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( spinMinuteWakeup, gbcPnlBedroom );
        tabBedroom.add( spinMinuteWakeup );

        btnTempBedroom = new JButton( "✓"  );
        gbcPnlBedroom.gridx = 3;
        gbcPnlBedroom.gridy = 0;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( btnTempBedroom, gbcPnlBedroom );
        tabBedroom.add( btnTempBedroom );

        gbcTabBedroom.gridx = 0;
        gbcTabBedroom.gridy = 0;
        gbcTabBedroom.fill = GridBagConstraints.HORIZONTAL;
        gbcTabBedroom.weightx = 1;
        gbcTabBedroom.weighty = 0;
        gbcTabBedroom.anchor = GridBagConstraints.NORTH;
        gbTabBedroom.setConstraints( tabBedroom, gbcTabBedroom );
    }

    private void initTabLivingroom() {
        tabLivingroom = new JPanel();
        GridBagLayout gbTabLivingroom = new GridBagLayout();
        GridBagConstraints gbcTabLivingroom = new GridBagConstraints();
        tabLivingroom.setLayout( gbTabLivingroom );

        rbgPnlLivingroom = new ButtonGroup();
        GridBagLayout gbPnlLivingroom = new GridBagLayout();
        GridBagConstraints gbcPnlLivingroom = new GridBagConstraints();
        tabLivingroom.setLayout( gbPnlLivingroom );

        lblTempRequested = new JLabel( "Température désirée : "  );
        gbcPnlLivingroom.gridx = 0;
        gbcPnlLivingroom.gridy = 0;
        gbcPnlLivingroom.weightx = 1;
        gbcPnlLivingroom.weighty = 1;
        gbcPnlLivingroom.anchor = GridBagConstraints.NORTH;
        gbPnlLivingroom.setConstraints( lblTempRequested, gbcPnlLivingroom );
        tabLivingroom.add( lblTempRequested );

        cmbDesiredTempLivingroom = new JComboBox<String>();
        for(int i = 0; i < 35; i++)
            cmbDesiredTempLivingroom.addItem(i);
        cmbDesiredTempLivingroom.setSelectedItem(20);
        gbcPnlLivingroom.gridx = 1;
        gbcPnlLivingroom.gridy = 0;
        gbcPnlLivingroom.weightx = 0;
        gbcPnlLivingroom.weighty = 0;
        gbcPnlLivingroom.anchor = GridBagConstraints.NORTH;
        gbPnlLivingroom.setConstraints( cmbDesiredTempLivingroom, gbcPnlLivingroom );
        tabLivingroom.add( cmbDesiredTempLivingroom );

        lblTempLivingroom = new JLabel( "°C"  );
        gbcPnlLivingroom.gridx = 18;
        gbcPnlLivingroom.gridy = 0;
        gbcPnlLivingroom.weightx = 1;
        gbcPnlLivingroom.weighty = 1;
        gbcPnlLivingroom.anchor = GridBagConstraints.NORTH;
        gbPnlLivingroom.setConstraints( lblTempLivingroom, gbcPnlLivingroom );
        tabLivingroom.add( lblTempLivingroom );

        btnTempLivingroom = new JButton( "✓"  );
        gbcPnlLivingroom.gridx = 3;
        gbcPnlLivingroom.gridy = 0;
        gbcPnlLivingroom.weightx = 1;
        gbcPnlLivingroom.weighty = 0;
        gbcPnlLivingroom.anchor = GridBagConstraints.NORTH;
        gbPnlLivingroom.setConstraints( btnTempLivingroom, gbcPnlLivingroom );
        tabLivingroom.add( btnTempLivingroom );

        gbcTabLivingroom.gridx = 0;
        gbcTabLivingroom.gridy = 0;
        gbcTabLivingroom.fill = GridBagConstraints.HORIZONTAL;
        gbcTabLivingroom.weightx = 1;
        gbcTabLivingroom.weighty = 0;
        gbcTabLivingroom.anchor = GridBagConstraints.NORTH;
        gbTabLivingroom.setConstraints( tabLivingroom, gbcTabLivingroom );
    }

    private void initTabKitchen() {
        tabKitchen = new JPanel();
        GridBagLayout gbTabKitchen = new GridBagLayout();
        GridBagConstraints gbcTabKitchen = new GridBagConstraints();
        tabKitchen.setLayout( gbTabKitchen );

        rbgPnlKitchen = new ButtonGroup();
        GridBagLayout gbPnlKitchen = new GridBagLayout();
        GridBagConstraints gbcPnlKitchen = new GridBagConstraints();
        tabKitchen.setLayout( gbPnlKitchen );

        lblTempRequested = new JLabel( "Température désirée : "  );
        gbcPnlKitchen.gridx = 0;
        gbcPnlKitchen.gridy = 0;
        gbcPnlKitchen.weightx = 1;
        gbcPnlKitchen.weighty = 1;
        gbcPnlKitchen.anchor = GridBagConstraints.NORTH;
        gbPnlKitchen.setConstraints( lblTempRequested, gbcPnlKitchen );
        tabKitchen.add( lblTempRequested );

        cmbDesiredTempKitchen = new JComboBox<String>();
        for(int i = 0; i < 35; i++)
            cmbDesiredTempKitchen.addItem(i);
        cmbDesiredTempKitchen.setSelectedItem(20);
        gbcPnlKitchen.gridx = 1;
        gbcPnlKitchen.gridy = 0;
        gbcPnlKitchen.weightx = 0;
        gbcPnlKitchen.weighty = 0;
        gbcPnlKitchen.anchor = GridBagConstraints.NORTH;
        gbPnlKitchen.setConstraints( cmbDesiredTempKitchen, gbcPnlKitchen );
        tabKitchen.add( cmbDesiredTempKitchen );

        lblTempKitchen = new JLabel( "°C"  );
        gbcPnlKitchen.gridx = 18;
        gbcPnlKitchen.gridy = 0;
        gbcPnlKitchen.weightx = 1;
        gbcPnlKitchen.weighty = 1;
        gbcPnlKitchen.anchor = GridBagConstraints.NORTH;
        gbPnlKitchen.setConstraints( lblTempKitchen, gbcPnlKitchen );
        tabKitchen.add( lblTempKitchen );

        btnTempKitchen = new JButton( "✓"  );
        gbcPnlKitchen.gridx = 3;
        gbcPnlKitchen.gridy = 0;
        gbcPnlKitchen.weightx = 1;
        gbcPnlKitchen.weighty = 0;
        gbcPnlKitchen.anchor = GridBagConstraints.NORTH;
        gbPnlKitchen.setConstraints( btnTempKitchen, gbcPnlKitchen );
        tabKitchen.add( btnTempKitchen );

        gbcTabKitchen.gridx = 0;
        gbcTabKitchen.gridy = 0;
        gbcTabKitchen.fill = GridBagConstraints.HORIZONTAL;
        gbcTabKitchen.weightx = 1;
        gbcTabKitchen.weighty = 0;
        gbcTabKitchen.anchor = GridBagConstraints.NORTH;
        gbTabKitchen.setConstraints( tabKitchen, gbcTabKitchen );
    }

    private void initTabBathroom() {
        tabBathroom = new JPanel();
        GridBagLayout gbTabBathroom = new GridBagLayout();
        GridBagConstraints gbcTabBathroom = new GridBagConstraints();
        tabBathroom.setLayout( gbTabBathroom );

        rbgPnlBathroom = new ButtonGroup();
        GridBagLayout gbPnlBathroom = new GridBagLayout();
        GridBagConstraints gbcPnlBathroom = new GridBagConstraints();
        tabBathroom.setLayout( gbPnlBathroom );

        lblTempRequested = new JLabel( "Température désirée : "  );
        gbcPnlBathroom.gridx = 0;
        gbcPnlBathroom.gridy = 0;
        gbcPnlBathroom.weightx = 1;
        gbcPnlBathroom.weighty = 1;
        gbcPnlBathroom.anchor = GridBagConstraints.NORTH;
        gbPnlBathroom.setConstraints( lblTempRequested, gbcPnlBathroom );
        tabBathroom.add( lblTempRequested );

        cmbDesiredTempBathroom = new JComboBox<String>();
        for(int i = 0; i < 35; i++)
            cmbDesiredTempBathroom.addItem(i);
        cmbDesiredTempBathroom.setSelectedItem(20);
        gbcPnlBathroom.gridx = 1;
        gbcPnlBathroom.gridy = 0;
        gbcPnlBathroom.weightx = 0;
        gbcPnlBathroom.weighty = 0;
        gbcPnlBathroom.anchor = GridBagConstraints.NORTH;
        gbPnlBathroom.setConstraints( cmbDesiredTempBathroom, gbcPnlBathroom );
        tabBathroom.add( cmbDesiredTempBathroom );

        lblTempBathroom = new JLabel();
        gbcPnlBathroom.gridx = 18;
        gbcPnlBathroom.gridy = 0;
        gbcPnlBathroom.weightx = 1;
        gbcPnlBathroom.weighty = 1;
        gbcPnlBathroom.anchor = GridBagConstraints.NORTH;
        gbPnlBathroom.setConstraints( lblTempBathroom, gbcPnlBathroom );
        tabBathroom.add( lblTempBathroom );

        btnTempBathroom = new JButton( "✓"  );
        gbcPnlBathroom.gridx = 3;
        gbcPnlBathroom.gridy = 0;
        gbcPnlBathroom.weightx = 1;
        gbcPnlBathroom.weighty = 0;
        gbcPnlBathroom.anchor = GridBagConstraints.NORTH;
        gbPnlBathroom.setConstraints( btnTempBathroom, gbcPnlBathroom );
        tabBathroom.add( btnTempBathroom );

        gbcTabBathroom.gridx = 0;
        gbcTabBathroom.gridy = 0;
        gbcTabBathroom.fill = GridBagConstraints.HORIZONTAL;
        gbcTabBathroom.weightx = 1;
        gbcTabBathroom.weighty = 0;
        gbcTabBathroom.anchor = GridBagConstraints.NORTH;
        gbTabBathroom.setConstraints( tabBathroom, gbcTabBathroom );
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
        propIsIn = model.getProperty(ns+"isIn");
        propHour = model.getProperty(ns+"hour");
        propMinute = model.getProperty(ns+"minute");

        desiredTempBedroom = model.getResource(ns + "BedroomTempRequest");
        desiredTempKitchen = model.getResource(ns + "KitchenTempRequest");
        desiredTempBathroom = model.getResource(ns + "BathroomTempRequest");
        desiredTempLivingroom = model.getResource(ns + "LivingroomTempRequest");

        currentClock = model.getResource(ns + "CurrentClock");
        wakeupClock = model.getResource(ns + "WakeupClock");

        System.out.println(desiredTempBedroom + " \"" + desiredTempBedroom.getProperty(propName).getString() + "\"  -  " + desiredTempBedroom.getProperty(propIntensity).getInt());
        sensorTemp = model.getResource(ns + "BedroomTempSensor");
        System.out.println(sensorTemp + " \"" + sensorTemp.getProperty(propName).getString() + "\"  -  " + sensorTemp.getProperty(propIntensity).getInt());
    }

    private void initModelInView() {
	    lblTempBathroom.setText(model.getResource(ns+"BathroomTempSensor").getProperty(propIntensity).getString() + "°C");
	    lblTempBathroom.update(lblTempBathroom.getGraphics());
        lblTempBedroom.setText(model.getResource(ns+"BedroomTempSensor").getProperty(propIntensity).getString() + "°C");
        lblTempBedroom.update(lblTempBedroom.getGraphics());
        lblTempKitchen.setText(model.getResource(ns+"KitchenTempSensor").getProperty(propIntensity).getString() + "°C");
        lblTempKitchen.update(lblTempKitchen.getGraphics());
        lblTempLivingroom.setText(model.getResource(ns+"LivingroomTempSensor").getProperty(propIntensity).getString() + "°C");
        lblTempLivingroom.update(lblTempLivingroom.getGraphics());
    }

	private void initEventListeners() {
        btnTempBedroom.addActionListener(e -> {
            updateTemperature(cmbDesiredTempBedroom, desiredTempBedroom);
        });

        btnTempBathroom.addActionListener(e -> {
            updateTemperature(cmbDesiredTempBathroom, desiredTempBathroom);
        });

        btnTempKitchen.addActionListener(e -> {
            updateTemperature(cmbDesiredTempKitchen, desiredTempKitchen);
        });

        btnTempLivingroom.addActionListener(e -> {
            updateTemperature(cmbDesiredTempLivingroom, desiredTempLivingroom);
        });

        spinHour.addChangeListener(e -> {
            checkWakeup(spinHour, currentClock, propHour);
        });

        spinMinute.addChangeListener(e -> {
            checkWakeup(spinMinute, currentClock, propMinute);
        });
    }

    private void checkWakeup(JSpinner time, Resource clock, Property prop) {
        clock.removeAll(prop);
        clock.addLiteral(prop, time.getValue());

        //Calcul
        clearRelevantActionsPanel();
        model = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
        refreshRelevantActionsPanel();
    }

	private void updateTemperature(JComboBox cmb, Resource desTemp) {
        System.out.println("Temperature désirée : " + cmb.getSelectedItem() +"°C");

        desTemp.removeAll(propIntensity);
        desTemp.addLiteral(propIntensity, cmb.getSelectedItem());

        //Calcul
        clearRelevantActionsPanel();
        model = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
        refreshRelevantActionsPanel();
        //System.out.println(JenaEngine.executeQueryFile(model, "data/query.txt"));

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
        txtareaRelevantAct.update(txtareaRelevantAct.getGraphics());
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
