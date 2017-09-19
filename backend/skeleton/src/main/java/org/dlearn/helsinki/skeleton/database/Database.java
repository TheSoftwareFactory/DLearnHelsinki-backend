package org.dlearn.helsinki.skeleton.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dlearn.helsinki.skeleton.model.SpiderGraph;

public class Database {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/Dlearn_db?verifyServerCertificate=false&useSSL=true";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    public void testConnection() throws Exception {
        try(Connection dbConnection = getDBConnection()) {
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public SpiderGraph getSpiderGraph(int student_id, int spidergraph_id) throws SQLException {
        SpiderGraph spidergraph = new SpiderGraph();
        
        try(Connection dbConnection = getDBConnection()) {
            // Set up batch of statements
            String statement = "SELECT * FROM public.\"SpiderGraphs\" WHERE _id = ? AND student_id = ?";
            try(PreparedStatement select = dbConnection.prepareStatement(statement)) {
                select.setInt(1, spidergraph_id);
                select.setInt(2, student_id);
                // execute query
                try(ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        spidergraph.set_id(spidergraph_id);
                        spidergraph.setStudent_id(student_id);
                        spidergraph.setValue1(result.getInt(1));
                        spidergraph.setValue2(result.getInt(2));
                        spidergraph.setValue3(result.getInt(3));
                        spidergraph.setValue4(result.getInt(4));
                        spidergraph.setValue5(result.getInt(5));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return spidergraph;
    }

    public SpiderGraph updateSpidergraph(SpiderGraph spidergraph) throws SQLException {
        SpiderGraph old_spidergraph = getSpiderGraph(spidergraph.getStudent_id(), spidergraph.get_id());
        
        System.out.println(old_spidergraph.getValue1());
        old_spidergraph.setValue1((old_spidergraph.getValue1() + 1) % 5);

        String updateSpidergraphFromId = "UPDATE public.\"SpiderGraphs\" SET value1 = ? WHERE _id = ? AND student_id = ?";

        try (Connection dbConnection = getDBConnection()) {
            try (PreparedStatement ps_update = dbConnection.prepareStatement(updateSpidergraphFromId)) {
                ps_update.setInt(1, old_spidergraph.getValue1());
                ps_update.setInt(2, old_spidergraph.get_id());
                ps_update.setInt(3, old_spidergraph.getStudent_id());
                //ps_update.setInt(1, spidergraph.getValue1());
                //ps_update.setInt(2, spidergraph.getValue2());
                //ps_update.setInt(3, spidergraph.getValue3());
                //ps_update.setInt(4, spidergraph.getValue4());
                //ps_update.setInt(5, spidergraph.getValue5()); 
                //ps_update.setInt(6, spidergraph.get_id());
                //ps_update.setInt(7, spidergraph.getStudent_id());
                // execute update SQL statement
                ps_update.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getSpiderGraph(spidergraph.getStudent_id(), spidergraph.get_id());
    }

/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;

    }

    @SuppressWarnings("unused")
    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

}
