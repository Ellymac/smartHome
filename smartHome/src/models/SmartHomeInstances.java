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
        JenaEngine.createInstanceOfClass(model, ns, "Temperature", "BedroomTempSensor");
        JenaEngine.createInstanceOfClass(model, ns, "Heating", "BathroomHeating");
        JenaEngine.createInstanceOfClass(model, ns, "Temperature", "BathroomTempSensor");
        JenaEngine.createInstanceOfClass(model, ns, "Heating", "LivingroomHeating");
        JenaEngine.createInstanceOfClass(model, ns, "Temperature", "LivingroomTempSensor");
        JenaEngine.createInstanceOfClass(model, ns, "Heating", "KitchenHeating");
        JenaEngine.createInstanceOfClass(model, ns, "Temperature", "KitchenTempSensor");

        JenaEngine.createInstanceOfClass(model, ns, "HeatingUp", "HeatingUp");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingDown", "HeatingDown");


        // UserRequests creation
		JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "BedroomTempRequest");
        JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "BathroomTempRequest");
        JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "LivingroomTempRequest");
        JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "KitchenTempRequest");


        // Relevant action
        JenaEngine.createInstanceOfClass(model, ns, "RelevantAction", "RelevantAction");
        JenaEngine.createInstanceOfClass(model, ns, "Action", "Action");


    }
	
	private void addDataTypeProperties(Model model, String ns) {
		// Update heating properties for bedroom
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomHeating", "name", "BedroomHeating");
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "Bedroom", "contains", "BedroomHeating");
		JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomHeating", "isIn", "Bedroom");

		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "name", "BedroomTempRequest");
		JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Bedroom", "contains", "BedroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "isIn", "Bedroom");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "name", "BedroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Bedroom", "contains", "BedroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "isIn", "Bedroom");

        // Update heating properties for bathroom
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomHeating", "name", "BathroomHeating");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "Bathroom", "contains", "BathroomHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomHeating", "isIn", "Bathroom");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "name", "BathroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Bathroom", "contains", "BathroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "isIn", "Bathroom");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "name", "BathroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Bathroom", "contains", "BathroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "isIn", "Bathroom");

        // Update heating properties for kitchen
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenHeating", "name", "KitchenHeating");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "Kitchen", "contains", "KitchenHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenHeating", "isIn", "Kitchen");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "name", "KitchenTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Kitchen", "contains", "KitchenTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "isIn", "Kitchen");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "name", "KitchenTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Kitchen", "contains", "KitchenTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "isIn", "Kitchen");

        // Update heating properties for Livingroom
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomHeating", "name", "LivingroomHeating");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "Livingroom", "contains", "LivingroomHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomHeating", "isIn", "Livingroom");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "name", "LivingroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Livingroom", "contains", "LivingroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "isIn", "Livingroom");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "name", "LivingroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "intensity", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "Livingroom", "contains", "LivingroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "isIn", "Livingroom");

    }
	
	private void addObjectProperties(Model model, String ns) {
		// 
		JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingUp");
		JenaEngine.addValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingDown");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomHeating", "hasAction", "HeatingUp");
        JenaEngine.addValueOfObjectProperty(model, ns, "BathroomHeating", "hasAction", "HeatingDown");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomHeating", "hasAction", "HeatingUp");
        JenaEngine.addValueOfObjectProperty(model, ns, "LivingroomHeating", "hasAction", "HeatingDown");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenHeating", "hasAction", "HeatingUp");
        JenaEngine.addValueOfObjectProperty(model, ns, "KitchenHeating", "hasAction", "HeatingDown");

	}
}
