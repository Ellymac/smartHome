package models;

import com.hp.hpl.jena.rdf.model.Model;

import tools.JenaEngine;

public class SmartHomeInstances {
	//JenaEngine.updateValueOfDataTypeProperty(inferedModel, ns, "Peter", "age", 25);
    // JenaEngine.updateValueOfObjectProperty(inferedModel, ns, "Peter", "estFilsDe", "Femme1");
    //JenaEngine.createInstanceOfClass(model, ns, "Femme", "abc");
	
	public void createAll(Model model, String ns) {
		createInstances(model, ns);
		addDataTypeProperties(model, ns);
		addObjectProperties(model, ns);
	}
	
	private void createInstances(Model model, String ns) {
		
		// Room creation
		JenaEngine.createInstanceOfClass(model, ns, "Bathroom", "Bathroom");
		JenaEngine.createInstanceOfClass(model, ns, "Bedroom", "Bedroom");
		JenaEngine.createInstanceOfClass(model, ns, "Kitchen", "Kitchen");
		JenaEngine.createInstanceOfClass(model, ns, "LivingRoom", "Livingroom");
		
		// Object creation
		JenaEngine.createInstanceOfClass(model, ns, "Heating", "BedroomHeating");
		
		// UserRequests creation
		JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "TemperatureRequest");

	}
	
	private void addDataTypeProperties(Model model, String ns) {
		// Update heating properties
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomHeating", "name", "BedroomHeating");
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomHeating", "intensity", 19);
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "Bedroom", "contains", "BedroomHeating");
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomHeating", "isIn", "Bedroom");

		
	}
	
	private void addObjectProperties(Model model, String ns) {
		// 
		JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingUp");
		JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingDown");

	}
}
