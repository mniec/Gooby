package pl.edu.agh.student.nimichal.Gooby.Model;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.apache.log4j.Logger;
import pl.edu.agh.student.nimichal.Gooby.Settings;

import java.io.Serializable;
import java.io.StringWriter;

import static java.lang.System.arraycopy;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 00:10
 */
public class BaseDataObject implements Serializable {

    public BaseDataObject() {

    }

    public String toYAML() {
        StringWriter writer = new StringWriter();
        try {
            YamlWriter ywriter = new YamlWriter(writer);
            ywriter.write(this);
            ywriter.close();
        } catch (YamlException e) {
            Logger.getRootLogger().fatal("BaseObject could not serialze this to Yaml");
            System.exit(1);
        }
        return writer.toString();
    }

    public byte[] toBytes() {
        byte[] data = new byte[Settings.Settings().getPacketLength()];
        byte[] bytes = this.toYAML().getBytes();
        arraycopy(bytes, 0, data, 0, data.length > bytes.length ? bytes.length : data.length);
        return bytes;
    }
}
