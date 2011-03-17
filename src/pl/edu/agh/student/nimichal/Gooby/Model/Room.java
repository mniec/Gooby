package pl.edu.agh.student.nimichal.Gooby.Model;

import java.util.Arrays;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:51
 */
public class Room {
    private String name;
    transient private Client[] clients;

    public Client[] getClients() {
        return clients;
    }

    public void setClients(Client[] clients) {
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
        return name+"("+clients.length+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (name != null ? !name.equals(room.name) : room.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (clients != null ? Arrays.hashCode(clients) : 0);
        return result;
    }

    public static Room find(Room room){
           for(Room ro:Model.getModel().getRooms()) {
               if(ro.equals(room))
                   return ro;
           }
           return null;
       }

}
