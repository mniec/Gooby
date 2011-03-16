package pl.edu.agh.student.nimichal;

import pl.edu.agh.student.nimichal.DTO.Client;
import pl.edu.agh.student.nimichal.DTO.Room;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:55
 */
public class Model {
    private Model instance;

    private Client[] clients;
    private Room[] rooms;

    private Model() {
    }

    public synchronized Model Model() {
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

    public Client[] getClients() {
        return clients;
    }

    public void setClients(Client[] clients) {
        this.clients = clients;
    }
}
