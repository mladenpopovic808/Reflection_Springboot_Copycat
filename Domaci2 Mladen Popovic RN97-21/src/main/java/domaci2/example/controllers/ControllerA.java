package domaci2.example.controllers;


import domaci2.example.services.ServiceA;
import domaci2.example.services.ServiceB;
import domaci2.example.services.ServiceInterface;
import domaci2.framework.annotations.*;

import java.util.HashMap;


@Controller
public class ControllerA {

    @Autowired(verbose = true)
    private ServiceA serviceA;

    @Autowired(verbose = true)
    private ServiceB serviceB;

    @Autowired(verbose = true)
    @Qualifier("ServiceC")
    private ServiceInterface serviceC;

    @Path(path = "/methodA1")
    @Get
    public HashMap<String,String> methodA1(){
        System.out.println("Pokrenuta metoda : /methodA1(GET)");
        HashMap<String,String>map=new HashMap<>();
        map.put("podatak1","podatak1 iz /methodA1");
        map.put("podatak2","podatak2 iz /methodA1");
        return map;

    }

    @Path(path = "/methodA2")
    @Post
    public HashMap<String,String> methodA2(){
        System.out.println("Pokrenuta metoda : /methodA2 (POST)");
        HashMap<String,String>map=new HashMap<>();
        map.put("p1","podatak1 iz /methodA2");
        map.put("p2","podatak2 iz /methodA2");
        return map;
    }
}
