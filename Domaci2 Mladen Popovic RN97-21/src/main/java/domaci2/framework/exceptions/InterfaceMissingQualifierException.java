package domaci2.framework.exceptions;

public class InterfaceMissingQualifierException extends Exception{
    public InterfaceMissingQualifierException() {
        super("Interface doesn't have qualifier annotation!");
    }
}
