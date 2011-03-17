package pl.edu.agh.student.nimichal.Gooby.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:51
 */
public class Room {
    private String name;
    transient private Collection<Client> clients = new ArrayList<Client>();

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (name != null ? !name.equals(room.name) : room.name != null) return false;

        return true;
    }

    public static Room find(Room room){
           for(Room ro:Model.getModel().getRooms()) {
               if(ro.equals(room))
                   return ro;
           }
           return null;
       }


    public void addClient(Client client) {
        this.getClients().add(client);
    }
}
