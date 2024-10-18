package domaci2.framework.annotations;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@Inherited
public @interface Qualifier {

    String value();//Kada anotira klasu, predstavlja jedinstvenu vrednost po kojoj ćemo prepoznati
                    // ovu implementaciju. Vrednost registrovati u Dependency Container-u.
}
