package pl.edu.agh.student.nimichal.Gooby;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.Greeting;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.GreetingResponse;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.RoomCreation;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;

import java.io.StringReader;
import java.net.DatagramPacket;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 21:38
 */
public class MessageFactory {

    static Logger logger = Logger.getLogger(MessageFactory.class);

    public static Greeting crateGreetingMsg() {
        Greeting greet = new Greeting();
        greet.setClient(getLocalClient());

        return greet;
    }

    public static Client getLocalClient() {
        Client client = new Client();
        client.setIpAddress(Settings.Settings().getLocalIP());
        client.setName(Settings.Settings().getName());

        return client;
    }

     public static RoomCreation createRoomCreationMessage(Room room) {
        RoomCreation roomCreation = new RoomCreation();
        roomCreation.setRoom(room);

        return roomCreation;
    }

    public static GreetingResponse creteGreetingResponse(){
        GreetingResponse response = new GreetingResponse();
        response.setClient(getLocalClient());

        return  response;

    }

    public static <T> T formByte(byte[] data, int len) {
        YamlReader yreader = new YamlReader(new StringReader(new String(data)));
        T obj = null;
        try {
            obj =  (T)yreader.read();
        } catch (YamlException e) {
            Logger.getRootLogger().fatal("Error durring !", e);
            System.exit(1);
        }
        return obj;
    }

    public static DatagramPacket getPacket(){
        return new DatagramPacket(new byte[Settings.Settings().getPacketLength()],Settings.Settings().getPacketLength());
    }
}
