package pl.edu.agh.student.nimichal.Gooby.Model;

import pl.edu.agh.student.nimichal.Gooby.ChatListener;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;

import java.util.Collection;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:55
 */
public class Model {
    private static Model instance;

    private Collection<Client> clients;
    private Room[] rooms;
    private Collection<ChatListener> listeners;

    private Model() {
    }

    public static synchronized Model Model() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;

    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        getClients().add(client);

    }

    //listeners
    public void addChatListener(ChatListener listener){
        this.listeners.add(listener);
    }
}
