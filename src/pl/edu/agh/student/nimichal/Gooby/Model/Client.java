package pl.edu.agh.student.nimichal.Gooby.Model;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:52
 */
public class Client extends pl.edu.agh.student.nimichal.Model.BaseDataObject {
    private String name;
    private String ipAddress;
    private pl.edu.agh.student.nimichal.Model.Room[] rooms;

    public pl.edu.agh.student.nimichal.Model.Room[] getRooms() {
        return rooms;
    }

    public void setRooms(pl.edu.agh.student.nimichal.Model.Room[] rooms) {
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

    @Override
    public String toString(){
        return name+":"+ipAddress;
    }
}