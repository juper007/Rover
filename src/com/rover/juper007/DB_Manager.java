package com.rover.juper007;

import java.sql.*;
import java.util.ArrayList;

public class DB_Manager {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
    private static final String DB_NAME = "rover";
    private static final String DB_USER = "juper007";
    private static final String DB_PASS = "Redmond1!";
    private static Statement stmt = null;

    public DB_Manager () {
        this.stmt = getDBConnection(this.DB_URL, this.DB_USER, this.DB_PASS);
    }

    private static Statement getDBConnection(String db_url, String db_user, String db_pass) {
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            stmt = con.createStatement();
            stmt.executeQuery("use " + DB_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return stmt;
    }

    public User insertUserInfo(String userName, String userImage, String userPhone, String userEmail) {
        String getUserQuery = String.format("SELECT User_Id, User_Name, User_Image, User_Phone, User_Email FROM users" +
                " WHERE User_Name = '%s' AND User_Phone = '%s'", userName, userPhone);
        ResultSet rs;
        User user = null;
        try {
            rs = stmt.executeQuery(getUserQuery);
            rs.last();
            if (rs.getRow() < 1) {
                String insertUserQuery = String.format("INSERT INTO users (User_Name, User_Image, User_Phone, User_Email) " +
                        "VALUES ('%s', '%s', '%s', '%s');", userName, userImage, userPhone, userEmail);
                stmt.executeUpdate(insertUserQuery);
                rs = stmt.executeQuery(getUserQuery);
            }

            rs.beforeFirst();
            rs.next();
            user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Sitter insertSitterInfo(String sitterName, String sitterImage, String sitterPhone, String sitterEmail) {
        String getSitterQuery = String.format("SELECT Sitter_Id, Sitter_Name, Sitter_Image, Sitter_Phone, Sitter_Email, Sitter_Score FROM sitters" +
                " WHERE Sitter_Name = '%s' AND Sitter_Phone = '%s'", sitterName, sitterPhone);
        ResultSet rs;
        Sitter sitter = null;
        try {
            rs = stmt.executeQuery(getSitterQuery);
            rs.last();
            if (rs.getRow() < 1) {
                String insertUserQuery = String.format("INSERT INTO sitters (Sitter_Name, Sitter_Image, Sitter_Phone, Sitter_Email, Sitter_Score) " +
                        "VALUES ('%s', '%s', '%s', '%s', %d);", sitterName, sitterImage, sitterPhone, sitterEmail, Common.getSitterScore(sitterName));
                stmt.executeUpdate(insertUserQuery);
                rs = stmt.executeQuery(getSitterQuery);
            }

            rs.beforeFirst();
            rs.next();
            sitter = new Sitter(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sitter;
    }

    public Pet insertPetInfo(int userId, String petName) {
        String getPetQuery = String.format("SELECT Pet_Id, User_Id, Pet_Name FROM pets " +
                "WHERE User_Id = %d AND Pet_Name = '%s'", userId, petName);
        ResultSet rs;
        Pet pet = null;
        try {
            rs = stmt.executeQuery(getPetQuery);
            rs.last();
            if (rs.getRow() < 1) {
                String insertUserQuery = String.format("INSERT INTO pets (User_Id, Pet_Name) " +
                        "VALUES (%d, '%s');", userId, petName);
                stmt.executeUpdate(insertUserQuery);
            }

            rs.beforeFirst();
            rs.next();
            pet = new Pet(rs.getInt("Pet_Id"), rs.getInt("User_Id"), rs.getString("Pet_Name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }

    public Sitting insertSittingInfo(int userId, int sitterId, String startDate, String endDate, String text, String rating) {
        String insertSittingQuery = String.format("INSERT INTO sittings (User_Id, Sitter_Id, Sitting_StartDate, Sitting_EndDate, Sitting_Text, Sitting_Rating) " +
                "VALUES (%d, %d, '%s', '%s', '%s', '%s');", userId, sitterId, startDate, endDate, text, rating);

        String getSittingQuery = String.format("SELECT Sitting_Id, User_Id, Sitter_Id, Sitting_StartDate, Sitting_EndDate, Sitting_Text, Sitting_Rating FROM Sittings " +
                "WHERE User_Id = %d AND Sitter_Id = %d AND Sitting_StartDate = '%s' AND Sitting_EndDate = '%s';", userId, sitterId, startDate, endDate);

        Sitting sitting = null;
        ResultSet rs;
        try {
            stmt.executeUpdate(insertSittingQuery);
            rs = stmt.executeQuery(getSittingQuery);
            rs.next();
            sitting = new Sitting(rs.getInt("Sitting_Id"),
                    rs.getInt("User_Id"),
                    rs.getInt("Sitter_Id"),
                    rs.getDate("Sitting_StartDate"),
                    rs.getDate("Sitting_EndDate"),
                    rs.getString("Sitting_Text"),
                    rs.getInt("Sitting_Rating")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sitting;
    }

    public void insertPetList(int sittingId, int petId) {
        try {
            String insertUserQuery = String.format("INSERT INTO sitting_pet_list (Sitting_Id, Pet_Id) " +
                    "VALUES (%d, %d);", sittingId, petId);
            stmt.executeUpdate(insertUserQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sitter> getSitter() {
        ArrayList<Sitter> sitters = new ArrayList<>();
        try {
            String getSitterQuery = String.format("SELECT Sitter_Id, Sitter_Name, Sitter_Image, Sitter_Phone, Sitter_Email, Sitter_Score " +
                    "FROM sitters");
            ResultSet rs = stmt.executeQuery(getSitterQuery);
            while (rs.next()) {
                Sitter sitter = new Sitter(rs.getInt("Sitter_Id"),
                        rs.getString("Sitter_Name"),
                        rs.getString("Sitter_Image"),
                        rs.getString("Sitter_Phone"),
                        rs.getString("Sitter_Email"),
                        rs.getInt("Sitter_Score"));
                sitters.add(sitter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sitters;
    }

    public Sitter getSitter(int sitterId) {
        Sitter sitter = null;
        try {
            String getSitterQuery = String.format("SELECT Sitter_Id, Sitter_Name, Sitter_Image, Sitter_Phone, Sitter_Email, Sitter_Score " +
                    "FROM sitters WHERE Sitter_Id = %d", sitterId);
            ResultSet rs = stmt.executeQuery(getSitterQuery);
            rs.next();
            sitter = new Sitter(rs.getInt("Sitter_Id"),
                    rs.getString("Sitter_Name"),
                    rs.getString("Sitter_Image"),
                    rs.getString("Sitter_Phone"),
                    rs.getString("Sitter_Email"),
                    rs.getInt("Sitter_Score"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sitter;
    }
}
