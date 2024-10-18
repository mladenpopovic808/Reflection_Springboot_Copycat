package domaci2.framework.exceptions;

public class AutowiredException extends Exception{
    public AutowiredException() {
        super("Missing other annotations like @Bean,@Component or @Service");
    }
}
