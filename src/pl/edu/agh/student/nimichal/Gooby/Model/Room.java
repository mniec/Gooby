package pl.edu.agh.student.nimichal.Gooby.Model;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:51
 */
public class Room {
    private String name;
    transient private pl.edu.agh.student.nimichal.Model.Client[] clients;

    public pl.edu.agh.student.nimichal.Model.Client[] getClients() {
        return clients;
    }

    public void setClients(pl.edu.agh.student.nimichal.Model.Client[] clients) {
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
