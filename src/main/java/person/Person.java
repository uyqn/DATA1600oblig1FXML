package person;

import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {
    private transient SimpleObjectProperty<Name> name;
    private transient SimpleObjectProperty<Birthdate> birthdate;
    private transient SimpleObjectProperty<Email> email;
    private transient SimpleObjectProperty<PhoneNumber> number;

    private static String DELIMETER = ",";

    public Person(){}

    public Person(String name, String birthdate, String email, String number){
        this.name = new SimpleObjectProperty<>(new Name(name));
        this.birthdate = new SimpleObjectProperty<>(new Birthdate(birthdate));
        this.email = new SimpleObjectProperty<>(new Email(email));
        this.number = new SimpleObjectProperty<>(new PhoneNumber(number));
    }

    public Person(Name name, Birthdate birthdate, Email email, PhoneNumber number){
        this.name = new SimpleObjectProperty<>(name);
        this.birthdate = new SimpleObjectProperty<>(birthdate);
        this.email = new SimpleObjectProperty<>(email);
        this.number = new SimpleObjectProperty<>(number);
    }

    public String getName() {
        return name.getValue().getName();
    }
    public void setName(String name) {
        this.name.getValue().setName(name);
    }

    public String getBirthdate() {
        return birthdate.getValue().getBirthdate();
    }
    public void setBirthdate(String birthdate) {
        this.birthdate.getValue().setBirthdate(birthdate);
    }

    public void setAge(int age){
        this.birthdate.getValue().setAge(age);
    }
    public int getAge(){
        return birthdate.getValue().getAge();
    }

    public String getEmail() {
        return email.getValue().getEmail();
    }
    public void setEmail(String email) {
        this.email.getValue().setEmail(email);
    }

    public String getNumber() {
        return number.getValue().getNumber();
    }
    public void setNumber(String number) {
        this.number.getValue().setNumber(number);
    }

    public static String getDELIMETER() {
        return DELIMETER;
    }

    public String csvString(){
        return name.getValue() + DELIMETER +
                getAge() + DELIMETER +
                birthdate.getValue().getBirthdate() + DELIMETER +
                email.getValue() + DELIMETER +
                number.getValue();
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(name.getValue());
        outputStream.writeObject(birthdate.getValue());
        outputStream.writeObject(email.getValue());
        outputStream.writeObject(number.getValue());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Name name = (Name) inputStream.readObject();
        Birthdate birthdate = (Birthdate) inputStream.readObject();
        Email email = (Email) inputStream.readObject();
        PhoneNumber number = (PhoneNumber) inputStream.readObject();

        this.name = new SimpleObjectProperty<>(name);
        this.birthdate = new SimpleObjectProperty<>(birthdate);
        this.email = new SimpleObjectProperty<>(email);
        this.number = new SimpleObjectProperty<>(number);
    }

    @Override
    public String toString() {
        return "Navn: " + name.getValue() + "\n" +
                "Alder: " + getAge() + " år\n" +
                "Fødselsdag: " + birthdate.getValue().toString() + "\n" +
                "Epost: " + email.getValue() + "\n" +
                "Telefon: " + number.getValue();
    }


}
