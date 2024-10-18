package domaci2.framework.annotations;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Bean {

    //ako se ne navede vrednost kada se anotira klasa,bice singleton
    String scope() default "singleton";
}
