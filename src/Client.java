import java.net.Socket;

/**
 * Created by kash on 6/23/17.
 */
class Client
{
    private String userName;
    private String ipAddress;
    private Socket socket = null;

    public Client (String userName, String ipAddress, Socket socket)
    {
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.socket = socket;

    }

    public Socket getSocket()
    {

        return this.socket;
    }

    public String getUser()
    {

        return this.userName;
    }

    public String getIP()
    {

        return this.ipAddress;
    }
}