package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

import pl.edu.agh.student.nimichal.Gooby.Model.Room;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 21:40
 */
public class Greeting extends Message<Greeting> {

    @Override
    public String toString(){
        return "Greeting from "+ getClient().toString();
    }
}
