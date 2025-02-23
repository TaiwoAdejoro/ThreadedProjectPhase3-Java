package org.example.travelexpertsphase3desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SuppliersTableController {

    public ObservableList<String> fetchData() throws SQLException {
        Connection conn = DBconnection.getConnection();

        ObservableList<String> data = FXCollections.observableArrayList();
        String query = "select * from suppliers";

        try (Statement state = conn.createStatement();
        ResultSet resultSet = state.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("There was an error fetching the suppliers list");
        }
        return data;
    }


}
