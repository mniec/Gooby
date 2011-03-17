package pl.edu.agh.student.nimichal.Gooby;

import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Model.Model.Messages.Greeting;
import pl.edu.agh.student.nimichal.Model.Model.Messages.GreetingResponse;
import pl.edu.agh.student.nimichal.Model.Model;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

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
    private DatagramChannel udpChannel;
    private Socket tcpSock;

    private ByteBuffer buffer;

    public Loop() {
        try {
            multiAddr = InetAddress.getByName(Settings().getMulticastIP());
            multiSock = new MulticastSocket(Settings().getMulticastPort());

            udpSock = new DatagramSocket(Settings().getUdpPort());

            udpChannel = DatagramChannel.open();
            udpChannel.configureBlocking(false);

            buffer = ByteBuffer.allocate(Settings().getPacketLength());
            buffer.flip();

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

            DatagramPacket packet = message.dumpToDatagram();
            packet.setAddress(multiAddr);
            multiSock.send(packet);

            logger.info("Greeting sent :" + message.toYAML());

            long start = System.currentTimeMillis();

            while (System.currentTimeMillis() - start > Settings().getTimeToReceiveResponse()) {
                SocketAddress sa = udpChannel.receive(buffer);
                if (sa == null) {
                    Thread.sleep(10);
                } else {
                    GreetingResponse response = MessageFactory.formByte(buffer.array(),buffer.limit());
                    handle(response);
                }
            }

        } catch (IOException e) {
            logger.fatal("Cannot create multicast socket", e);
            System.exit(1);
        } catch (InterruptedException e) {
            logger.fatal("Error durring sleep !", e);
            System.exit(1);
        }
    }

    public void handle(GreetingResponse response) {
        Model.Model().addClient(response.getClient());
    }
}
