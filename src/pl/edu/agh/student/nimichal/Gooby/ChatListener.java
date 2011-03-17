package pl.edu.agh.student.nimichal.Gooby;

import pl.edu.agh.student.nimichal.Gooby.Model.StringMessage;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 01:35
 */
public interface ChatListener {

    public void clientsChanged();
    public void roomsChanged();
    public void roomCreated();
    public void messageArrived(StringMessage msg);

}
