package pl.edu.agh.student.nimichal.Gooby.Model;

import pl.edu.agh.student.nimichal.Gooby.ChatListener;
import pl.edu.agh.student.nimichal.Gooby.Settings;

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
    private Collection<StringMessage> messages = new ArrayList<StringMessage>();

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
        updateGUI();
    }

    public void addRoom(Room room) {
        room.setClients(new ArrayList<Client>());
        this.rooms.add(room);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Collection<StringMessage> getMessages() {
        return messages;
    }

    public void setMessages(Collection<StringMessage> messages) {
        this.messages = messages;
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
            thisClient.setUDPPort(Settings.Settings().getUdpPort());

        }
        return thisClient;
    }


    //listeners
    public void addChatListener(ChatListener listener){
        this.listeners.add(listener);
    }


    public void updateGUI() {
        for(ChatListener list:listeners){
            list.roomsChanged();
        }
    }

    public void addMessage(StringMessage msg) {
        getMessages().add(msg);
        for(ChatListener list:listeners){
            list.messageArrived(msg);
        }
    }
}
