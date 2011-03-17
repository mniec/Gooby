package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

import pl.edu.agh.student.nimichal.Gooby.Model.Room;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 05:25
 */
public class LeaveRoom extends Message{
    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
