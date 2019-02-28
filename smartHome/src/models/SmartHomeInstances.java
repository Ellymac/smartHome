package models;

import com.hp.hpl.jena.rdf.model.Model;

import org.json.simple.JSONObject;
import tools.JenaEngine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

        JenaEngine.createInstanceOfClass(model, ns, "HeatingUp", "HeatingUpBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingDown", "HeatingDownBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingUp", "HeatingUpLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingDown", "HeatingDownLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingUp", "HeatingUpKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingDown", "HeatingDownKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingUp", "HeatingUpBathroom");
        JenaEngine.createInstanceOfClass(model, ns, "HeatingDown", "HeatingDownBathroom");

        // UserRequests creation
		JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "BedroomTempRequest");
        JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "BathroomTempRequest");
        JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "LivingroomTempRequest");
        JenaEngine.createInstanceOfClass(model, ns, "TemperatureRequest", "KitchenTempRequest");

        // Clocks
        JenaEngine.createInstanceOfClass(model, ns, "Clock", "WakeupClock");
        JenaEngine.createInstanceOfClass(model, ns, "Clock", "CurrentClock");

        // Shutters
        JenaEngine.createInstanceOfClass(model, ns, "Shutter", "BedroomShutter");

        // Light
        JenaEngine.createInstanceOfClass(model, ns, "Light", "BedroomLight");
    }
	
	private void addDataTypeProperties(Model model, String ns) {
		// Update heating properties for bedroom
		JSONObject currentData = SensorData.getDataFromSensor();

		//temperature
		int temperature = (int) ((Double.parseDouble(currentData.get("temperature")+"")-32)*5/9);

		//precipProbability
		double precipProbability = Double.parseDouble(currentData.get("precipProbability")+"");

		//date
		long time = (long) currentData.get("time");
		Date date = new Date(time*1000L);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		String formattedDate = simpleDateFormat.format(date);

		// Update heating properties
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomHeating", "name", "BedroomHeating");

		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "name", "BedroomTempRequest");
		JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "intensity", 19);

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "name", "BedroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "intensity", temperature);

        // Update heating properties for bathroom
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomHeating", "name", "BathroomHeating");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "name", "BathroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "intensity", 19);

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "name", "BathroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "intensity", temperature);

        // Update heating properties for kitchen
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenHeating", "name", "KitchenHeating");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "name", "KitchenTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "intensity", 19);

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "name", "KitchenTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "intensity", temperature);

        // Update heating properties for Livingroom
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomHeating", "name", "LivingroomHeating");

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "name", "LivingroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "intensity", 19);

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "name", "LivingroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "intensity", temperature);

        // Clocks
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "WakeupClock", "name", "WakeupClock");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "WakeupClock", "hour", 7);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "WakeupClock", "minute", 0);

        JenaEngine.updateValueOfDataTypeProperty(model, ns, "CurrentClock", "name", "CurrentClock");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "CurrentClock", "hour", 19);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "CurrentClock", "minute", 7);
    }
	
	private void addObjectProperties(Model model, String ns) {
		// 
		JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingUpBedroom");
		JenaEngine.addValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingDownBedroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomHeating", "hasAction", "HeatingUpBathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "BathroomHeating", "hasAction", "HeatingDownBathroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomHeating", "hasAction", "HeatingUpLivingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "LivingroomHeating", "hasAction", "HeatingDownLivingroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenHeating", "hasAction", "HeatingUpKitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "KitchenHeating", "hasAction", "HeatingDownKitchen");

        JenaEngine.updateValueOfObjectProperty(model, ns, "WakeupClock", "hasAction", "Ring");

        JenaEngine.updateValueOfObjectProperty(model, ns, "Bedroom", "contains", "BedroomHeating");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomHeating", "isIn", "Bedroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bedroom", "contains", "BedroomTempRequest");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomTempRequest", "isIn", "Bedroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bedroom", "contains", "BedroomTempSensor");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomTempSensor", "isIn", "Bedroom");

        JenaEngine.updateValueOfObjectProperty(model, ns, "Bathroom", "contains", "BathroomHeating");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomHeating", "isIn", "Bathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bathroom", "contains", "BathroomTempRequest");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomTempRequest", "isIn", "Bathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bathroom", "contains", "BathroomTempSensor");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomTempSensor", "isIn", "Bathroom");

        JenaEngine.updateValueOfObjectProperty(model, ns, "Kitchen", "contains", "KitchenHeating");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenHeating", "isIn", "Kitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "Kitchen", "contains", "KitchenTempRequest");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenTempRequest", "isIn", "Kitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "Kitchen", "contains", "KitchenTempSensor");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenTempSensor", "isIn", "Kitchen");

        JenaEngine.updateValueOfObjectProperty(model, ns, "Livingroom", "contains", "LivingroomHeating");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomHeating", "isIn", "Livingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "Livingroom", "contains", "LivingroomTempRequest");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomTempRequest", "isIn", "Livingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "Livingroom", "contains", "LivingroomTempSensor");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomTempSensor", "isIn", "Livingroom");

    }
}
