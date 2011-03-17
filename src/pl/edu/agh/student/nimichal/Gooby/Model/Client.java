package pl.edu.agh.student.nimichal.Gooby.Model;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:52
 */
public class Client extends BaseDataObject {
    private String name;
    private String ipAddress;
    private Room[] rooms;
    private Room currentRoom;

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString(){
        return name+":"+ipAddress;
    }

    public static Client find(Client client){
        for(Client cl:Model.getModel().getClients()) {
            if(cl.equals(client))
                return cl;
        }
        return null;
    }
}
