package com.almatarm.simpleclientserver.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Main window of the application which enables users to use services provided
 * locally or by network
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class MainWindow extends JFrame {

    /**
     * Provides Search and Book services to GUI components. 
     */
    private DataController controller;
    
    /**
     * Construct a new Client Window 
     * @param mode Application mode
     */
    public MainWindow(ApplicationMode mode) {
        super(Constants.APPLICATOIN_NAME);
        init(mode);
    }

    private void init(ApplicationMode mode) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(7, 7));
        
        setJMenuBar(buildMenuBar());
        add(buildTopLayout(),    BorderLayout.CENTER);
        
        connectActions();
        
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth()  - this.getWidth())  / 2);
        int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
       
        //Initialize the controller and get all recrods
        controller = new DataController(mode);    
    }
    
    /**
     * Build menu bar
     * @return menu bar for this window
     */
    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        //File Menu
        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //exit menu item
        exitMenuItem = new JMenuItem();
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 
                InputEvent.ALT_MASK));
        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);   
        return menuBar;
    }
    
    /**
     * Build top layout which holds the welcome message and search box
     * @return top layout which holds the welcome message and services box
     */
    private JPanel buildTopLayout() {
        //Create Panel and setup layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 2)); //3 cols => horizontal
        
        //add components
        topPanel.add(new JLabel(String.format(
                "<html><h3>&nbsp;&nbsp;Welcome to %s<h3></html>",
                Constants.APPLICATOIN_NAME)));
        topPanel.add(buildEchoLayout());
        
        return topPanel;
    }
    
    /**
     * Build services layout
     * @return services layout
     */
    private JPanel buildEchoLayout() {
         //Create Panel and setup layout
        JPanel echoPanel = new JPanel();
        echoPanel.setBorder(BorderFactory.createTitledBorder("Services"));
        GridBagLayout layout = new GridBagLayout();
        //use fixed column width and heights
        layout.columnWidths = new int[] {0, 7, 0, 7, 0};
        layout.rowHeights   = new int[] {0, 5, 0};
        echoPanel.setLayout(layout);
        
        
        //Initialize components
        message       = new JTextField(20);
        echo          = new JTextField(20);
        echoButton    = new JButton("Echo");
        echoButton.setMnemonic(KeyEvent.VK_E);
        
        //add componenets
        GridBagConstraints gridBagConstraints;
        
        //Message Name row
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        echoPanel.add(new JLabel("Message:"), gridBagConstraints);
        gridBagConstraints.gridx  = 2;
        echoPanel.add(message, gridBagConstraints);
        gridBagConstraints.gridx  = 3;
        gridBagConstraints.fill   = GridBagConstraints.HORIZONTAL;
        
        //Echo row
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        echoPanel.add(new JLabel("Echo:"), gridBagConstraints);
        gridBagConstraints.gridx  = 2;
        echoPanel.add(echo, gridBagConstraints);
        gridBagConstraints.gridx  = 3;
        gridBagConstraints.fill   = GridBagConstraints.HORIZONTAL;
        echoPanel.add(echoButton, gridBagConstraints);
        
        return echoPanel;
    }
    
    /**
     * add actions to components
     */
    private void connectActions() {
        
        exitMenuItem.addActionListener(new ExitAction());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                new ExitAction().actionPerformed(null);
            }
        });

        echoButton.addActionListener((ActionEvent ae) -> {
            EventQueue.invokeLater(() -> {
                setCursor(Cursor.getPredefinedCursor(
                        Cursor.WAIT_CURSOR));
                try {
                    String echoMsg = controller.echo(message.getText());
                    echo.setText(echoMsg);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
        });

    }
    
    /**
     * An Action class called on exit
     */
    private class ExitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            controller.disconnect();
            System.exit(0);
        }
    }
    
    //Menu 
    JMenuItem exitMenuItem; 
    
    //TopPanel  
    private JTextField message;
    private JTextField echo;
    private JButton echoButton;
    
}
