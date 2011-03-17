package pl.edu.agh.student.nimichal.Gooby.Model;

import pl.edu.agh.student.nimichal.Gooby.Model.Messages.AckMessage;
import pl.edu.agh.student.nimichal.Gooby.Model.Messages.Message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 07:33
 */
public class StringMessage extends Message{
    private int id;
    private Room room;
    private String text;
    private long time;
    private Collection<Client> recipientsLeft = new ArrayList<Client>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Client> getRecipientsLeft() {
        return recipientsLeft;
    }

    public void setRecipientsLeft(Collection<Client> recipientsLeft) {
        this.recipientsLeft = recipientsLeft;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static StringMessage find(StringMessage smsg){
        for(StringMessage msg:Model.getModel().getMessages()){
            if(msg.equals(smsg))
                return msg;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringMessage that = (StringMessage) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString(){
        return "Message from "+getClient().toString()+": "+ getText();
    }
}
