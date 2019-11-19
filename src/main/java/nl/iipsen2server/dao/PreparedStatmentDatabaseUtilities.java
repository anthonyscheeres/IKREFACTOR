package main.java.nl.iipsen2server.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import main.java.nl.iipsen2server.models.DatabaseModel;

public class PreparedStatmentDatabaseUtilities {

    /**
     *
     * @author Anthony Scheeres
     * @return  
     *
     */
    //use a database object to connect to database and perform a query
    public String connectDatabaseJson(
    		DatabaseModel databaseModel, 
    		String query, 
    		List < String > values,
            boolean isUpdate
    		) throws Exception {

        return connectToDatabaseJson(
        		databaseModel.getUsername(),
        		databaseModel.getPassword(),
        		databaseModel.getPortNumber(),
        		databaseModel.getDatabaseName(),
        		databaseModel.getHostName(),
        		query,
        		values,
                isUpdate
        		);
    }


    /**
     *
     * @author Anthony Scheeres
     * @return 
     *
     */
    //use a database object to connect to database and perform a query
    public HashMap < String, List < String >> connectDatabaseHashmap(
    		DatabaseModel databaseModel, 
    		String query, 
    		List < String > values) 
    				throws Exception {

        return connectToDatabaseHashmap(
        		databaseModel.getUsername(),
        		databaseModel.getPassword(),
        		databaseModel.getPortNumber(),
        		databaseModel.getDatabaseName(),
        		databaseModel.getHostName(),
        		query,
        		values
        		);
    }





    /**
     *
     * @author Anthony Scheeres
     * @return 
     * @throws Exception 
     *
     */
    private String connectToDatabaseJson(
            String username,
            String password, int portNumber,
            String databaseName,
            String hostName,
            String query,
            List<String> values,
            boolean isUpdate
    ) throws Exception {
    	
        DatabaseUtilities dataUtilities = new DatabaseUtilities();
        String url = dataUtilities.createUrl(portNumber, databaseName, hostName);
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        //     Class.forName("org.postgresql.Driver"); 


        try  {
            System.out.println("Java JDBC PostgreSQL: " + databaseName);
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int counter = 0;
            for (int index = 0; index < values.size(); index++) {
                counter = index + 1;
                System.out.println(values.get(index));

                if (isNumeric(values.get(index))) {
                    pstmt.setInt(counter, Integer.parseInt(values.get(index)));
                } else {

                    pstmt.setString(counter, values.get(index));
                }
            }
            //System.out.println(pstmt);

            if(isUpdate){
                pstmt.executeUpdate();
                return "Update was succecfull";
            }else{
                ResultSet r = pstmt.executeQuery();
                JsonConverterUtilities jsonConverter = new JsonConverterUtilities();
                String f = jsonConverter.convertToJSON(r).toString();
                connection.close();
                return f;
            }

        } catch (SQLException err) {
            System.out.println("Connection failure.");
            err.printStackTrace();
        }

        return null;




    }




    /**
     *
     * @author Anthony Scheeres
     * @return 
     * @throws Exception 
     *
     */
    private HashMap < String, List < String >> connectToDatabaseHashmap(
            String username,
            String password, int portNumber,
            String databaseName,
            String hostName,
            String query,
            List<String> values
    ) throws Exception {
    	
        DatabaseUtilities dUtilities = new DatabaseUtilities();
        String url = dUtilities.createUrl(portNumber, databaseName, hostName);
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        //     Class.forName("org.postgresql.Driver"); 
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Java JDBC PostgreSQL: " + databaseName);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int counter = 0;
            for (int index = 0; index < values.size(); index++) {
                counter = index + 1;
                System.out.println(values.get(index));
                if (isNumeric(values.get(index))) {
                    pstmt.setInt(counter, Integer.parseInt(values.get(index)));
                } else {
                    pstmt.setString(counter, values.get(index));
                }
            }
            System.out.println(pstmt);
            ResultSet r = pstmt.executeQuery();
            DatabaseUtilities g = new DatabaseUtilities();
            HashMap < String, List < String >> hashmap = g.getTableContents2(r);
            connection.close();
            return hashmap;
        } catch (SQLException err) {
            System.out.println("Connection failure.");
            err.printStackTrace();
        }

        return null;




    }


    /**
     *
     * @author Anthony Scheeres
     * @return 
     * @throws Exception 
     *
     */
    private static boolean isNumeric(String strNum) {
        try {
            double integer = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }


}