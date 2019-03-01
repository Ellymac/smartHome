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

        // Shutter actions
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenBathroom");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseBathroom");

        // Windows actions
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenWinBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseWinBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenWinLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseWinLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenWinKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseWinKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "Open", "OpenWinBathroom");
        JenaEngine.createInstanceOfClass(model, ns, "Close", "CloseWinBathroom");

        // Light actions
        JenaEngine.createInstanceOfClass(model, ns, "PowerOn", "PowerOnBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOff", "PowerOffBedroom");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOn", "PowerOnLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOff", "PowerOffLivingroom");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOn", "PowerOnKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOff", "PowerOffKitchen");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOn", "PowerOnBathroom");
        JenaEngine.createInstanceOfClass(model, ns, "PowerOff", "PowerOffBathroom");

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
        JenaEngine.createInstanceOfClass(model, ns, "Shutter", "BathroomShutter");
        JenaEngine.createInstanceOfClass(model, ns, "Shutter", "LivingroomShutter");
        JenaEngine.createInstanceOfClass(model, ns, "Shutter", "KitchenShutter");

        // Light
        JenaEngine.createInstanceOfClass(model, ns, "Light", "BedroomLight");
        JenaEngine.createInstanceOfClass(model, ns, "Light", "BathroomLight");
        JenaEngine.createInstanceOfClass(model, ns, "Light", "LivingroomLight");
        JenaEngine.createInstanceOfClass(model, ns, "Light", "KitchenLight");

        // Windows
        JenaEngine.createInstanceOfClass(model, ns, "Window", "BedroomWindow");
        JenaEngine.createInstanceOfClass(model, ns, "Window", "BathroomWindow");
        JenaEngine.createInstanceOfClass(model, ns, "Window", "LivingroomWindow");
        JenaEngine.createInstanceOfClass(model, ns, "Window", "KitchenWindow");

        // Weather
        JenaEngine.createInstanceOfClass(model, ns, "RainProbability", "RainProbability");
    }
	
	private void addDataTypeProperties(Model model, String ns) {
		// Update heating properties for bedroom
		JSONObject currentData = SensorData.getDataFromSensor();

		//temperature
		int temperature = (int) ((Double.parseDouble(currentData.get("temperature")+"")-32)*5/9 +10);

		//precipProbability
		double precipProbability = Double.parseDouble(currentData.get("precipProbability")+"");

		//date
		long time = (long) currentData.get("time");
		Date date = new Date(time*1000L);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		String formattedDate = simpleDateFormat.format(date);

		// Raining probability
        JenaEngine.addValueOfDataTypeProperty(model, ns, "RainProbability", "intensity", 80); //precipProbability

        // Update heating properties for bedroom
		JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomHeating", "name", "BedroomHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomHeating", "state", "off");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "name", "BedroomTempRequest");
		JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempRequest", "intensity", temperature);
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "name", "BedroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomTempSensor", "intensity", temperature);

        // Update heating properties for bathroom
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomHeating", "name", "BathroomHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomHeating", "state", "off");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "name", "BathroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempRequest", "intensity", temperature);
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "name", "BathroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomTempSensor", "intensity", temperature);

        // Update heating properties for kitchen
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenHeating", "name", "KitchenHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenHeating", "state", "off");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "name", "KitchenTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempRequest", "intensity", temperature);
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "name", "KitchenTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenTempSensor", "intensity", temperature);

        // Update heating properties for livingroom
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomHeating", "name", "LivingroomHeating");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomHeating", "state", "off");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "name", "LivingroomTempRequest");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempRequest", "intensity", temperature);
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "name", "LivingroomTempSensor");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomTempSensor", "intensity", temperature);

        // Clocks
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "WakeupClock", "name", "WakeupClock");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "WakeupClock", "hour", 30);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "WakeupClock", "minute", 0);
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "CurrentClock", "name", "CurrentClock");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "CurrentClock", "hour", 12);
        JenaEngine.addValueOfDataTypeProperty(model, ns, "CurrentClock", "minute", 0);

        // Shutter
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomShutter", "name", "BedroomShutter");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomShutter", "name", "BathroomShutter");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomShutter", "name", "LivingroomShutter");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenShutter", "name", "KitchenShutter");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomShutter", "state", "closed");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomShutter", "state", "closed");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomShutter", "state", "closed");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenShutter", "state", "closed");

        // Lights
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomLight", "name", "BedroomLight");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomLight", "name", "BathroomLight");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomLight", "name", "LivingroomLight");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenLight", "name", "KitchenLight");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomLight", "state", "off");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomLight", "state", "off");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomLight", "state", "off");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenLight", "state", "off");

        // Windows
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BedroomWindow", "name", "BedroomWindow");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "BathroomWindow", "name", "BathroomWindow");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "LivingroomWindow", "name", "LivingroomWindow");
        JenaEngine.updateValueOfDataTypeProperty(model, ns, "KitchenWindow", "name", "KitchenWindow");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BedroomWindow", "state", "closed");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "BathroomWindow", "state", "closed");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "LivingroomWindow", "state", "closed");
        JenaEngine.addValueOfDataTypeProperty(model, ns, "KitchenWindow", "state", "closed");


    }
	
	private void addObjectProperties(Model model, String ns) {
		// Heating
		JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingUpBedroom");
		JenaEngine.addValueOfObjectProperty(model, ns, "BedroomHeating", "hasAction", "HeatingDownBedroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomHeating", "hasAction", "HeatingUpBathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "BathroomHeating", "hasAction", "HeatingDownBathroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomHeating", "hasAction", "HeatingUpLivingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "LivingroomHeating", "hasAction", "HeatingDownLivingroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenHeating", "hasAction", "HeatingUpKitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "KitchenHeating", "hasAction", "HeatingDownKitchen");

        // Clock
        JenaEngine.updateValueOfObjectProperty(model, ns, "WakeupClock", "hasAction", "Ring");

        // Heating
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

        // Shutter
        JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomShutter", "hasAction", "OpenBedroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenShutter", "hasAction", "OpenKitchen");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomShutter", "hasAction", "OpenBathroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomShutter", "hasAction", "OpenLivingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "BedroomShutter", "hasAction", "CloseBedroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "KitchenShutter", "hasAction", "CloseKitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "BathroomShutter", "hasAction", "CloseBathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "LivingroomShutter", "hasAction", "CloseLivingroom");

        JenaEngine.addValueOfObjectProperty(model, ns, "Bedroom", "contains", "BedroomShutter");
        JenaEngine.addValueOfObjectProperty(model, ns, "Kitchen", "contains", "KitchenShutter");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bathroom", "contains", "BathroomShutter");
        JenaEngine.addValueOfObjectProperty(model, ns, "Livingroom", "contains", "LivingroomShutter");

        // Light
        JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomLight", "hasAction", "PowerOnBedroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenLight", "hasAction", "PowerOnKitchen");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomLight", "hasAction", "PowerOnBathroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomLight", "hasAction", "PowerOnLivingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "BedroomLight", "hasAction", "PowerOffBedroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "KitchenLight", "hasAction", "PowerOffKitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "BathroomLight", "hasAction", "PowerOffBathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "LivingroomLight", "hasAction", "PowerOffLivingroom");

        JenaEngine.addValueOfObjectProperty(model, ns, "Bedroom", "contains", "BedroomLight");
        JenaEngine.addValueOfObjectProperty(model, ns, "Kitchen", "contains", "KitchenLight");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bathroom", "contains", "BathroomLight");
        JenaEngine.addValueOfObjectProperty(model, ns, "Livingroom", "contains", "LivingroomLight");

        // Window
        JenaEngine.updateValueOfObjectProperty(model, ns, "BedroomWindow", "hasAction", "OpenWinBedroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "KitchenWindow", "hasAction", "OpenWinKitchen");
        JenaEngine.updateValueOfObjectProperty(model, ns, "BathroomWindow", "hasAction", "OpenWinBathroom");
        JenaEngine.updateValueOfObjectProperty(model, ns, "LivingroomWindow", "hasAction", "OpenWinLivingroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "BedroomWindow", "hasAction", "CloseWinBedroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "KitchenWindow", "hasAction", "CloseWinKitchen");
        JenaEngine.addValueOfObjectProperty(model, ns, "BathroomWindow", "hasAction", "CloseWinBathroom");
        JenaEngine.addValueOfObjectProperty(model, ns, "LivingroomWindow", "hasAction", "CloseWinLivingroom");

        JenaEngine.addValueOfObjectProperty(model, ns, "Bedroom", "contains", "BedroomWindow");
        JenaEngine.addValueOfObjectProperty(model, ns, "Kitchen", "contains", "KitchenWindow");
        JenaEngine.addValueOfObjectProperty(model, ns, "Bathroom", "contains", "BathroomWindow");
        JenaEngine.addValueOfObjectProperty(model, ns, "Livingroom", "contains", "LivingroomWindow");

    }
}
