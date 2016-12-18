package com.almatarm.simpleclientserver.gui;

import static com.almatarm.simpleclientserver.gui.ConfigManager.Key.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * Presents the user with a dialog to select the application mode (server, 
 * networking or standalone) and its related options (server 
 * host/IP and port).
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class RunConfigurationDialog extends JDialog {
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(
            RunConfigurationDialog.class.getName());
    
    /**
     * current selected application mode
     */
    private ApplicationMode mode;
    
    //Store and load application configuration
    private ConfigManager config;

    /**
     * Construct a new <code>RunConfigurationDialog</code> object.
     * @param mode Initial application mode which can be changed via this 
     * dialog.
     */
    public RunConfigurationDialog(ApplicationMode mode) {
        this.mode   = mode;
        this.config = ConfigManager.getInstance();
        init();
    }
    
    private void init() {
        setTitle(Constants.APPLICATOIN_NAME + mode + " mode");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      
        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);
        add(buildAppModePanel(),       BorderLayout.WEST);
        add(buildConfigurationPanel(), BorderLayout.CENTER);
        add(buildActionsPanel(),       BorderLayout.SOUTH);
        
        connectActions();
        
        //Select the radio button associated with the mode
        JRadioButton selectedButton = null;
        switch(mode) {
            case ALONE:
                selectedButton = aloneRButton;
                break;
            case NETWORK:
                selectedButton = networkRButton;
                break;
            case SERVER:
                selectedButton = serverRButton;
                break;
        }
        if(selectedButton != null) {
            selectedButton.setSelected(true);
        }
        
        //load configuration
        loadConfig();
        
        //Pack, center on screen the window
        setResizable(false);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth()  - this.getWidth())  / 2);
        int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    /**
     * Return Application configuration, e.g. location, server host and
     * port.
     * @return Application configuration, e.g. location, server host 
     * and port.
     */
    public ConfigManager getConfig() {
        return config;
    }

    /**
     * Get selected application mode.
     * @return selected application mode.
     */
    public ApplicationMode getMode() {
        return mode;
    }
    
    /**
     * load configuration form disk and apply to dialog component
     */
    private void loadConfig() {
        serverHost.setText(config.getProperty(ConfigManager.Key.SERVER_HOST));
        serverPort.setValue(Integer.valueOf(Integer.parseInt(
                config.getProperty(ConfigManager.Key.PORT))));
    }
    
    /**
     * Save current window configuration to disk
     */
    private void saveConfig() {
        try {
            config.setProperty(SERVER_HOST, serverHost.getText());
            config.setProperty(PORT, ((Integer) serverPort.getValue()).toString());
            config.writeConfig();
        } catch (IOException ex) {
            //TODO send error to the user
            logger.severe(ex.getMessage());
        }
    }
    
    /**
     * Create configuration panel  with allows the user to select db location, 
     * server and IP.
     * @return configuration panel  with allows the user to select db location, 
     * server and IP.
     */
    private JPanel buildConfigurationPanel() {
        //Create Panel and setup layout and border
        JPanel configPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        //use fixed column width and heights
        layout.columnWidths = new int[] {0, 7, 0, 7, 0};
        layout.rowHeights   = new int[] {0, 5, 0, 5, 0};
        configPanel.setLayout(layout);
        configPanel.setBorder(BorderFactory.createTitledBorder(
                "Configuration"));
        
        //Initialize components
        serverHost       = new JTextField(35);
        serverPort = new JSpinner();
        SpinnerNumberModel serverPortModel = new SpinnerNumberModel(
                        Constants.DEFAULT_PORT, //Default value
                        null, null, 1); //min, max, step
        serverPort.setModel(serverPortModel);
        
        //Add components to layout
        GridBagConstraints gridBagConstraints;
        
        //Server Host/IP Row
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        configPanel.add(new JLabel("Server Host/IP:"), gridBagConstraints);
        gridBagConstraints.gridx  = 2;
        gridBagConstraints.fill   = GridBagConstraints.HORIZONTAL;
        configPanel.add(serverHost, gridBagConstraints);
        
        //Server Port Row
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        configPanel.add(new JLabel("Port:"), gridBagConstraints);
        gridBagConstraints.gridx  = 2;
        configPanel.add(serverPort, gridBagConstraints);
        
        //Add tooltip
        serverHost.setToolTipText("Ther server host name or IP "
                + "address");
        serverPort.setToolTipText("The port on which the server will " 
                + "listen to clients");
        
        return configPanel;
    }
    
    /**
     * Build application mode panel that helps in selecting the application mode
     * @return application mode panel
     */
    private JPanel buildAppModePanel() {
        //Create Panel and setup layout and border
        JPanel appModePanel = new JPanel();
        appModePanel.setBorder(BorderFactory.createTitledBorder("Mode"));
        appModePanel.setLayout(new GridLayout(3, 0)); //3 rows => vertical
        
        //Initialize components
        aloneRButton   = new JRadioButton("Standalone");
        aloneRButton.setMnemonic(KeyEvent.VK_A);
        networkRButton = new JRadioButton("Network");
        networkRButton.setMnemonic(KeyEvent.VK_N);
        serverRButton  = new JRadioButton("Server");
        serverRButton.setMnemonic(KeyEvent.VK_V);
        modeBtnGroup   = new ButtonGroup();
        modeBtnGroup.add(aloneRButton);
        modeBtnGroup.add(networkRButton);
        modeBtnGroup.add(serverRButton);
        
        //Add tooltips
        aloneRButton.setToolTipText(
                "Start GUI application that connects directly to "
                + "without any networking.");
        networkRButton.setToolTipText(
                "Start application that connects to server via "
                + "networking.");
        serverRButton.setToolTipText("Start server alone.");
        
        //Add components to layout
        appModePanel.add(aloneRButton);
        appModePanel.add(networkRButton);
        appModePanel.add(serverRButton);
        
        return appModePanel;
    }
    
    /**
     * Create actions panel with 2 buttons (start and exit)
     * @return actions panel
     */
    private JPanel buildActionsPanel() {
        //Create the panel
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        //Creating buttons
        startButton = new JButton("Start");
        startButton.setMnemonic(KeyEvent.VK_S);
        exitButton  = new JButton("Exit");
        exitButton.setMnemonic(KeyEvent.VK_X);
        
        //Add tooltips
        startButton.setToolTipText("Start the application on selected mode");
        exitButton.setToolTipText("Exit the application");
        
        //add them to panel
        actionsPanel.add(startButton);
        actionsPanel.add(exitButton);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                onExit();
            }    
        });
        return actionsPanel;
    }
    
    /**
     * add actions to components
     */
    private void connectActions() {
        aloneRButton.addItemListener(
                new AppModeGroupEvent(ApplicationMode.ALONE));
        networkRButton.addItemListener(
                new AppModeGroupEvent(ApplicationMode.NETWORK));
        serverRButton.addItemListener(
                new AppModeGroupEvent(ApplicationMode.SERVER));
        
        //Start Button -> hide the dialog
        StartAction startAction = new StartAction();
        startButton.addActionListener(startAction);
        serverHost.addActionListener(startAction);
        
        //Exit Application Event
        exitButton.addActionListener((ActionEvent ae) -> {
            onExit();
        });
    }
    
    /**
     * save configuration when the dialog closes
     */
    private void onExit() {
        saveConfig();
        System.exit(0);
    }
    
    /**
     * A helper class to track application mode changes
     */
    private class AppModeGroupEvent implements ItemListener {
        ApplicationMode mode;

        public AppModeGroupEvent(ApplicationMode mode) {
            this.mode = mode;
        }

        public void itemStateChanged(ItemEvent ie) {
            if(ie.getStateChange() == ItemEvent.SELECTED) {
                applicationModeChanged(mode);
            }
        }
    }
    
    /**
     * Application Mode Changed; enable disable related fields.
     * @param mode 
     */
    private void applicationModeChanged(ApplicationMode mode) {
        this.mode = mode;
        
        boolean databasedLocationEnabled = false;
        boolean serverHostEnabled        = false;
        boolean serverPortEnabled        = false;
        switch(mode) {
            case ALONE:
                databasedLocationEnabled = true;
                break;
            case NETWORK:
                serverHostEnabled        = true;
                serverPortEnabled        = true;
                break;
            case SERVER:
                databasedLocationEnabled = true;
                serverPortEnabled        = true;
                break;
        }
        
        //Enable/Disable componenets
        serverHost.setEnabled(serverHostEnabled);
        serverPort.setEnabled(serverPortEnabled);
    }
    
    /**
     * An Action class called to start the selected mode
     */
    private class StartAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            saveConfig();
            setVisible(false);
        }
    }
    
    //Configuration Panel
    private JTextField serverHost;
    private JSpinner serverPort;
    
    //Application Mode Panel
    private ButtonGroup modeBtnGroup;
    private JRadioButton aloneRButton;
    private JRadioButton networkRButton;
    private JRadioButton serverRButton;
    
    //Action Panel
    private JButton startButton;
    private JButton exitButton;   
  
}
