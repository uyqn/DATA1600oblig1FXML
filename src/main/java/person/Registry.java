package person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Registry implements Serializable {
    private transient static ObservableList<Person> registry = FXCollections.observableArrayList();

    public static ObservableList<Person> getRegistry() {
        return registry;
    }

    public static void add(Person person){
        registry.add(person);
    }

    public static void remove(Person person){
        registry.remove(person);
    }

    public static void replaceWith(ObservableList<Person> list){
        registry.setAll(list);
    }

    public static boolean personExists(Person person){
        for(Person member : registry){
            if(member.getEmail().equals(person.getEmail()) || member.getNumber().equals(person.getNumber())){
                return true;
            }
        }
        return false;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        List<Person> list = (List<Person>) s.readObject();
        registry = FXCollections.observableList(list);
    }
}
