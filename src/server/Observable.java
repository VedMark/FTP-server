package server;

import view.Observer;

import java.util.ArrayList;

interface Observable {
    void addView(Observer view);
    void removeView(Observer view);
    void notifyViews(ArrayList<Observer> observers);
}
