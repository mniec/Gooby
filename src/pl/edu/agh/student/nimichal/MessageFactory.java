package pl.edu.agh.student.nimichal;

import pl.edu.agh.student.nimichal.DTO.Client;
import pl.edu.agh.student.nimichal.Messages.Greeting;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 21:38
 */
public class MessageFactory {

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
}
