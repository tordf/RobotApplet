/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hibu.atek.tordf.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibu.atek.tordf.io.SaveHandler;
import org.hibu.atek.tordf.SourceProvider;
import robotapplet.StatusUpdater;

/**
 *
 * @author Tord
 */
public class ClientSocketHandler implements SaveHandler, SourceProvider {

    protected StatusUpdater statusUpdater;
    final InetSocketAddress  socketAddress = new InetSocketAddress("158.36.70.52" ,Protocol.PORT);

    public ClientSocketHandler(StatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }

    /**
     * Get the value of statusUpdater
     *
     * @return the value of statusUpdater
     */
    public StatusUpdater getStatusUpdater() {
        return statusUpdater;
    }

    /**
     * Set the value of statusUpdater
     *
     * @param statusUpdater new value of statusUpdater
     */
    public void setStatusUpdater(StatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }
    protected String ID = "TestLab1";

    ;

    /**
     * Get the value of ID
     *
     * @return the value of ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Set the value of ID
     *
     * @param ID new value of ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean RequestResource() {
        boolean result = false;
        try {
            Socket s = new Socket();
            System.out.println(socketAddress);
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.REQUEST_RESOURCE);
                bw.println(ID);
                bw.println(""+500);//120
                String res = br.readLine();
                if (res.compareTo(Protocol.RESOURCE_AQUIRED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_AQUIRED:Connection Successfull.\n Device aquired.");
                }
                else if(res.compareTo(Protocol.RESOURCE_DENIED) == 0)
                {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED:Connection Successfull.\n no free devices.");
                }
            }
        } catch (IOException ex) {
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR: Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String GetSource() {
        String result = null;
        try {
            Socket s = new Socket();
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.REQUEST_SOURCE);
                bw.println(ID);                
                String res = br.readLine();
                if (res.compareTo(Protocol.REQUEST_SOURCE_SUCESS) == 0) {
                    String size = br.readLine();
                    int iSize = Integer.parseInt(size);
                    char content[] = new char[iSize];
                    br.read(content, 0, iSize);
                    String sContent = new String(content);
                    result = sContent;
                    statusUpdater.UpdateStatusMessage("REQUEST_SOURCE_SUCESS:Connection Successfull.\n Device aquired.");
                }
                else if (res.compareTo(Protocol.REQUEST_SOURCE_FAIL) == 0) {
                    statusUpdater.UpdateStatusMessage("REQUEST_SOURCE_FAIL:\n Unable to get source.");
                }
                else if (res.compareTo(Protocol.RESOURCE_DENIED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED:\n Device not available.");
                }
            }
        } catch (IOException ex) {
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR: Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean Compile() {
        boolean result = true;
        try {
            Socket s = new Socket();
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.BUILD_RESOURCE);
                bw.println(ID);
                String res = br.readLine();
                if (res.compareTo(Protocol.BUILD_FAILED) == 0) {
                    statusUpdater.UpdateStatusMessage("BUILD_FAILED.");
                    result = false;
                } else if (res.compareTo(Protocol.BUILD_SUCESS) == 0) {
                    statusUpdater.UpdateStatusMessage("BUILD_SUCESS.");
                } else if (res.compareTo(Protocol.RESOURCE_DENIED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED: Build Failed.\n Was not able to connect to device.");
                }

                s.close();
            }

        } catch (IOException ex) {
            result = false;
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR: Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean Run() {
        boolean result = true;
        try {
            Socket s = new Socket();
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.RUN_RESOURCE);
                bw.println(ID);
                String res = br.readLine();
                if (res.compareTo(Protocol.RUN_FAIL) == 0) {
                    statusUpdater.UpdateStatusMessage("Run Failed.");
                    result = false;
                } else if (res.compareTo(Protocol.RUN_SUCESS) == 0) {
                    statusUpdater.UpdateStatusMessage("Run Success.");
                } else if (res.compareTo(Protocol.RESOURCE_DENIED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED: RUN_FAIL .\n Was not able to connect to device.");
                }
            }


        } catch (IOException ex) {
            result = false;
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR.\n Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean Deploy() {
        boolean result = false;
        try {
            Socket s = new Socket();
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.DEPLOY_RESOURCE);
                bw.println(ID);
                String res = br.readLine();
                if (res.compareTo(Protocol.DEPLOY_FAILED) == 0) {
                    statusUpdater.UpdateStatusMessage("DEPLOY_FAILED");
                }
                if (res.compareTo(Protocol.DEPLOY_SUCESS) == 0) {
                    result = true;
                    statusUpdater.UpdateStatusMessage("DEPLOY_SUCESS");
                } else if (res.compareTo(Protocol.RESOURCE_DENIED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED: DEPLOY_FAILED.\n Was not able to connect to device.");
                }
            }

        } catch (IOException ex) {
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR.\n Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean Save(String content) {
        boolean result = false;
        try {
            Socket s = new Socket();
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.SAVE_RESOURCE);
                bw.println(ID);
                bw.println("" + content.length());
                bw.println(content);
                String res = br.readLine();
                if (res.compareTo(Protocol.SAVE_FAIL) == 0) {
                    statusUpdater.UpdateStatusMessage("SAVE_FAIL .\n Was not able to connect to device.");
                }
                if (res.compareTo(Protocol.SAVE_SUCESS) == 0) {
                    result = true;
                    statusUpdater.UpdateStatusMessage("SAVE_SUCESS");
                } else if (res.compareTo(Protocol.RESOURCE_DENIED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED: SAVE_FAIL .\n Was not able to connect to device.");
                }
            }

        } catch (IOException ex) {
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR .\n Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean Release() {
        boolean result = false;
        try {
            Socket s = new Socket();
            s.connect(socketAddress);
            if (s.isConnected()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(s.getOutputStream(), true);
                bw.println(Protocol.RELEASE_RESOURCE);
                bw.println(ID);
                String res = br.readLine();
                if (res.compareTo(Protocol.RELEASE_FAIL) == 0) {
                    statusUpdater.UpdateStatusMessage("RELEASE_FAIL .\n Was not able to connect to device.");
                }
                if (res.compareTo(Protocol.RELEASE_SUCESS) == 0) {
                    result = true;
                    statusUpdater.UpdateStatusMessage("RELEASE_SUCESS");
                } else if (res.compareTo(Protocol.RESOURCE_DENIED) == 0) {
                    statusUpdater.UpdateStatusMessage("RESOURCE_DENIED .\n Was not able to connect to device.");
                }
            }

        } catch (IOException ex) {
            statusUpdater.UpdateStatusMessage("CONNECTION_ERROR .\n Was not able to connect to device.");
            Logger.getLogger(ClientSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
