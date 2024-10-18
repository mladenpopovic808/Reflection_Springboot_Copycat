package domaci2.example.controllers;

import domaci2.example.services.ServiceA;
import domaci2.example.services.ServiceB;
import domaci2.example.services.ServiceInterface;
import domaci2.framework.annotations.*;

@Controller
public class ControllerB {

    @Autowired(verbose = true)
    private ServiceA serviceA;

    @Autowired(verbose = true)
    private ServiceB serviceB;

    @Autowired(verbose = true)
    @Qualifier("ServiceC")
    private ServiceInterface serviceC;

    @Path(path = "/methodB1")
    @Get
    public void methodB1(){}

    @Path(path = "/methodB2")
    @Post
    public void methodB2(){}
}
