package domaci2.example.services;

import domaci2.framework.annotations.*;

@Service
@Qualifier("ServiceB")
public class ServiceB implements ServiceInterface {

    @Autowired(verbose = true)
    private ComponentB componentB;

    public ServiceB() {
    }
}
