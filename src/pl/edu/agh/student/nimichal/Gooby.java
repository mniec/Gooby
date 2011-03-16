package pl.edu.agh.student.nimichal;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Author: m
 * Date: 16.03.11
 * Time: 15:44
 */
public class Gooby implements KeyListener {

    static Logger logger = Logger.getLogger(Gooby.class);

    private JList messageList;
    private JPanel mainPanel;
    private JTextField messageField;
    private JList roomsList;
    private JTextField roomField;

    private DefaultListModel messagesListModel = new DefaultListModel();
    private DefaultListModel roomsListModel = new DefaultListModel();

    public static void main(String[] args) {
        try {
            PropertyConfigurator.configure(Settings.Settings().getLogConfig());

            logger.debug("Starting application");

            JFrame frame = new JFrame("Gooby");
            frame.setContentPane(new Gooby().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            new Loop().start();
        } catch (HeadlessException e) {
            logger.fatal("Error in main method!", e);
            System.exit(1);
        }
    }

    public Gooby() {
        this.createUIComponents();
    }

    private void createUIComponents() {
        //listeners
        this.messageField.addKeyListener(this);
        this.roomsList.addKeyListener(this);

        //mesages List!
        this.messageList.setModel(messagesListModel);
        this.messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.messageList.setSelectedIndex(0);

        //roomsList!
        this.roomsList.setModel(roomsListModel);
        this.roomsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.roomsList.setSelectedIndex(0);
    }


    public void keyTyped(KeyEvent keyEvent) {

    }

    public void keyPressed(KeyEvent keyEvent) {

    }

    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            String message = messageField.getText();
            JLabel label = new JLabel(message);
            ((DefaultListModel) messageList.getModel()).addElement(message);

        }
    }
}
