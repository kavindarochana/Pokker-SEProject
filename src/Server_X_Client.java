/**
 * Created by kash on 6/23/17.
 */
// echo server
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Server_X_Client {
    public static void main(String args[]){


        Socket s=null;
        ServerSocket ss2=null;
        BufferedReader  is = null;
        Map<Integer, Socket> clients = new HashMap<Integer, Socket>();

        System.out.println("Server Listening......");
        try{
            ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined
            // os=new PrintWriter(s.getOutputStream());
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");

        }

        while(true){
            try{


                s= ss2.accept();

                System.out.println("connection Established");

                Client c = new Client("x", s.getLocalAddress().toString(), s);

               // System.out.println(c.getIP());

            //metanta add karapan array list 1k
                clients.put(s.getPort(), s);//me map 1kt add kale

                System.out.println("clients" + clients);

                ServerThread st=new ServerThread(s, (HashMap) clients);

                st.start();



            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }

}

class ServerThread extends Thread{

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    HashMap clients;

    public ServerThread(Socket s,HashMap clients){
        this.s=s;
        this.clients=clients;
    }

    public void run() {
        try{
            is= new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());


        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            line=is.readLine();
            while(line.compareTo("QUIT")!=0){

                os.println(line);

                os.flush();

                System.out.println("Response to Client  :  "+line);
                //System.out.println("Port: "+s);
                //System.out.println(clients);

               //metanta arraylist 1kn item 1k load kalama hari
               sendToOneClient("KS",s.getLocalAddress().toString(),clients,s);







                line=is.readLine();

            }
        } catch (IOException e) {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }
        catch(NullPointerException e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }

        finally{
            try{
                System.out.println("Connection Closing..");
                if (is!=null){
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s!=null){
                    s.close();
                    System.out.println("Socket Closed");
                }

            }
            catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }//end finally
    }

    public void sendToOneClient (String userName, String ipAddress, Map<String, Client> clients,Socket socP) throws IOException {
       Client c = clients.get(userName + ":" + ipAddress);

        Socket socket = socP;

        //Mekata pass karaganna ona arraylist 1kn aragena


        //System.out.println(clients);


         OutputStream os = socket.getOutputStream();
         OutputStreamWriter osw = new OutputStreamWriter(os);
         BufferedWriter bw = new BufferedWriter(osw);
         bw.write("Some message");
         bw.flush();
    }

}