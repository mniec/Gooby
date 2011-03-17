package pl.edu.agh.student.nimichal.Gooby.Model;

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
}
