package domaci2.framework.dependencyInjection;

import domaci2.framework.annotations.*;
import domaci2.framework.exceptions.AutowiredException;
import domaci2.framework.exceptions.InterfaceMissingQualifierException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DIEngine {

    private static DIEngine instance;
    public DependencyContainer dependencyContainer;
    public HashMap<String,Object> singletonsMap; //ime klase:klasa

    private DIEngine(){

    }
    public static DIEngine getInstance(){
        if(instance==null){
            instance=new DIEngine();
            instance.singletonsMap=new HashMap<>();
            instance.dependencyContainer=new DependencyContainer();
            instance.dependencyContainer.mapQualifiers();//odmah mapiramo sve qualifiere
        }
        return instance;
    }

    public static Object returnClassInstance(Class cl) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException{
        //ako postoji vrati,ako nema napravi i vrati

        if(instance.singletonsMap.containsKey(cl.getName())){
            return instance.singletonsMap.get(cl.getName());

        }else{
            Constructor constructor=cl.getDeclaredConstructor();
            Object obj=constructor.newInstance();
            instance.singletonsMap.put(obj.getClass().getName(),obj);
            return obj;
        }
    }


    public void initDependencies(String controllerName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException{
        Class cl = Class.forName(controllerName);//uzmemo controller
        Object controllerObject=instance.returnClassInstance(cl);//Ako nema u listi,ubacice ga i vratice ga

        Field[] controllerFields=cl.getDeclaredFields();
        initController(controllerObject,controllerFields);//iniciramo sva polja controllera
        System.out.println("....");
    }

    //rekurzivno
    public void initController(Object object,Field[]fields) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{

        for(Field childField:fields){

            if(childField.isAnnotationPresent(Autowired.class)){

                Object childObject=null;//instanca klase
                Class childClass=null; //klasa objekta koji ce da se ubaci na mesto fielda
                Constructor constructor;

                if(childField.getType().isInterface()){//ako je interface gledamo da li ima qualifier, i ako nema bacamo exception

                    Qualifier qualifier=childField.getAnnotation(Qualifier.class);

                    if(qualifier!=null){
                        childClass=instance.dependencyContainer.returnImplementation(qualifier.value());
                    }else {
                        try{
                            throw new InterfaceMissingQualifierException();
                        }catch (InterfaceMissingQualifierException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    String[]str=childField.toString().split(" ");//ako nije interface uzmemo ime polja
                    childClass=Class.forName(str[1]);

                }
                constructor= childClass.getDeclaredConstructor();

                if(childClass.isAnnotationPresent(Bean.class)){
                    Bean bean=(Bean)childClass.getAnnotation(Bean.class);

                    if(bean.scope().equalsIgnoreCase("singleton")){//ako je singleton vracamo postojecu
                        childObject=instance.returnClassInstance(childClass);
                    }else if(bean.scope().equalsIgnoreCase("prototype")) {//ako je prototype,vracamo novu instancu uvek
                        childObject=constructor.newInstance();
                    }
                }else if(childClass.isAnnotationPresent(Service.class)){ //singleton
                    childObject = instance.returnClassInstance(childClass);

                }else if(childClass.isAnnotationPresent(Component.class)){//prototype
                    childObject = constructor.newInstance();

                }else{
                    try{
                        throw new AutowiredException(); //autowired a nije bean,to ne moze
                    }catch (AutowiredException e){
                        e.printStackTrace();
                    }
                }

                initController(childObject, childClass.getDeclaredFields());//prvo rekurzija po dubini
                childField.setAccessible(true);//pa tek onda setovanje ovog polja
                childField.set(object,childObject);

                //ako je @Autowired polje verbose true,onda se ispisuju dodatne informacije
                checkVerboseAndPrint(childField,object,childObject);
            }
        }

    }
    private void checkVerboseAndPrint(Field fieldToBeInserted,Object parentObj,Object insertedObject){
        Autowired autowired=fieldToBeInserted.getAnnotation(Autowired.class);
        if(autowired.verbose()){
            System.out.println("VerboseOutput: Initialized "+fieldToBeInserted.getType() +" ("+fieldToBeInserted.getName()+ ") in "+parentObj.getClass().getName()+
            " on "+ LocalDateTime.now() + " with "+insertedObject.hashCode());
        }
        //“Initialized <param.object.type> <param.name> in <param.parentClass.name> on <localDateTime.now()> with <param.instance.hashCode>”


    }




}
