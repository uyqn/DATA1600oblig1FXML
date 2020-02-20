package exceptions;

public class InvalidPersonException extends IllegalArgumentException {
    public InvalidPersonException(String msg){
        super(msg);
    }
}
