package com.ursalabs.telnetscrapper;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CheckPorts implements Runnable {

    private String ip;
    private int startPort;
    private int endPort;
    private List<Integer> openPorts = new ArrayList<Integer>();
    private Status currentStatus;

    enum Status {
        INITIALIZED, RUNNING, FINISHED;
    }

    public CheckPorts(String ip, int startPort, int endPort) {
        this.ip = ip;
        this.endPort = endPort;
        this.startPort = startPort;
        this.currentStatus = Status.INITIALIZED;
    }

    @Override
    public void run() {
        this.currentStatus = Status.RUNNING;

        for (int port = startPort; port < endPort; port++) {
            System.out.print("Checking port " + port + ": ");
            if (deviceListening(ip, port)) {
                openPorts.add(port);
                System.out.println("OPEN!!!");
            } else {
                System.out.println("Closed");
            }
        }

        this.currentStatus = Status.FINISHED;
    }

    /**
     * Checks if a device is listening to a given port
     *
     * @param host the IP address of the device being checked
     * @param port the port to check
     * @return True if the host is listening to the port
     */
    private static boolean deviceListening(String host, int port) {
        Socket s = null;
        try {
            s = new Socket(host, port);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (Exception e) {
                }
        }
    }

    public int getEndPort() {
        return endPort;
    }

    public int getStartPort() {
        return startPort;
    }

    public List<Integer> getOpenPorts() {
        return openPorts;
    }

    public void setEndPort(int endPort) {
        this.endPort = endPort;
    }

    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }
}