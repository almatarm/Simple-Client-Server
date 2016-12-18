package com.almatarm.simpleclientserver.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Present the user with a dialog showing the server status. The dialog
 * also allow the user to shutdown the server.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class ServerWindow extends JDialog {

    /**
     * Construct a Server Status Dialog 
     * @param port the port on which the server is listening to clients. 
     */
    public ServerWindow(String port) {
        init(port);
    }

    private void init(String port)  {
        setTitle("Running...");
        setLayout(new GridLayout(2,0));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel msgPanel = new JPanel();
        msgPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        msgPanel.add(new JLabel("Server is running on port " + port));
        add(msgPanel);
        
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton exit = new JButton("Exit");
        exit.setToolTipText("Shutdown the server.");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        actionPanel.add(exit);
        add(actionPanel);
        
        //Add Close listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                System.exit(0);
            }    
        });
        
        setResizable(false);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth()  - this.getWidth())  / 2);
        int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

}
