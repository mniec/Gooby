package pl.edu.agh.student.nimichal.Gooby.Model.Messages;

import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Model.BaseDataObject;
import pl.edu.agh.student.nimichal.Gooby.Model.Client;

import java.net.DatagramPacket;

/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 21:38
 */
public abstract class Message<T> extends BaseDataObject {
    transient static Logger logger = Logger.getRootLogger();

    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public DatagramPacket dumpToDatagram() {

        byte[] data = this.toBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        return datagramPacket;
    }






}
