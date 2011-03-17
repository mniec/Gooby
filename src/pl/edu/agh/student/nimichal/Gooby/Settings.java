package pl.edu.agh.student.nimichal.Gooby;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static java.lang.System.exit;


/**
 * Author: Michal Niec
 * Date: 16.03.11
 * Time: 15:18
 */
public class Settings {

    static Logger logger = Logger.getLogger(Settings.class);

    private String name = "Unknown User";
    private String local_ip = "127.0.0.1";
    private String multicast_ip = "127.0.0.1";
    private int multicast_port = 6778;
    private int udp_port = 6779;
    private int tcp_port = 6880;
    private String logFile;
    private int packetLength;
    private int multicastTTL;
    private int timeToReceiveResponse;
    private int timeout;


    private static Settings instance;
    transient private static String filename = "config.yml";

    private Settings() {
    }

    public static void setConfigFile(String value){
        if(value != null){
            filename = value;
        }
    }

    public static synchronized Settings Settings() {
        try {
            if (instance == null) {
                YamlReader ymlReader = new YamlReader(new FileReader(filename));
                instance = ymlReader.read(Settings.class);
            }
        } catch (FileNotFoundException e) {
            logger.fatal("Error during reading config file", e);
        } catch (YamlException e) {
            logger.fatal("Error during parsing config file", e);
        } finally {
            if (instance == null) {
                exit(1);
            }
        }

        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLocalIP() {
        return local_ip;
    }

    public void setLocalIP(String local_ip) {
        this.local_ip = local_ip;
    }

    public int getMulticastPort() {
        return multicast_port;
    }

    public void setMulticastPort(int multicast_port) {
        this.multicast_port = multicast_port;
    }

    public String getMulticastIP() {
        return multicast_ip;
    }

    public void setMulticastIP(String multicast_ip) {
        this.multicast_ip = multicast_ip;
    }

    public int getTcpPort() {
        return tcp_port;
    }

    public void setTcpPort(int tcp_port) {
        this.tcp_port = tcp_port;
    }

    public int getUdpPort() {
        return udp_port;
    }

    public void setUdpPort(int udp_port) {
        this.udp_port = udp_port;
    }

    public String getLogConfig() {
        return logFile;
    }

    public void setLogConfig(String logFile) {
        this.logFile = logFile;
    }

    public int getPacketLength() {
        return packetLength;
    }

    public void setPacketLength(int value) {
        this.packetLength = value;
    }

    public int getMulticastTTL() {
        return multicastTTL;
    }

    public void setMulticastTTL(int multicastTTL) {
        this.multicastTTL = multicastTTL;
    }

    public int getTimeToReceiveResponse() {
        return timeToReceiveResponse;
    }

    public void setTimeToReceiveResponse(int timeToReceiveResponse) {
        this.timeToReceiveResponse = timeToReceiveResponse;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
