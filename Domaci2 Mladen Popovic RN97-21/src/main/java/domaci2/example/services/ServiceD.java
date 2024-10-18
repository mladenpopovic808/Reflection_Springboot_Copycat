package domaci2.example.services;

import domaci2.framework.annotations.*;

@Bean()
@Qualifier("ServiceD")
public class ServiceD implements ServiceInterface {

    public ServiceD() {
    }
}