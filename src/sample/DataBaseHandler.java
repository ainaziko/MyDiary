package sample;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class DataBaseHandler extends Configs{
    //Connection dbConnection;

    public static Connection createConnection()
    {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/db"; //MySQL URL followed by the database name
        String username = "root"; //MySQL username
        String password = "Asadjioli0"; //MySQL password
        System.out.println("In DBConnection.java class ");

        try
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver"); //loading MySQL drivers. This differs for database servers
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            con = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
            System.out.println("Printing connection object "+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
    public void signUpUser(User user){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRST_NAME + "," + Const.USERS_LAST_NAME + "," +
                Const.USERS_USER_NAME + "," + Const.USERS_PASSWORD + ","  +
                Const.USERS_CITY + "," + Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = createConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getCity());
            prSt.setString(6, user.getGender());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addNoteToDataBase(Note note){
        String insert = "INSERT INTO " + Const.NOTES_TABLE + "(" +
                Const.NOTES_TITLE + "," + Const.NOTES_NOTE + "," + Const.NOTES_IDFK + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = createConnection().prepareStatement(insert);
            prSt.setString(1, note.getTitle());
            prSt.setString(2, note.getNote());
            prSt.setInt(3, note.getUsers_idUser());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USER_NAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = createConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resSet;
    }

    public int getIdUser(String userName){
        int id = 0;
        try {
            Statement stmt = createConnection().createStatement();

            ResultSet resultSet = null;
            String select = "SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USER_NAME + " = " + "'" + userName + "'";
            ResultSet rs = stmt.executeQuery(select);
            while (rs.next()){
                id = rs.getInt("idUser");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}
