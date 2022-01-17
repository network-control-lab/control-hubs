package main;
import control_package.control_shcemes;
import control_package.node_classification;
import control_package.find_sensitive_control_hub;

import java.io.IOException;



public class demo{
	
	public static void main(String args[]) throws IOException {
		
		/*
		 * note: 
		 * This function is the main function to calculate the 
		 * node classification and the different control schemes.
		 * For the two functions we provide, "node_classification" 
		 * ,"find_sensitive_control_hub" and "control_shcemes", you
		 * need to set the network file in the parameter "inputFile"
		 * and it will automatically save the results to "./result" 
		 * under the project. 
		 * 
		 * Please refer to "Sample_network" in the "./net" folder under
		 * the project for the format of the input network.
		 * 
		 * 
		 * 
		 */
		
		String inputFile = "./net/blca.net";
		
		
		new node_classification(inputFile);
		
		new find_sensitive_control_hub(inputFile);
		
		new control_shcemes(inputFile);
		
	}
	
}