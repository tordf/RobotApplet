/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robotapplet;

import javax.swing.JApplet;

/**
 *
 * @author Tord
 */
public class RoboApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        MainJFrame mf =new MainJFrame();
        mf.setVisible(true);
        mf.toFront();        // TODO start asynchronous download of heavy resources
    }

    // TODO overwrite start(), stop() and destroy() methods

}
