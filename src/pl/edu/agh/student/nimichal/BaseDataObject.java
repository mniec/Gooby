package pl.edu.agh.student.nimichal;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * Author: Michal Niec
 * Date: 17.03.11
 * Time: 00:10
 */
public class BaseDataObject implements Serializable{
    @Override
    public String toString() {
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
}
