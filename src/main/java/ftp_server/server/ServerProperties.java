package ftp_server.server;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ServerProperties {
    private static final String CONFIG_FILE = "config";

    private static final Long MIN_SHORT_VALUE = (long) 0;
    private static final Long MIN_UINT_VALUE = (long) 0;
    private static final Long MAX_SHORT_VALUE = (long) 65535;
    private static final Long MAX_UINT_VALUE = ((long) Integer.MAX_VALUE * 2 + 1);

    private static final String WRONG_PI_PORT_MESSAGE = "portPI is not a valid number";
    private static final String WRONG_DTP_PORT_MESSAGE = "portDTP is not a valid number";
    private static final String WRONG_CAPACITY_PORT_MESSAGE = "capacity is not a valid number";
    private static final String WRONG_TIMEOUT_PORT_MESSAGE = "timeout is not a valid number";

    private static final ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_FILE);

    public ServerProperties() {
    }

    public static Short getPortPI() throws ConfigException, MissingResourceException {
        return new Short(String.valueOf(convertValue(
                bundle.getString("ftp.portPI"),
                WRONG_PI_PORT_MESSAGE,
                MIN_SHORT_VALUE,
                MAX_SHORT_VALUE
        )));
    }

    public static Short getPortDTP() throws ConfigException, MissingResourceException {
        return new Short(String.valueOf(convertValue(
                bundle.getString("ftp.portDTP"),
                WRONG_DTP_PORT_MESSAGE,
                MIN_SHORT_VALUE,
                MAX_SHORT_VALUE
        )));
    }

    public static Integer getCapacity() throws ConfigException, MissingResourceException {
        return Math.toIntExact(convertValue(
                bundle.getString("ftp.capacity"),
                WRONG_CAPACITY_PORT_MESSAGE,
                MIN_SHORT_VALUE,
                MAX_SHORT_VALUE
        ));
    }

    public static Integer getTimeout() throws ConfigException, MissingResourceException {
        return Math.toIntExact(convertValue(
                bundle.getString("ftp.timeout"),
                WRONG_TIMEOUT_PORT_MESSAGE,
                MIN_UINT_VALUE,
                MAX_UINT_VALUE
        ));
    }

    public static String getHome(String user) throws MissingResourceException {
        return bundle.getString("ftp.home." + user);
    }

    public static String getPassword(String user) throws MissingResourceException {
        return bundle.getString("ftp.user." + user);
    }

    private static Long convertValue(String number, String wrongPortMessage, Long minValue, Long maxValue) throws ConfigException {
        Long value;
        try {
            value = Long.parseLong(number);
            if(value < minValue || value > maxValue) {
                throw new ConfigException(wrongPortMessage);
            }
        }
        catch (NumberFormatException exception) {
            throw new ConfigException(wrongPortMessage);
        }
        return value;
    }
}
