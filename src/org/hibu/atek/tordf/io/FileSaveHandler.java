/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hibu.atek.tordf.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tord
 */
public class FileSaveHandler implements SaveHandler
{

    public boolean Save(String text)
    {
       boolean result = true;
        try {
            File f = new File("source.java");
            if (!f.exists()) f.createNewFile();
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(text);
            //fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            result = false;
            Logger.getLogger(FileSaveHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
       return result;
    }

}
