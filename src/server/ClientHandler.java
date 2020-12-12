package server;



import car.Cardata;
import javafx.fxml.Initializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientHandler extends Thread  {

    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private Connection con;
    static int count;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        count++;
        try {
            this.socket = socket;
            this.clients = clients;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            con=new ConnectionClass().getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            JSONObject msg;
            while ((msg = (JSONObject) in.readUnshared())!= null) {

                JSONArray jarray=new JSONArray();
                if(msg.get("task").equals("login")){
                    System.out.println(msg);
                    String  messageToSend="";
                    try {

                      /*  String line;
                        BufferedReader br = new BufferedReader(new FileReader("data.txt"));
                        while (true) {
                            line= br.readLine();
                            if (line== null) break;
                            messageToSend+=line+"\n";

                        }
                        br.close();*/


                        Statement stmt=con.createStatement();

                        ResultSet rs=stmt.executeQuery("SELECT * FROM USERS");
                        int state=0;
                        while(rs.next()){

                            String user=rs.getString("username");
                            String pass=rs.getString("password");
                            if(user.equals(msg.get("username"))&&pass.equals(msg.get("password"))){
                              for(int i=0;i<clients.size();i++){
                                  clients.get(i).out.writeUnshared(true);
                              }

                                state=1;
                                break;
                            }


                        }
                        if(state==0){
                            clients.get(count-1).out.writeUnshared(false);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(msg.get("task").equals("getCars")){

                    Statement stmt=con.createStatement();

                    ResultSet rs=stmt.executeQuery("SELECT * FROM car_data");

                    while(rs.next()){
                      //  System.out.println("name :"+rs.getString("u"));
                        // System.out.println("password "+rs.getString("password"));

                        JSONObject job=new JSONObject();
                        job.put("regNo",rs.getString("registration_no"));
                        job.put("yearMade",rs.getString("year_made"));
                        job.put("colour1",rs.getString("colour_1"));
                        job.put("colour2",rs.getString("colour_2"));
                        job.put("colour3",rs.getString("colour_3"));
                        job.put("price",rs.getString("price"));
                        job.put("quantity",rs.getInt("quantity"));
                        job.put("carMake",rs.getString("car_make"));
                        job.put("carModel",rs.getString("car_model"));
                        job.put("imagePath",rs.getString("Image"));
                        jarray.add(job);
                        //messageToSend+=rs.getString("username")+"\n";
                    }


/*for(int i=count-1;i>=0;i-=2){

    clients.get(i).out.writeUnshared(jarray);
}*/
                    for(int i=0;i<clients.size();i++){
                        clients.get(i).out.writeUnshared(jarray);
                    }




                }


                else if(msg.get("task").equals("addCars")){
                    System.out.println("success");
                    System.out.println((String)msg.get("image"));
                    String insert="INSERT INTO car_data VALUES(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stmt=con.prepareStatement(insert);
                    stmt.setString(1, (String) msg.get("regNo"));
                    stmt.setString(2, (String) msg.get("yearMade"));
                    stmt.setString(3, (String) msg.get("color_1"));
                    stmt.setString(4, (String) msg.get("color_2"));
                    stmt.setString(5, (String) msg.get("color_3"));
                    stmt.setString(6, (String) msg.get("carMake"));
                    stmt.setString(7, (String) msg.get("carModel"));

                    stmt.setInt(8,(int)msg.get("quantity"));
                    stmt.setString(9, (String) msg.get("price"));
                    stmt.setString(10,(String)msg.get("image"));
 stmt.execute();




                }
                else if(msg.get("task").equals("editCar")){
                    System.out.println("got");

                    String del="DELETE FROM car_data WHERE registration_no=?";
                    PreparedStatement stmt=con.prepareStatement(del);
                    stmt.setString(1, (String)(msg.get("reg")));
                    stmt.execute();
                    stmt.close();
                    String insert="INSERT INTO car_data VALUES(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement stmt2=con.prepareStatement(insert);
                    stmt2.setString(1, (String) msg.get("regNo"));
                    stmt2.setString(2, (String) msg.get("yearMade"));
                    stmt2.setString(3, (String) msg.get("color_1"));
                    stmt2.setString(4, (String) msg.get("color_2"));
                    stmt2.setString(5, (String) msg.get("color_3"));
                    stmt2.setString(6, (String) msg.get("carMake"));
                    stmt2.setString(7, (String) msg.get("carModel"));

                    stmt2.setInt(8,(int)msg.get("quantity"));
                    stmt2.setString(9, (String) msg.get("price"));
                    stmt2.setString(10,(String)msg.get("image"));

                    stmt2.execute();

                    stmt2.close();





                }
                else if(msg.get("task").equals("createUser")){
                    String insert="INSERT INTO users VALUES(?,?)";
                    System.out.println("got");
                    PreparedStatement stmt=con.prepareStatement(insert);
                    stmt.setString(1,(String)msg.get("userName"));
                    stmt.setString(2,(String)msg.get("password"));
stmt.execute();
stmt.close();
out.writeUnshared(true);
                }


            }
        } catch (Exception e) {

        }
        finally {
            try {
                out.close();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }}

