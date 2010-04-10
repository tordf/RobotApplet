/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robotapplet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tord
 */
public class ImagePanel extends JPanel implements ImageUpdater
{

    BufferedImage image = null;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(image!=null)
        {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void ImageUpdated(final BufferedImage bufferedImage) {
        image = bufferedImage;
        //invalidate();
        repaint();
        /*SwingUtilities.invokeLater(new Runnable() {
        public void run() {

    }
});*/
        
    }

}
