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
    private JPanel tabCurrentState;

    private JPanel pnlLightShutter;

    private ButtonGroup rbgPnlBedroom;
    private ButtonGroup rbgPnlLivingroom;
    private ButtonGroup rbgPnlBathroom;
    private ButtonGroup rbgPnlKitchen;

    private JTextArea textAreaCurrState;

    private JLabel lblTempRequested;
    private JLabel lblTempBedroom;
    private JLabel lblTempLivingroom;
    private JLabel lblTempBathroom;
    private JLabel lblTempKitchen;

    private JCheckBox cbWakeup;
    private JSpinner spinHourWakeup;
    private JSpinner spinMinuteWakeup;
    private JButton btnTempBedroom;
    private JButton btnClockBedroom;
    private JButton btnTempLivingroom;
    private JButton btnTempBathroom;
    private JButton btnTempKitchen;
    private JButton btnExecute;

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
	private Property propState;
	private Property propExecute;
	
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

		updateCurrentState();
		
	    this.setVisible(true);
	}

	private JPanel initTabView() {
        pnl0 = new JPanel();
        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        pnl0.setLayout( gbPanel0 );

        lblHour = new JLabel("Heure : ");
        SpinnerNumberModel spinModelHour = new SpinnerNumberModel(12, 1, 24, 1);
        spinHour = new JSpinner(spinModelHour);
        SpinnerNumberModel spinModelMinute = new SpinnerNumberModel(0, 0, 59, 1);
        spinMinute = new JSpinner(spinModelMinute);
        pnl0.add(lblHour);
        pnl0.add(spinHour);
        pnl0.add(spinMinute);

        tabpnlRooms = new JTabbedPane( );

        initTabBedroom();

        initTabLivingroom();

        initTabKitchen();

        initTabBathroom();

        initTabCurrentState();

        tabpnlRooms.addTab("Chambre",tabBedroom);
        tabpnlRooms.addTab("Salle de bains", tabBathroom);
        tabpnlRooms.addTab("Cuisine", tabKitchen);
        tabpnlRooms.addTab("Salon", tabLivingroom);
        tabpnlRooms.addTab("État courant", tabCurrentState);

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

        //rbgPnlBedroom = new ButtonGroup();
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

        pnlLightShutter = new JPanel();
        JCheckBox cbLightsBedroom = new JCheckBox("Lumière");
        cbLightsBedroom.addActionListener(e -> {
            if(cbLightsBedroom.isSelected()) {
                model.getResource(ns+"BedroomLight").removeAll(propState);
                model.getResource(ns+"BedroomLight").addLiteral(propState, "on");
            }
            else {
                model.getResource(ns+"BedroomLight").removeAll(propState);
                model.getResource(ns+"BedroomLight").addLiteral(propState, "off");
            }
        });
        JCheckBox cbShuttersBedroom = new JCheckBox("Volets");
        cbShuttersBedroom.addActionListener(e -> {
            if(cbShuttersBedroom.isSelected()) {
                model.getResource(ns+"BedroomShutter").removeAll(propState);
                model.getResource(ns+"BedroomShutter").addLiteral(propState, "open");
            }
            else {
                model.getResource(ns+"BedroomShutter").removeAll(propState);
                model.getResource(ns+"BedroomShutter").addLiteral(propState, "closed");
            }
        });
        pnlLightShutter.add(cbShuttersBedroom);
        pnlLightShutter.add(cbLightsBedroom);
        gbcPnlBedroom.gridx = 0;
        gbcPnlBedroom.gridy = 2;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints(pnlLightShutter, gbcPnlBedroom );
        tabBedroom.add(pnlLightShutter);

        cbWakeup = new JCheckBox( "Réveil"  );
        cbWakeup.setSelected( false );
        gbcPnlBedroom.gridx = 0;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints(cbWakeup, gbcPnlBedroom );
        tabBedroom.add(cbWakeup);

        spinHourWakeup = new JSpinner( );
        spinHourWakeup.setVisible(false);
        gbcPnlBedroom.gridx = 1;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( spinHourWakeup, gbcPnlBedroom );
        tabBedroom.add( spinHourWakeup );

        lblPoints = new JLabel(":" );
        lblPoints.setVisible(false);
        gbcPnlBedroom.gridx = 2;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( lblPoints, gbcPnlBedroom );
        tabBedroom.add( lblPoints );

        spinMinuteWakeup = new JSpinner( );
        spinMinuteWakeup.setVisible(false);
        gbcPnlBedroom.gridx = 3;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( spinMinuteWakeup, gbcPnlBedroom );
        tabBedroom.add( spinMinuteWakeup );

        btnClockBedroom = new JButton( "✓"  );
        btnClockBedroom.setVisible(false);
        gbcPnlBedroom.gridx = 4;
        gbcPnlBedroom.gridy = 1;
        gbcPnlBedroom.weightx = 1;
        gbcPnlBedroom.weighty = 0;
        gbcPnlBedroom.anchor = GridBagConstraints.NORTH;
        gbPnlBedroom.setConstraints( btnClockBedroom, gbcPnlBedroom );
        tabBedroom.add( btnClockBedroom );

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

        pnlLightShutter = new JPanel();
        JCheckBox cbLightsBedroom = new JCheckBox("Lumière");
        cbLightsBedroom.addActionListener(e -> {
            if(cbLightsBedroom.isSelected()) {
                model.getResource(ns+"LivingroomLight").removeAll(propState);
                model.getResource(ns+"LivingroomLight").addLiteral(propState, "on");
            }
            else {
                model.getResource(ns+"LivingroomLight").removeAll(propState);
                model.getResource(ns+"LivingroomLight").addLiteral(propState, "off");
            }
        });
        JCheckBox cbShuttersBedroom = new JCheckBox("Volets");
        cbShuttersBedroom.addActionListener(e -> {
            if(cbShuttersBedroom.isSelected()) {
                model.getResource(ns+"LivingroomShutter").removeAll(propState);
                model.getResource(ns+"LivingroomShutter").addLiteral(propState, "open");
            }
            else {
                model.getResource(ns+"LivingroomShutter").removeAll(propState);
                model.getResource(ns+"LivingroomShutter").addLiteral(propState, "closed");
            }
        });
        pnlLightShutter.add(cbShuttersBedroom);
        pnlLightShutter.add(cbLightsBedroom);
        gbcPnlLivingroom.gridx = 0;
        gbcPnlLivingroom.gridy = 2;
        gbcPnlLivingroom.weightx = 1;
        gbcPnlLivingroom.weighty = 0;
        gbcPnlLivingroom.anchor = GridBagConstraints.NORTH;
        gbPnlLivingroom.setConstraints(pnlLightShutter, gbcPnlLivingroom );
        tabLivingroom.add(pnlLightShutter);

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

        pnlLightShutter = new JPanel();
        JCheckBox cbLightsBedroom = new JCheckBox("Lumière");
        cbLightsBedroom.addActionListener(e -> {
            if(cbLightsBedroom.isSelected()) {
                model.getResource(ns+"KitchenLight").removeAll(propState);
                model.getResource(ns+"KitchenLight").addLiteral(propState, "on");
            }
            else {
                model.getResource(ns+"KitchenLight").removeAll(propState);
                model.getResource(ns+"KitchenLight").addLiteral(propState, "off");
            }
        });
        JCheckBox cbShuttersBedroom = new JCheckBox("Volets");
        cbShuttersBedroom.addActionListener(e -> {
            if(cbShuttersBedroom.isSelected()) {
                model.getResource(ns+"KitchenShutter").removeAll(propState);
                model.getResource(ns+"KitchenShutter").addLiteral(propState, "open");
            }
            else {
                model.getResource(ns+"KitchenShutter").removeAll(propState);
                model.getResource(ns+"KitchenShutter").addLiteral(propState, "closed");
            }
        });
        pnlLightShutter.add(cbShuttersBedroom);
        pnlLightShutter.add(cbLightsBedroom);
        gbcPnlKitchen.gridx = 0;
        gbcPnlKitchen.gridy = 2;
        gbcPnlKitchen.weightx = 1;
        gbcPnlKitchen.weighty = 0;
        gbcPnlKitchen.anchor = GridBagConstraints.NORTH;
        gbPnlKitchen.setConstraints(pnlLightShutter, gbcPnlKitchen );
        tabKitchen.add(pnlLightShutter);

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

        pnlLightShutter = new JPanel();
        JCheckBox cbLightsBedroom = new JCheckBox("Lumière");
        cbLightsBedroom.addActionListener(e -> {
            if(cbLightsBedroom.isSelected()) {
                model.getResource(ns+"BathroomLight").removeAll(propState);
                model.getResource(ns+"BathroomLight").addLiteral(propState, "on");
            }
            else {
                model.getResource(ns+"BathroomLight").removeAll(propState);
                model.getResource(ns+"BathroomLight").addLiteral(propState, "off");
            }
        });
        JCheckBox cbShuttersBedroom = new JCheckBox("Volets");
        cbShuttersBedroom.addActionListener(e -> {
            if(cbShuttersBedroom.isSelected()) {
                model.getResource(ns+"BathroomShutter").removeAll(propState);
                model.getResource(ns+"BathroomShutter").addLiteral(propState, "open");
            }
            else {
                model.getResource(ns+"BathroomShutter").removeAll(propState);
                model.getResource(ns+"BathroomShutter").addLiteral(propState, "closed");
            }
        });
        pnlLightShutter.add(cbShuttersBedroom);
        pnlLightShutter.add(cbLightsBedroom);
        gbcPnlBathroom.gridx = 0;
        gbcPnlBathroom.gridy = 2;
        gbcPnlBathroom.weightx = 1;
        gbcPnlBathroom.weighty = 0;
        gbcPnlBathroom.anchor = GridBagConstraints.NORTH;
        gbPnlBathroom.setConstraints(pnlLightShutter, gbcPnlBathroom );
        tabBathroom.add(pnlLightShutter);

        gbcTabBathroom.gridx = 0;
        gbcTabBathroom.gridy = 0;
        gbcTabBathroom.fill = GridBagConstraints.HORIZONTAL;
        gbcTabBathroom.weightx = 1;
        gbcTabBathroom.weighty = 0;
        gbcTabBathroom.anchor = GridBagConstraints.NORTH;
        gbTabBathroom.setConstraints( tabBathroom, gbcTabBathroom );
    }

    private void initTabCurrentState() {
        tabCurrentState = new JPanel();
        GridBagLayout gbTabCurrentState = new GridBagLayout();
        GridBagConstraints gbcTabCurrentState = new GridBagConstraints();
        tabCurrentState.setLayout( gbTabCurrentState );

        GridBagLayout gbPnlCurrentState = new GridBagLayout();
        GridBagConstraints gbcPnlCurrentState = new GridBagConstraints();
        tabCurrentState.setLayout( gbPnlCurrentState );

        JPanel p = new JPanel();
        textAreaCurrState = new JTextArea();
        textAreaCurrState.setEditable(false);
        textAreaCurrState.setRows(10);
        p.add(new JScrollPane(textAreaCurrState));
        gbcPnlCurrentState.gridx = 0;
        gbcPnlCurrentState.gridy = 0;
        gbcPnlCurrentState.weightx = 1;
        gbcPnlCurrentState.weighty = 1;
        gbcPnlCurrentState.fill = GridBagConstraints.BOTH;
        gbcPnlCurrentState.anchor = GridBagConstraints.NORTH;
        gbPnlCurrentState.setConstraints( p, gbcPnlCurrentState );
        tabCurrentState.add( p );

        gbcTabCurrentState.gridx = 0;
        gbcTabCurrentState.gridy = 0;
        gbcTabCurrentState.fill = GridBagConstraints.BOTH;
        gbcTabCurrentState.weightx = 1;
        gbcTabCurrentState.weighty = 0;
        gbcTabCurrentState.anchor = GridBagConstraints.NORTH;
        gbTabCurrentState.setConstraints( tabCurrentState, gbcTabCurrentState );
    }

	private JPanel initRelevantActionsPanel() {
		pnlRelActions = new JPanel(new BorderLayout());

		txtareaRelevantAct = new JTextArea("Relevant actions :\n");
        txtareaRelevantAct.setEditable(false);
        txtareaRelevantAct.setRows(10);

		pnlRelActions.add(txtareaRelevantAct, BorderLayout.CENTER);

		btnExecute = new JButton("Tout exécuter");
		pnlRelActions.add(btnExecute, BorderLayout.EAST);

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
        propHour = model.getProperty(ns+"hour");
        propMinute = model.getProperty(ns+"minute");
        propState = model.getProperty(ns+"state");
        propExecute = model.getProperty(ns+"execute");

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

        cbWakeup.addActionListener(e -> {
            if(cbWakeup.isSelected()) {
                lblPoints.setVisible(true);
                spinHourWakeup.setVisible(true);
                spinMinuteWakeup.setVisible(true);
                btnClockBedroom.setVisible(true);
            }
            else {
                lblPoints.setVisible(false);
                spinHourWakeup.setVisible(false);
                spinMinuteWakeup.setVisible(false);
                btnClockBedroom.setVisible(false);
                removeClock(wakeupClock);
            }
        });

        btnClockBedroom.addActionListener(e -> {
            updateClock(spinHourWakeup, spinMinuteWakeup, wakeupClock);
        });

        btnExecute.addActionListener(e -> {
            List<QuerySolution> results = new ArrayList<QuerySolution>();
            getRelevantActions().forEachRemaining(results::add);
            Resource resource;
            for(QuerySolution qs : results) {
                resource = qs.getResource("act");
                if(resource != null) {
                    if (resource.getProperty(propType).asTriple().getObject().getLocalName().equals("RelevantAction")) {
                        System.out.println("exec : " + resource.getProperty(propType).asTriple().getObject().getLocalName()+"\n");
                        resource.removeAll(propExecute);
                        resource.addLiteral(propExecute, true);
                    }
                }
            }
            clearRelevantActionsPanel();
            updateRelevantActionsPanel();
        });

        tabpnlRooms.addChangeListener(e -> {
            if(tabpnlRooms.getSelectedIndex() == 4)
                updateCurrentState();
        });
    }

    private void checkWakeup(JSpinner time, Resource clock, Property prop) {
        clock.removeAll(prop);
        clock.addLiteral(prop, time.getValue());

        //Calcul
        updateRelevantActionsPanel();
    }

	private void updateTemperature(JComboBox cmb, Resource desTemp) {
        System.out.println("Temperature désirée : " + cmb.getSelectedItem() +"°C");

        desTemp.removeAll(propIntensity);
        desTemp.addLiteral(propIntensity, cmb.getSelectedItem());

        //Calcul
        updateRelevantActionsPanel();
    }

    private void removeClock(Resource wuClock) {
        System.out.println("Suppression du réveil.");

        wuClock.removeAll(propHour);
        wuClock.removeAll(propMinute);
        wuClock.addLiteral(propHour, 30);
        wuClock.addLiteral(propMinute, 0);

        //Calcul
        updateRelevantActionsPanel();
    }

    private void updateClock(JSpinner h, JSpinner m, Resource wuClock) {
        System.out.println("Nouveau réveil : " + h.getValue() + ":" + m.getValue());

        wuClock.removeAll(propHour);
        wuClock.removeAll(propMinute);
        wuClock.addLiteral(propHour, h.getValue());
        wuClock.addLiteral(propMinute, m.getValue());

        //Calcul
        updateRelevantActionsPanel();
    }

    private void updateRelevantActionsPanel() {
        clearRelevantActionsPanel();
        model = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
        refreshRelevantActionsPanel();
        System.out.println(JenaEngine.executeQueryFile(model, "data/query.txt"));
    }

	private void clearRelevantActionsPanel() {
        List<QuerySolution> results = new ArrayList<QuerySolution>();
        getRelevantActions().forEachRemaining(results::add);
        Resource resource;
        for(QuerySolution qs : results) {
            resource = qs.getResource("act");
            if(resource != null) {
                //System.out.println(resource.getProperty(propType).asTriple().getObject().getLocalName());
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

	private void updateCurrentState() {
	    StringBuffer states = new StringBuffer();
	    String minWakeup = model.getResource(ns+"WakeupClock").getProperty(propMinute).getString();
	    String hourWakeup = model.getResource(ns+"WakeupClock").getProperty(propHour).getString();
        String wakeup;
	    if(hourWakeup.equals("30"))
	        wakeup = "Aucun réveil";
	    else
	        wakeup = hourWakeup + ":" + minWakeup;
	    states.append("Horloges :\n");
	    states.append("CurrentClock -> " + model.getResource(ns+"CurrentClock").getProperty(propHour).getString() + ":" + model.getResource(ns+"CurrentClock").getProperty(propMinute).getString() +"\n");
        states.append("WakeupClock -> " + wakeup +"\n");
        states.append("\nTempératures :\n");
        states.append("Chambre -> courante : " + model.getResource(ns+"BedroomTempSensor").getProperty(propIntensity).getString()+ "°C.  désirée : "+
                        model.getResource(ns+"BedroomTempRequest").getProperty(propIntensity).getString()+ "°C.\n");
        states.append("Salle de bains -> courante : " + model.getResource(ns+"BathroomTempSensor").getProperty(propIntensity).getString()+ "°C.  désirée : "+
                model.getResource(ns+"BathroomTempRequest").getProperty(propIntensity).getString()+ "°C.\n");
        states.append("Salon -> courante : " + model.getResource(ns+"LivingroomTempSensor").getProperty(propIntensity).getString()+ "°C.  désirée : "+
                model.getResource(ns+"LivingroomTempRequest").getProperty(propIntensity).getString()+ "°C.\n");
        states.append("Cuisine -> courante : " + model.getResource(ns+"KitchenTempSensor").getProperty(propIntensity).getString()+ "°C.  désirée : "+
                model.getResource(ns+"KitchenTempRequest").getProperty(propIntensity).getString()+ "°C.\n");
        states.append("\nChauffages :\n");
        states.append(model.getResource(ns+"BedroomHeating").getProperty(propName).getString() + " -> " + model.getResource(ns+"BedroomHeating").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"BathroomHeating").getProperty(propName).getString() + " -> " + model.getResource(ns+"BathroomHeating").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"LivingroomHeating").getProperty(propName).getString() + " -> " + model.getResource(ns+"LivingroomHeating").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"KitchenHeating").getProperty(propName).getString() + " -> " + model.getResource(ns+"KitchenHeating").getProperty(propState).getString()+"\n");
        states.append("\nLumières :\n");
        states.append(model.getResource(ns+"BedroomLight").getProperty(propName).getString() + " -> " + model.getProperty(ns+"BedroomLight").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"BathroomLight").getProperty(propName).getString() + " -> " + model.getProperty(ns+"BathroomLight").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"LivingroomLight").getProperty(propName).getString() + " -> " + model.getProperty(ns+"LivingroomLight").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"KitchenLight").getProperty(propName).getString() + " -> " + model.getProperty(ns+"KitchenLight").getProperty(propState).getString()+"\n");
        states.append("\nVolets :\n");
        states.append(model.getResource(ns+"BedroomShutter").getProperty(propName).getString() + " -> " + model.getProperty(ns+"BedroomShutter").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"BathroomShutter").getProperty(propName).getString() + " -> " + model.getProperty(ns+"BathroomShutter").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"LivingroomShutter").getProperty(propName).getString() + " -> " + model.getProperty(ns+"LivingroomShutter").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"KitchenShutter").getProperty(propName).getString() + " -> " + model.getProperty(ns+"KitchenShutter").getProperty(propState).getString()+"\n");
        states.append("\nFenêtres :\n");
        states.append(model.getResource(ns+"BedroomWindow").getProperty(propName).getString() + " -> " + model.getProperty(ns+"BedroomWindow").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"BathroomWindow").getProperty(propName).getString() + " -> " + model.getProperty(ns+"BathroomWindow").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"LivingroomWindow").getProperty(propName).getString() + " -> " + model.getProperty(ns+"LivingroomWindow").getProperty(propState).getString()+"\n");
        states.append(model.getResource(ns+"KitchenWindow").getProperty(propName).getString() + " -> " + model.getProperty(ns+"KitchenWindow").getProperty(propState).getString()+"\n");

        textAreaCurrState.setText(states.toString());
        textAreaCurrState.update(textAreaCurrState.getGraphics());
    }
	
	public static void main(String args[]){
       new Gui();
    }
}
