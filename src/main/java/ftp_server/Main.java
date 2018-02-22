package ftp_server;

import ftp_server.view.FXServerApp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Main {
    private static void printAddresses() throws SocketException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                System.out.println(i.getHostAddress());
            }
        }
    }

    public static void main(String[] args) {
        // TODO: remove later
        try {
            printAddresses();
        } catch (SocketException e) {
        }

        FXServerApp.start();
    }
}
