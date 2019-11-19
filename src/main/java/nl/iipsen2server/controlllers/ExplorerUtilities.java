package main.java.nl.iipsen2server.controlllers;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

class ExplorerUtilities {
	/**
	 * @author Anthony Scheeres
	 */
	//Ik heb een nieuwe class gemaakt die handige dingen met explorer kan doen je kan explorer openen een bestand kiezen en dit url word terug gegeven door de methode
	public static String getUrlFromExplorer() {
		FileDialog fileDialog = new FileDialog(new JFrame());
		fileDialog.setVisible(true);
		File[] f = fileDialog.getFiles();
		if(f.length > 0){
		 
		    return fileDialog.getFiles()[0].getAbsolutePath();
		}
		return null;
	}
	

	
	/**
	 * @author Anthony Scheeres
	 */
	//select folder from explorer
	public static File selectFolder() {
		  JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select a destination folder");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

		      return chooser.getSelectedFile();
		    } return null;
		  }
}
