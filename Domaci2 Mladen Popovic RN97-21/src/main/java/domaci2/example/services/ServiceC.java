package domaci2.example.services;

import domaci2.framework.annotations.*;

@Service
@Qualifier("ServiceC")
public class ServiceC implements ServiceInterface {

    @Autowired(verbose = true)
    @Qualifier("ServiceD")
    private ServiceInterface serviceD;

    public ServiceC() {
    }
}
