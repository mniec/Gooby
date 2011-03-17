package pl.edu.agh.student.nimichal.Gooby;

import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.Greeting;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.GreetingResponse;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.Message;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.RoomCreation;
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
        try {

            multiAddr = InetAddress.getByName(Settings().getMulticastIP());
            multiSock = new MulticastSocket(new InetSocketAddress(Settings().getMulticastPort()));
            multiSock.joinGroup(multiAddr);
            multiSock.setSoTimeout(Settings().getTimeout());

            udpSock = new DatagramSocket(Settings().getUdpPort());
            udpSock.setSoTimeout(Settings().getTimeout());

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        }
    }

    public void start() {
        try {
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
            Message response = MessageFactory.formByte(receive().getData(), receive().getLength());

            if (response instanceof GreetingResponse)
                handle((GreetingResponse) response);
            else if (response instanceof RoomCreation)
                handle((RoomCreation) response);
            else if (response instanceof Greeting)
                handle((Greeting) response);
        }
    }

    public void sendMulticast(Message message) {
        try {
            logger.debug("Sending Multicast message:" + message.toString());
            logger.debug("packetdup:" + message.dumpToDatagram().toString());
            multiSock.send(message.dumpToDatagram());
        } catch (IOException e) {
            logger.fatal("Error durring sending multicas !", e);
            System.exit(1);
        }
    }

    public void handle(GreetingResponse response) {
        Model.Model().addClient(response.getClient());
    }

    private void handle(RoomCreation response) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void handle(Greeting response) {
        Model.Model().addClient(response.getClient());
    }

    public void sendMessage(String message) {
    }

    public void createRoom(Room room) {
        RoomCreation message = MessageFactory.createRoomCreationMessage(room);
        sendMulticast(message);
    }
}
