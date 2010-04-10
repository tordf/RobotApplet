/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hibu.atek.tordf.communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibu.atek.tordf.util.Utils;

/**
 * Socket that just stays up.
 * @author Tord
 */
public class RoboustSocket
{

    protected Socket socket;

    /**
     * Get the value of socket
     *
     * @return the value of socket
     */
    public Socket getSocket() {
        if(socket==null || (socket != null && socket.isClosed()))
            socket = new Socket();
        return socket;
    }

    /**
     * Set the value of socket
     *
     * @param socket new value of socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    /**
     * Blocking, will block thread until is connected
     * @param sa
     */
    public void Connect(SocketAddress sa)
    {
        while (!getSocket().isConnected()) {
        try {            
                System.out.println("Connecting....");
                getSocket().connect(sa);// throws exception on falure.
                System.out.println("Connected....");
                
        } catch (IOException ex) {
            Utils.sleep(2000);
            close();
            //getSocket();
            Logger.getLogger(RoboustSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    public DataInputStream GetDataInputStream()
    {
        try {
            return new DataInputStream(getSocket().getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(RoboustSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public boolean IsConnected()
    {
        return getSocket().isConnected();
    }

    public void close()
    {
        try {
            getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(RoboustSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    


}
