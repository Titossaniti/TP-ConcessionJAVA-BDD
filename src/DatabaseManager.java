import Metier.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    String BDD = "mysql";
    String url = "jdbc:mysql://localhost:3306/" + BDD +"?characterEncoding=utf8";
    String user = "root";
    String passwd = "";
    String db2 = "Concession";
    String url2 = "jdbc:mysql://localhost:3306/" + db2 +"?characterEncoding=utf8";

         public DatabaseManager() {
                System.out.println("Start bdd connexion");

// Test connexion BDD
//             try {
//                 Class.forName("com.mysql.jdbc.Driver");
//                 Connection conn = DriverManager.getConnection(url, user, passwd);
//                 System.out.println("Connecté");
//                 conn.close();
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 System.out.println("Erreur");
//                 System.exit(0);
//             }

             // Créer la BDD
             try {
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection conn = DriverManager.getConnection(url, user, passwd);
                 System.out.println("Connecté");
                 String db = "CREATE DATABASE IF NOT EXISTS " + db2;
                 PreparedStatement st = conn.prepareStatement(db);
                 boolean reussi = st.execute(db);
                 System.out.print(reussi);

             // Créer les tables

                 String tablename1 = "voiture";
                 String tablename2 = "concession";
                 String newTable1 = "CREATE TABLE IF NOT EXISTS "+ tablename1+"(marque VARCHAR(50), model VARCHAR(50))";
                 conn.prepareStatement(newTable1);
                 st.execute(newTable1);
                 String newTable2 = "CREATE TABLE IF NOT EXISTS "+ tablename2+"(raisonSocial VARCHAR(50))";
                 conn.prepareStatement(newTable2);
                 st.execute(newTable2);
                 st.close();
                 conn.close();
                 System.out.println("Tables "+ tablename1+ " et "+ tablename2 +" crées");
             }
             catch (Exception e) {
                 e.printStackTrace();
                 System.out.println("Erreur");
                 System.exit(0);
             }

         }

    public DatabaseManager(String url, String user, String passwd) {
        this.url = url;
        this.user = user;
        this.passwd = passwd;
    }

// REQUETES CRUD

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