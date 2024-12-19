

import java.util.ArrayList;
import java.util.List;

// Observable sınıfı (Özel isimli)
abstract class CustomObservable {
    private List<CustomObserver> observers = new ArrayList<>();

    public void addObserver(CustomObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CustomObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (CustomObserver observer : observers) {
            observer.update(message);
        }
    }
}
