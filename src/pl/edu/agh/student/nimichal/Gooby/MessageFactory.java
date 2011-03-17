package pl.edu.agh.student.nimichal.Gooby;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.*;
import pl.edu.agh.student.nimichal.Gooby.Model.Model;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;
import pl.edu.agh.student.nimichal.Gooby.Model.StringMessage;

import java.io.StringReader;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static pl.edu.agh.student.nimichal.Gooby.Model.Model.getModel;

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
        return getModel().getThisClient();
    }

     public static RoomCreation createRoomCreationMessage(Room room) {
        RoomCreation roomCreation = new RoomCreation();
        roomCreation.setRoom(room);
        roomCreation.setClient(getLocalClient());

        return roomCreation;
    }

    public static GreetingResponse creteGreetingResponse(){
        GreetingResponse response = new GreetingResponse();
        response.setClient(getLocalClient());

        return  response;

    }

    public static JoinRoom createJoinRoomMsg(Room room) {
        JoinRoom msg = new JoinRoom();
        msg.setRoom(room);
        msg.setClient(getLocalClient());
        return msg;
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

    public static StringMessage createSendMessage(String message) {
        StringMessage msg = new StringMessage();
        msg.setClient(getLocalClient());
        msg.setId(new Random().nextInt());
        msg.setTime(System.currentTimeMillis());
        msg.setRoom(Model.getModel().getCurrentRoom());
        msg.setText(message);

        for(Client client:msg.getRoom().getClients()){
            msg.getRecipientsLeft().add(client);
        }
        return msg;
    }
}
