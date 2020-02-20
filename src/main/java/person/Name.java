package person;

import exceptions.InvalidNameException;

import java.io.Serializable;

public class Name implements Serializable {
    private String name;
    public static final String PATTERN =
            "[A-ZÆØÅa-zæøå ]+([A-ZÆØÅa-zæøå]\\. [A-ZÆØÅa-zæøå ]*)?";

    public Name(String name) throws InvalidNameException{
        if(!name.matches(PATTERN) || name.isBlank()){
            throw new InvalidNameException("Ugyldig navn!");
        }
        String trimmedName = name.trim().toLowerCase().replaceAll("\\s{2,}", "");
        String[] splitName = trimmedName.split(" ");

        StringBuilder finalName = new StringBuilder();

        for(String eachPart : splitName){
            finalName.append(eachPart.substring(0, 1).toUpperCase()).append(eachPart.substring(1)).append(" ");
        }

        this.name = finalName.toString().trim();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) throws InvalidNameException {
        if(!name.matches(PATTERN) || name.isBlank()){
            throw new InvalidNameException("Ugyldig navn!");
        }
        String trimmedName = name.trim().toLowerCase().replaceAll("\\s{2,}", "");
        String[] splitName = trimmedName.split(" ");

        StringBuilder finalName = new StringBuilder();

        for(String eachPart : splitName){
            finalName.append(eachPart.substring(0, 1).toUpperCase()).append(eachPart.substring(1)).append(" ");
        }

        this.name = finalName.toString().trim();
    }

    @Override
    public String toString() {
        return name;
    }
}
