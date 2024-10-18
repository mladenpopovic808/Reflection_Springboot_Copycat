package domaci2.example.services;


import domaci2.framework.annotations.Autowired;
import domaci2.framework.annotations.Component;
import domaci2.framework.annotations.Qualifier;

@Component
@Qualifier("ComponentC")
public class ComponentC implements ComponentInterface {

    @Autowired(verbose = true)
    @Qualifier("ServiceC")
    private ServiceInterface serviceC;

    public ComponentC() {
    }
}
