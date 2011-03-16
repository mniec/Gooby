package pl.edu.agh.student.nimichal;

import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Messages.Greeting;

import java.io.IOException;
import java.net.*;

import static pl.edu.agh.student.nimichal.Settings.Settings;

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

    public Loop() {
        try {
            multiAddr = InetAddress.getByName(Settings().getMulticastIP());
            multiSock = new MulticastSocket(Settings().getMulticastPort());

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        }
    }

    public void start() {
        try {
            multiSock.joinGroup(multiAddr);
            multiSock.setTimeToLive(Settings.Settings().getMulticastTTL());

            Greeting message = MessageFactory.crateGreetingMsg();

            logger.debug("Greeting message created: "+ message.toString());

            DatagramPacket packet = message.dumpToDatagram();
            packet.setAddress(multiAddr);
            multiSock.send(packet);

            logger.info("Greeting sent :" + message.toString());

            while (true) {

            }

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        }


    }
}
