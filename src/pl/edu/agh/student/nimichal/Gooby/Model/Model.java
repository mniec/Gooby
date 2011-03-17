package pl.edu.agh.student.nimichal.Gooby.Model;

import pl.edu.agh.student.nimichal.Gooby.ChatListener;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;
import pl.edu.agh.student.nimichal.Gooby.Model.Room;
import pl.edu.agh.student.nimichal.Gooby.Settings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:55
 */
public class Model {
    private static Model instance;

    private Collection<Client> clients = new ArrayList<Client>();
    private Collection<Room> rooms = new ArrayList<Room>();
    private Collection<ChatListener> listeners = new ArrayList<ChatListener>();
    private Room currentRoom;
    private Client thisClient;

    private Model() {
    }

    public static synchronized Model getModel() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;

    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {

        for(Room room:client.getRooms()){
            if(!rooms.contains(room)){
                addRoom(room);
            }
        }
        client.setRooms(null);
        getClients().add(client);
        for(ChatListener list:listeners){
            list.clientsChanged();
        }
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        for(ChatListener list:listeners){
            list.roomsChanged();
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Client getThisClient() {
        if(thisClient==null){
            thisClient = new Client();
            thisClient.setIpAddress(Settings.Settings().getLocalIP());
            thisClient.setName(Settings.Settings().getName());
            thisClient.setCurrentRoom(currentRoom);
            thisClient.setRooms(rooms);
        }
        return thisClient;
    }


    //listeners
    public void addChatListener(ChatListener listener){
        this.listeners.add(listener);
    }




}
