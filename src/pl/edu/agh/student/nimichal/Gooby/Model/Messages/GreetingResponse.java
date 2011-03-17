package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 23:51
 */
public class GreetingResponse extends Response {


    @Override
    public String toString(){
        return "Greeting response from "+ getClient().toString();
    }

}
