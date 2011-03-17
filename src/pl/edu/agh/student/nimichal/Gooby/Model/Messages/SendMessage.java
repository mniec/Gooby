package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 05:18
 */
public class SendMessage extends Message{
    private long id;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
