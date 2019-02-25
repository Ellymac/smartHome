/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package application;

import com.hp.hpl.jena.rdf.model.Model;

import models.SmartHomeInstances;
import tools.JenaEngine;

/**
 *
 * @author DO.ITSUDPARIS
 */
public class Main {
    public static String ns = "";
    public static String inputDataOntology = "data/smartHome.owl";
    public static String inputRule = "data/owlrules.txt";
    public static String inputQuery = "data/query.txt";

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args){
        Model model = JenaEngine.readModel(inputDataOntology);
        
        if (model != null)
			ns = model.getNsPrefixURI("");
		
        (new SmartHomeInstances()).createAll(model,ns);
        
        model = JenaEngine.readInferencedModelFromRuleFile(model, inputRule);
        model = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

        //query on the model
        System.out.println(JenaEngine.executeQueryFile(model, inputQuery));
    }

}
