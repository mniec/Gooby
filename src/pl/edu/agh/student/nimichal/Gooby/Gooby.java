package pl.edu.agh.student.nimichal.Gooby;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Model;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Author: m
 * Date: 16.03.11
 * Time: 15:44
 */
public class Gooby {

    static Logger logger = Logger.getLogger(Gooby.class);

    private JList messageList;
    private JPanel mainPanel;
    private JTextField messageField;
    private JTextField roomField;
    private JTree roomsTree;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Rooms");
    private DefaultTreeModel roomsTreeModel = new DefaultTreeModel(rootNode);
    private static Loop mloop = new Loop();

    private DefaultListModel messagesListModel = new DefaultListModel();

    public static void main(String[] args) {
        try {
            if(args.length > 0)
                Settings.setConfigFile(args[0]);
            PropertyConfigurator.configure(Settings.Settings().getLogConfig());

            logger.debug("Starting application");

            JFrame frame = new JFrame("Gooby");
            frame.setContentPane(new Gooby().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            Model.getModel().setThisClient(MessageFactory.getLocalClient());

            mloop.start();
            mloop.mainLoop();
        } catch (HeadlessException e) {
            logger.fatal("Error in main method!", e);
            System.exit(1);
        }
    }

    public Gooby() {
        this.createUIComponents();
    }

    private void createUIComponents() {
        //mesages List!
        this.messageList.setModel(messagesListModel);
        this.messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.messageList.setSelectedIndex(0);

        //roomsList!
        this.roomsTree.setModel(roomsTreeModel);
        this.roomsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //adding new Message in room
        this.messageField.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {
            }

            public void keyPressed(KeyEvent keyEvent) {
            }

            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == 10) {
                    String message = messageField.getText();
                    ((DefaultListModel) messageList.getModel()).addElement(message);
                    mloop.sendMessage(message);
                }
            }
        });

        //adding new Room
        this.roomField.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {
            }

            public void keyPressed(KeyEvent keyEvent) {
            }

            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == 10) {
                    String roomName = roomField.getText();
                    Room room = new Room();
                    room.setName(roomName);
                    room.setClients(new Client[]{Model.getModel().getThisClient()});
                    Model.getModel().addRoom(room);
                    mloop.createRoom(room);
                }
            }
        });

        //selecting room
        this.roomsTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)roomsTree.getLastSelectedPathComponent();
                if(!node.isLeaf()){
                    Model.getModel().setCurrentRoom((Room)node.getUserObject());
                }
            }
        });

        //chat events
        Model.getModel().addChatListener(new ChatListener() {
            public void clientsChanged() {
                updateData();
            }

            public void roomsChanged() {
                updateData();
            }

            public void roomCreated() {
                updateData();
            }
        });


    }

    public void updateData() {
        rootNode =  new DefaultMutableTreeNode("Rooms");

        for (Room room : Model.getModel().getRooms()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(room);
            for (Client client : room.getClients()) {
                node.add(new DefaultMutableTreeNode(client));
            }
            rootNode.add(node);
        }

        roomsTreeModel = new DefaultTreeModel(rootNode);
        roomsTree.setModel(roomsTreeModel);
    }


}
