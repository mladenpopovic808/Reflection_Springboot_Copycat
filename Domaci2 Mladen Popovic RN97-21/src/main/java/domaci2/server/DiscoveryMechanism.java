package domaci2.server;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domaci2.framework.annotations.Controller;
import domaci2.framework.annotations.Get;
import domaci2.framework.annotations.Path;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;


public class DiscoveryMechanism {

    private static DiscoveryMechanism instance; //singleton

    private List<HttpRoute>controllerHttpRoutes; //namapirane rute iz svih kontrolera


    private Set<Class> getAllClasses(String packageName){ //pronalazi sve klase u paketu
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }
    public static DiscoveryMechanism getInstance(){
        if(instance==null){
            instance=new DiscoveryMechanism();
            instance.controllerHttpRoutes=new ArrayList<>();
            instance.mapRoutes();//prodje kroz sve klase i uzme sve rute iz controllera
        }
        return instance;
    }
    private DiscoveryMechanism(){

    }

    public List<HttpRoute> getControllerHttpRoutes() {
        return controllerHttpRoutes;
    }
    public void printRoutes() {
        System.out.println("Pronadjene su sledece rute:");
        for (HttpRoute route : controllerHttpRoutes) {
            System.out.println(route);
        }
        System.out.println(".");
    }

    private void mapRoutes(){
        Set<Class>classes=instance.getAllClasses("domaci2.example");//primeri sa kojima radimo

        for(Class cl:classes){
            if(cl.isAnnotationPresent(Controller.class)){
                Method[]methods=cl.getMethods();

                for(int i=0;i< methods.length;i++){
                    Method method=methods[i];
                    String httpMethod="";

                    if(method.isAnnotationPresent(Path.class)){
                        Path p = method.getAnnotation(Path.class);
                        if(method.isAnnotationPresent(Get.class)){
                            httpMethod="GET";
                        }else{
                            httpMethod="POST";
                        }
                        HttpRoute route=new HttpRoute(httpMethod,method.toString(),p.path(),cl);
                        instance.controllerHttpRoutes.add(route);
                    }
                }
            }
        }
    }
}
