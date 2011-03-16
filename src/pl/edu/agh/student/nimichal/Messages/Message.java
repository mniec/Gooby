package pl.edu.agh.student.nimichal.Messages;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.BaseDataObject;
import pl.edu.agh.student.nimichal.DTO.Client;

import java.io.IOException;
import java.io.StringWriter;
import java.net.DatagramPacket;

import static java.lang.System.arraycopy;
import static java.lang.System.exit;
import static pl.edu.agh.student.nimichal.Settings.Settings;

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

    public byte[] serialize() {
        byte[] data = new byte[Settings().getPacketLength()];

        StringWriter stringWriter = new StringWriter(2000);
        YamlWriter writer = new YamlWriter(stringWriter);
        try {
            writer.write(this);
            writer.close();
        } catch (YamlException e) {
            logger.fatal("Error durring serialization to YAML!", e);
            exit(1);
        } catch (IOException e) {
            logger.fatal("Error durring serialization to YAML!", e);
            exit(1);
        }

        byte[] bytes = stringWriter.toString().getBytes();
        arraycopy(bytes, 0, data, 0, data.length > bytes.length ? bytes.length : data.length);
        return data;
    }

    public DatagramPacket dumpToDatagram() {

        byte[] data = serialize();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        return datagramPacket;
    }

    /* public T deserialize(DatagramPacket packet) {
        return new Message<T>();
    }*/




}
