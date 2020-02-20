package exceptions;

public class InvalidEmailException extends IllegalArgumentException{
    public InvalidEmailException(String msg){
        super(msg);
    }
}
