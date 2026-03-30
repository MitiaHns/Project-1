package com.forage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/db_gestion_forage";
        String user = "postgres";
        String password = "postgresM";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM TypeDevis";

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("ID | TypeDevis");
            System.out.println("------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String libelle = rs.getString("libelle");

                System.out.printf("%d | %s\n", id, libelle);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}