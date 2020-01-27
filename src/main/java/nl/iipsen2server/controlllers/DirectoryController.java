package main.java.nl.iipsen2server.controlllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class DirectoryController {
	
	

/**
*
* @author Anthony Scheeres
*
*/
	  public String writeFileToDocuments(String folder, String fileName, String value){
	    	String url = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	    	////url);
	    	  String result = null;
	    	try {
				result = writeFile(url, folder, fileName, value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return result;
	    
	    }
	    
	  

/**
*
* @author Anthony Scheeres
*
*/
private String writeFile(String url, String folder, String fileName, String value) throws IOException {
	    	File file = new File(url +"/"+ folder);
	    	String path = null;
	    	if (!file.exists()) {
	    	    if (file.mkdir()) {
	    	        //"Directory created!");
	    	    } else {
	    	        //"Failed to create directory");
	    	    }
	    	}
	    	path = url +"/"+ folder+ "/" + fileName;
	    	//path);
	    	File files = new File(path);
	    	if(!files.exists()){
	    		files.createNewFile();
    	        FileWriter fw = new FileWriter(path);
    	        BufferedWriter bw = new BufferedWriter(fw);
    	        bw.write(value);
    	        bw.close();
	    		}else{
	    		  //"File already exists");
	    		}
	    		
	
    	 return path;  
	    	
	    }
}
