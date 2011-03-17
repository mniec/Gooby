package pl.edu.agh.student.nimichal.Gooby;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Model;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Author: m
 * Date: 16.03.11
 * Time: 15:44
 */
public class Gooby implements KeyListener, ChatListener {

    static Logger logger = Logger.getLogger(Gooby.class);

    private JList messageList;
    private JPanel mainPanel;
    private JTextField messageField;
    private JTextField roomField;
    private JTree roomsTree;
    private TreeNode rootNode = new DefaultMutableTreeNode("Rooms");
    private DefaultTreeModel roomsTreeModel = new DefaultTreeModel(rootNode);

    private DefaultListModel messagesListModel = new DefaultListModel();

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
        this.roomField.addKeyListener(this);
        Model.Model().addChatListener(this);

        //mesages List!
        this.messageList.setModel(messagesListModel);
        this.messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.messageList.setSelectedIndex(0);

        //roomsList!
        this.roomsTree.setModel(roomsTreeModel);
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

    public void updateData() {
        for (Room room : Model.Model().getRooms()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(room);
            for (Client client : room.getClients()) {
                node.add(new DefaultMutableTreeNode(client));
            }
        }
    }

    public void ClientsChanged() {
        updateData();
    }

    public void RoomsChanged() {
        updateData();
    }
}
