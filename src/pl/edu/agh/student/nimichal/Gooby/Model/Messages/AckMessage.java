package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

import pl.edu.agh.student.nimichal.Gooby.Model.StringMessage;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 05:19
 */
public class AckMessage extends Message {
    private StringMessage message;

    public StringMessage getMessage() {
        return message;
    }

    public void setMessage(StringMessage message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Ack from " + message.getClient().toString() + " for message: " + message.getText();
    }
}
