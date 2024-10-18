package domaci2.framework.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface Autowired {
    boolean verbose();//koji opisuje da li će se prilikom inicijalizacije anotiranog atributa u konzoli ispisati dodatne informacije o samom objektu
}
