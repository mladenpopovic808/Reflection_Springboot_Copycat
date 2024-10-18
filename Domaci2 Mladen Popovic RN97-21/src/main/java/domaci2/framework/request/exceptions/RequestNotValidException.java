package domaci2.framework.request.exceptions;

public class RequestNotValidException extends Exception {

    public RequestNotValidException(String command) {
        super("Client request is invalid. Requested command: " + command);
    }

}
