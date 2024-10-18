package domaci2.example.services;
import domaci2.framework.annotations.*;

@Bean()
@Qualifier("ServiceA")
public class ServiceA implements ServiceInterface {

    @Autowired(verbose = true)
    private ComponentA componentA;

    public ServiceA() {
    }
}
