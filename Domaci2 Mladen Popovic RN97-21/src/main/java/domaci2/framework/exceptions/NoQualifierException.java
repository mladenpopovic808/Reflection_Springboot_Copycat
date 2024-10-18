package domaci2.framework.exceptions;

public class NoQualifierException extends Exception{
    public NoQualifierException() {
        super("There is no such qualifier in map");
    }
}
