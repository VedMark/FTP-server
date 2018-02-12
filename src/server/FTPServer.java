package server;

import view.LogWindow;
import view.Observer;

import java.util.ArrayList;

public class FTPServer implements Observable{
    private RequestController requestController;
    FTPServerPI serverPI;
    FTPServerDTP serverDTP;
    private ArrayList<Observer> views = new ArrayList<Observer>();

    public FTPServer() {
        LogWindow logWindow = new LogWindow();
        logWindow.initialize();
        addView(logWindow);
    }

    public void start() {

    }

    public void stop() {

    }

    public void addView(Observer view) {
        views.add(view);
    }

    public void removeView(Observer view) {
        views.remove(view);
    }

    public void notifyViews(ArrayList<Observer> observers) {
        for(Observer view : views) {
            view.notify();
        }
    }
}
