package sample;

import sample.animations.Shake;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ForeignKeyID extends SignInController{


    ArrayList<Integer> mainList = new ArrayList<>();
    public static ArrayList<Integer> arr = new ArrayList<>();

    public void setID(String loginText, String loginPassword) {
        ArrayList<Integer> idArr = new ArrayList<>();
        DataBaseHandler dbHandler = new DataBaseHandler();

        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while(true){
            try {
                if (!result.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            counter++;
            if(counter >= 1){
                idArr.add(dbHandler.getIdUser(loginText));
                System.out.println(idArr);
            }
        }
        System.out.println(idArr.get(0));
    }

    public void putInfo(int idUser) {
        arr.add(idUser);
        System.out.println(arr);
    }

    public int getID(){
        System.out.println("from getID" + arr.get(0));
        return arr.get(0);
    }
    public void clearList(){
        arr.remove(0);
    }
}
