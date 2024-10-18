package domaci2.framework.dependencyInjection;

import domaci2.framework.annotations.Qualifier;
import domaci2.framework.exceptions.NoQualifierException;
import domaci2.framework.exceptions.QualifierAlreadyExistsException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DependencyContainer {

    public HashMap<String,Class>qualifierMap;

    public DependencyContainer(){
        qualifierMap=new HashMap<>();
    }

    public void registerImplementation(String qualifier,Class cl){
        qualifierMap.put(qualifier,cl);
    }



    public void mapQualifiers(){
        Set<Class>classes=getClassesInPackage("domaci2.example");//primeri sa kojima radimo

        for(Class cl:classes){
            Qualifier qualifier=(Qualifier) cl.getAnnotation(Qualifier.class);
            if(qualifier!=null){

                if(qualifierMap.containsKey(qualifier.value())){
                    try{
                        throw new QualifierAlreadyExistsException();
                    }catch (QualifierAlreadyExistsException e){
                        e.printStackTrace();
                    }
                }else{
                    this.registerImplementation(qualifier.value(),cl);
                }
            }
        }
    }

    public Class returnImplementation(String qualifier){
        try{
            if(qualifierMap.containsKey(qualifier)){
                return qualifierMap.get(qualifier);
            }else{
                throw new NoQualifierException();
            }
        }catch (NoQualifierException e){
            e.printStackTrace();
            return null;
        }
    }



    public Set<Class>getClassesInPackage(String packageName){
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));

    }





}
