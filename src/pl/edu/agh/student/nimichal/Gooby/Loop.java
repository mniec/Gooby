package pl.edu.agh.student.nimichal.Gooby;

import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.*;
import pl.edu.agh.student.nimichal.Gooby.Model.Model;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;
import pl.edu.agh.student.nimichal.Gooby.Model.StringMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

import static pl.edu.agh.student.nimichal.Gooby.Settings.Settings;

/**
 * User: m
 * Date: 16.03.11
 * Time: 20:40
 */

public class Loop {
    static Logger logger = Logger.getLogger(Loop.class);

    private MulticastSocket multiSock;
    private InetAddress multiAddr;

    private DatagramSocket udpSock;

    private ServerSocket tcpServerSock;
    private Socket tcpSock;

    public Loop() {

    }

    public void initSockets() {
        try {

            multiAddr = InetAddress.getByName(Settings().getMulticastIP());
            multiSock = new MulticastSocket(Settings().getMulticastPort());
            multiSock.setReuseAddress(true);
            multiSock.setTimeToLive(Settings().getMulticastTTL());
            multiSock.setSoTimeout(Settings().getTimeout());
            multiSock.joinGroup(multiAddr);

            udpSock = new DatagramSocket(Settings().getUdpPort());
            udpSock.setSoTimeout(Settings().getTimeout());
            udpSock.setReuseAddress(true);

            tcpServerSock = new ServerSocket(Settings().getTcpPort());
            tcpServerSock.setSoTimeout(Settings().getTimeout());
            tcpSock = new Socket();
            tcpSock.setSoTimeout(Settings().getTimeout());

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        }
    }

    public void start() {
        initSockets();

        Greeting message = MessageFactory.crateGreetingMsg();

        sendMulticast(message);
    }

    private Message receive() {
        DatagramPacket packet = MessageFactory.getPacket();
        packet.setPort(Settings().getMulticastPort());

        while (true) {
            try {
                try {
                    udpSock.receive(packet);
                    return MessageFactory.formByte(packet.getData(), packet.getLength());
                } catch (SocketTimeoutException e) {

                }
                try {
                    multiSock.receive(packet);
                    return MessageFactory.formByte(packet.getData(), packet.getLength());
                } catch (SocketTimeoutException e) {
                }

                try {
                    byte[] buff =  MessageFactory.getBuffer();
                    Socket sock =  tcpServerSock.accept();
                    new DataInputStream(sock.getInputStream()).read(buff);
                    return  MessageFactory.formByte(buff,buff.length);
                } catch (SocketTimeoutException e) {
                }


                Thread.sleep(10);
            } catch (IOException e) {
                logger.fatal("Error during receiving!", e);
                System.exit(1);
            } catch (InterruptedException e) {
                logger.fatal("Interrupted during sleep!", e);
                System.exit(1);
            }

        }

    }

    public void mainLoop() {
        while (true) {

            Message response = receive();

            if (response.getClient().equals(Model.getModel().getThisClient()))
                continue;

            logger.debug("Receive message: " + response.toString());

            if (response instanceof GreetingResponse)
                handle((GreetingResponse) response);
            else if (response instanceof RoomCreation)
                handle((RoomCreation) response);
            else if (response instanceof Greeting)
                handle((Greeting) response);
            else if (response instanceof JoinRoom)
                handle((JoinRoom) response);
            else if (response instanceof StringMessage)
                handle((StringMessage) response);
            else if (response instanceof LeaveRoom)
                handle((LeaveRoom) response);
            else if (response instanceof AckMessage)
                handle((AckMessage) response);

        }
    }


    public void sendMulticast(Message message) {
        try {
            DatagramPacket packet = message.dumpToDatagram();
            packet.setAddress(multiAddr);
            packet.setPort(Settings().getMulticastPort());

            logger.debug("Sending Multicast message:" + message.toString());

            multiSock.send(packet);
        } catch (IOException e) {
            logger.fatal("Error durring sending multicas !", e);
            System.exit(1);
        }
    }

    public void sendUDP(Message message, String addr, int port) {
        try {
            DatagramPacket packet = message.dumpToDatagram();
            packet.setAddress(InetAddress.getByName(addr));
            packet.setPort(port);

            logger.debug("Sending UDP message:" + message.toString());

            multiSock.send(packet);
        } catch (IOException e) {
            logger.fatal("Error durring sending UDP!", e);
            System.exit(1);
        }
    }

    public void sendTCP(Message message, String addr, int port){
        try {

            tcpSock.connect(InetSocketAddress.createUnresolved(addr,port));

            logger.debug("Sending TCP message:" + message.toString());

            new DataOutputStream(tcpSock.getOutputStream()).write(message.toBytes());
            tcpSock.close();

        } catch (IOException e) {
            logger.fatal("Error durring sending TCP!", e);
            System.exit(1);
        }
    }

    //EventHandlers

    public void handle(GreetingResponse message) {
        Model.getModel().addClient(message.getClient());
        Model.getModel().updateGUI();
    }

    private void handle(RoomCreation message) {
        Model.getModel().addRoom(message.getRoom());
        Model.getModel().updateGUI();
    }

    private void handle(Greeting message) {
        Model.getModel().addClient(message.getClient());
        sendUDP(MessageFactory.creteGreetingResponse(), message.getClient().getIpAddress(), message.getClient().getUDPPort());
    }

    private void handle(LeaveRoom message) {
        Client.find(message.getClient()).setCurrentRoom(null);
    }

    private void handle(StringMessage message) {
        Model.getModel().addMessage(message);
        sendUDP(MessageFactory.createAckMessage(message), message.getClient().getIpAddress(), message.getClient().getUDPPort());
        Model.getModel().updateGUI();
    }

    private void handle(JoinRoom message) {
        Client.find(message.getClient()).setCurrentRoom(Room.find(message.getRoom()));
        Room.find(message.getRoom()).addClient(Client.find(message.getClient()));
        Model.getModel().updateGUI();
    }

    private void handle(AckMessage response) {
        StringMessage.find(response.getMessage()).getRecipientsLeft().remove(Client.find(response.getClient()));
    }


    //Functions
    public void sendMessage(String message) {
        StringMessage msg = MessageFactory.createSendMessage(message);
        Model.getModel().addMessage(msg);
        for (Client client : msg.getRecipientsLeft()) {
            sendUDP(msg, client.getIpAddress(), client.getUDPPort());
        }

        int delay = 1000; //milliseconds

        new Timer(Settings().getTimeToReceiveResponse(), new TCPCorrector(msg)).start();

        Model.getModel().updateGUI();
    }

    public class TCPCorrector implements ActionListener{
        private StringMessage msg;
        public TCPCorrector(StringMessage msg){
            this.msg = msg;
        }
        public void actionPerformed(ActionEvent actionEvent) {
            for(Client client:msg.getRecipientsLeft()){
                sendTCP(msg,msg.getClient().getIpAddress(),msg.getClient().getTCPPort());
                msg.getRecipientsLeft().remove(client);
            }
        }
    }


    public void createRoom(Room room) {

        Model.getModel().addRoom(room);
        sendMulticast(MessageFactory.createRoomCreationMessage(room));
        Model.getModel().updateGUI();
    }

    public void joinRoom(Room room) {
        Model.getModel().setCurrentRoom(room);
        sendMulticast(MessageFactory.createJoinRoomMsg(room));
        Model.getModel().updateGUI();
    }
}
