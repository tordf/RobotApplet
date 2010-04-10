/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotapplet;

import org.hibu.atek.tordf.communication.RoboustSocket;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Tord
 */
public class ImageStreamer implements Runnable {

    protected ImageUpdater ImageUpdate;

    /**
     * Get the value of ImageUpdate
     *
     * @return the value of ImageUpdate
     */
    public ImageUpdater getImageUpdate() {
        return ImageUpdate;
    }

    /**
     * Set the value of ImageUpdate
     *
     * @param ImageUpdate new value of ImageUpdate
     */
    public void setImageUpdate(ImageUpdater ImageUpdate) {
        this.ImageUpdate = ImageUpdate;
    }

    

    public void run() {
        InputStream os = null;
        InetSocketAddress sa = new InetSocketAddress("127.0.0.1", 10);
        RoboustSocket rs = new RoboustSocket();
        //rs.Connect(sa);
        // keep connections
        while (!rs.IsConnected())// connect / reconnect
        {
            rs.Connect(sa);
                //BufferedReader br = new BufferedReader(new InputStreamReader(os));
                while (rs.IsConnected()) {
                try {

                    DataInputStream dis = rs.GetDataInputStream();
                    String ss = dis.readLine();//dis.readlreadUTF();
                    int size = Integer.parseInt(ss);//dis.readInt();//
                    //TODO: when NumberFormatException
                    System.out.println("size of img: " + size);

                    byte b[] = new byte[size];
                    dis.read(b, 0, size);
                    ByteArrayInputStream bis = new ByteArrayInputStream(b);
                    //ByteInputStream bis = new ByteInputStream(b, size);
                    BufferedImage bu = ImageIO.read(bis);
                    if (ImageUpdate != null && bu != null) {
                        ImageUpdate.ImageUpdated(bu);
                    }
                }
                catch (NumberFormatException nfe)
                {
                    System.out.println("error data from sever");
                    rs.close();
                }
                catch (IOException ex) { // assumes SocketException
                    Logger.getLogger(ImageStreamer.class.getName()).log(Level.SEVERE, null, ex);
                    rs.close();
                }
           
            }            
        }
    }
}
