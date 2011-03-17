package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 05:19
 */
public class AckMessage extends Message {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
