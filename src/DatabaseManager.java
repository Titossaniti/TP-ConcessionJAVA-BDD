import Metier.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "concession";
    private String user = "root";
    private String passwd = "";

        public DatabaseManager() {
            try {
                createDatabase();
                createTables();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur");
                System.exit(0);
            }
        }
                public void createDatabase() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url + "mysql" + "?characterEncoding=utf8", user, passwd);
                String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
                System.out.println("Database created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error creating database.");
            }
        }
                public void createTables() {
            String fullUrl = url + dbName + "?characterEncoding=utf8";
            String tablename1 = "voiture";
            String tablename2 = "raisonSocial";
                    String newTable1 = "CREATE TABLE IF NOT EXISTS " + tablename1 + " ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "marque VARCHAR(50), "
                    + "model VARCHAR(50), "
                    + "raisonSocialId INT, "
                    + "FOREIGN KEY (raisonSocialId) REFERENCES " + tablename2 + "(id)"
                    + ")";
                    String newTable2 = "CREATE TABLE IF NOT EXISTS " + tablename2 + " ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "raisonSocial VARCHAR(50)"
                    + ")";
                    try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(fullUrl, user, passwd);
                PreparedStatement stmt = conn.prepareStatement(newTable1);
                PreparedStatement stmt2 = conn.prepareStatement(newTable2);
                stmt.executeUpdate(newTable2); // Create raisonSocial table first
                stmt.executeUpdate(newTable1); // Then create voiture table
                stmt.close();
                conn.close();
                System.out.println("Tables created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error creating tables.");
            }
        }
        //      EQUETES CRUD
                    public void addVehicule(Voiture voiture) {
                String addQuery = "INSERT INTO voiture (marque, model) VALUES (?, ?)";
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, passwd);
                    System.out.println("Connecté");
                    PreparedStatement pstmt = conn.prepareStatement(addQuery);
                    pstmt.setString(1, voiture.getMarque());
                    pstmt.setString(2, voiture.getModel());
                    pstmt.execute();
                    pstmt.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur");
                    System.exit(0);
                }
            }
                    public List<Voiture> getVehicule() {
                        List<Voiture> voitures = new ArrayList<>();
                        String selectQuery = "SELECT * FROM voiture";
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            try (Connection conn = DriverManager.getConnection(url, user, passwd);
                                 PreparedStatement pstmt = conn.prepareStatement(selectQuery);
                                 ResultSet rs = pstmt.executeQuery()) {
                                        while (rs.next()) {
                                    String marque = rs.getString("marque");
                                    String model = rs.getString("model");
                                    Voiture voiture = new Voiture(marque, model);
                                    voitures.add(voiture);
                                }
                            }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur");
                }
                return voitures;
            }
                    public void deleteVehicule(String marque) {
                String deleteQuery = "DELETE FROM voiture WHERE marque = ?";
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, passwd);
                    System.out.println("Connecté");
                    PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
                    pstmt.setString(1, marque);
                    pstmt.execute();
                    pstmt.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur");
                    System.exit(0);
                }
            }
                    public void updateVehicule(String oldMarque, String newMarque, String newModel) {
                String updateQuery = "UPDATE voiture SET marque = ?, model = ? WHERE marque = ?";
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, passwd);
                    System.out.println("Connecté");
                    PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                    pstmt.setString(1, newMarque);
                    pstmt.setString(2, newModel);
                    pstmt.setString(3, oldMarque);
                    pstmt.execute();
                    pstmt.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur");
                    System.exit(0);
                }
            }

}