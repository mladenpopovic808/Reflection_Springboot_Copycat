package domaci2.example.services;

import domaci2.framework.annotations.*;

@Bean(scope = "prototype")
@Qualifier("ComponentA")
public class ComponentA implements ComponentInterface {

    @Autowired(verbose = true)
    private ComponentC componentC;

    public ComponentA() {
    }
}
