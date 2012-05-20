package generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericsTest {


    public static void main(String[] args) {
        List<Number> numberList = new ArrayList<Number>();
        List<Integer> intList = new ArrayList<Integer>();
        
        numberList.add(6);
        numberList.add(7l);
        numberList.add(new Float(6.7));

        Collection<?> objs = new ArrayList<String>();
        for (Object obj: objs )
            System.out.println(obj.toString());
        //objs.add("text");       // ошибка времени компиляции
        //objs.add(new Object()); // ошибка времени компиляции

        System.out.println(numberList);

//        List<String>[] lsa = new List<String>[10]; // не верно
//        Object[] oa = lsa;  // OK поскольку List<String> является подтипом Object
//        List<Integer> li = new ArrayList<Integer>();
//        li.add(new Integer(3));
//        oa[0] = li;
//        String s = lsa[0].get(0);
    }
    
    
}
interface Interface {
    //void test(Class<Object> clazz);
}

abstract class BaseClass<T> implements Interface {
    abstract public void test(Class<Object> clazz);
}

class MyClass extends BaseClass<List> {
    public void test(Class<Object> clazz) {
    }
}
/*Java 2, v5.0 (Tiger) New Features
        by Herbert Schildt
        ISBN: 0072258543
        Publisher: McGraw-Hill/Osborne, 2004
        */
class Gen<T> {
    T ob; // declare an object of type T

    // Pass the constructor a reference to
    // an object of type T.
    Gen(T o) {
        ob = o;
    }

    // Return ob.
    T getob() {
        System.out.print("Gen's getob(): " );
        return ob;
    }
}

// A subclass of Gen that overrides getob().
class Gen2<T> extends Gen<T> {

    Gen2(T o) {
        super(o);
    }

    // Override getob().
    T getob() {
        System.out.print("Gen2's getob(): ");
        return ob;
    }
}

// Demonstrate generic method override.
class OverrideDemo {
    public static void main(String args[]) {

        // Create a Gen object for Integers.
        Gen<Integer> iOb = new Gen<Integer>(88);

        // Create a Gen2 object for Integers.
        Gen2<Integer> iOb2 = new Gen2<Integer>(99);

        // Create a Gen2 object for Strings.
        Gen2<String> strOb2 = new Gen2<String>("Generics Test");

        System.out.println(iOb.getob());
        System.out.println(iOb2.getob());
        System.out.println(strOb2.getob());
    }
}
