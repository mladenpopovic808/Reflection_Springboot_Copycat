package domaci2.example.services;

import domaci2.framework.annotations.*;

@Component
@Qualifier("ComponentB")
public class ComponentB implements ComponentInterface {

    @Autowired(verbose = true)
    @Qualifier("ComponentC")
    private ComponentInterface componentC;

    public ComponentB() {
    }
}
