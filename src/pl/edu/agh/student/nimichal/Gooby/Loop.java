package pl.edu.agh.student.nimichal.Gooby;

import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.*;
import pl.edu.agh.student.nimichal.Gooby.Model.Model;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;

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
    private Socket tcpSock;

    private ByteBuffer buffer;

    public Loop() {

    }

    public void initSockets() {
        try {

            multiAddr = InetAddress.getByName(Settings().getMulticastIP());
            multiSock = new MulticastSocket(new InetSocketAddress(Settings().getMulticastPort()));
            multiSock.setReuseAddress(true);
            multiSock.setTimeToLive(Settings().getMulticastTTL());
            multiSock.setSoTimeout(Settings().getTimeout());

            udpSock = new DatagramSocket(Settings().getUdpPort());
            udpSock.setSoTimeout(Settings().getTimeout());
            udpSock.setReuseAddress(true);

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        }
    }

    public void start() {
        try {
            initSockets();

            multiSock.setTimeToLive(Settings().getMulticastTTL());
            multiSock.joinGroup(multiAddr);

            Greeting message = MessageFactory.crateGreetingMsg();

            sendMulticast(message);

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        }
    }

    private DatagramPacket receive() {
        DatagramPacket packet = MessageFactory.getPacket();

        while (true) {
            try {
                try {
                    udpSock.receive(packet);
                    return packet;
                } catch (SocketTimeoutException e) {

                }
                try {
                    multiSock.receive(packet);
                    return packet;
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
            DatagramPacket packet = receive();
            Message response = MessageFactory.formByte(packet.getData(), packet.getLength());

            if (response instanceof GreetingResponse)
                handle((GreetingResponse) response);
            else if (response instanceof RoomCreation)
                handle((RoomCreation) response);
            else if (response instanceof Greeting)
                handle((Greeting) response);
            else if(response instanceof JoinRoom)
                handle((JoinRoom) response);
            else if(response instanceof SendMessage)
                handle((SendMessage) response);
            else if(response instanceof LeaveRoom)
                handle((LeaveRoom) response);

        }
    }



    public void sendMulticast(Message message) {
        try {
            DatagramPacket packet = message.dumpToDatagram();
            packet.setAddress(multiAddr);
            packet.setPort(Settings().getMulticastPort());

            logger.debug("Sending Multicast message:" + message.toString());
            logger.debug("packetdup:" + packet.toString());

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
            logger.debug("packetdup:" + packet.toString());

            multiSock.send(packet);
        } catch (IOException e) {
            logger.fatal("Error durring sending multicast!", e);
            System.exit(1);
        }
    }

    //EventHandlers

    public void handle(GreetingResponse message) {
        Model.getModel().addClient(message.getClient());
    }

    private void handle(RoomCreation message) {
        Model.getModel().addRoom(message.getRoom());
    }

    private void handle(Greeting message) {
        Model.getModel().addClient(message.getClient());
        sendUDP(MessageFactory.creteGreetingResponse(),message.getClient().getIpAddress(),Settings().getUdpPort());
    }

    private void handle(LeaveRoom message) {
        Client.find(message.getClient()).setCurrentRoom(null);
    }

    private void handle(SendMessage message) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void handle(JoinRoom message) {
        Client.find(message.getClient()).setCurrentRoom(Room.find(message.getRoom()));
    }

    //Functions
    public void sendMessage(String message) {
    }

    public void createRoom(Room room) {
        RoomCreation message = MessageFactory.createRoomCreationMessage(room);
        sendMulticast(message);
    }
}
