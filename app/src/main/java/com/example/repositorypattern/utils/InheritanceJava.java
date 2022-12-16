/*
package com.example.repositorypattern.utils;

import java.util.ArrayList;
import java.util.List;

public class InheritanceJava {
    void asdf() {
        //? extends Number means during runtime (i.e., actual instance creation)
        //it erases Number and only accepts the template from Integer as below
        //called covariance
        List<? extends Number> list1 = new ArrayList<Integer>(); //allowed, I have not provided type here, ? is like anything
        //to know ?, compiler considers all type that are subtype of Number although it has erased to Long, Int, Double etc.,
        //but still we have list of long, list of Double etc.
        List<Double> doubleList = (List<Double>) list1; //unchecked cast //possible

        //reading above is all good. But writing is not allowed, as it can change the template


        //List<Number> list2 = new ArrayList<Integer>(); //not allowed
        //why?

        ArrayList<Integer> integers = new ArrayList<Integer>();
        //List<Number> numbers = new ArrayList<Number>();
        //numbers.add(2.0);
        //numbers.add(2);

        List<Number> numbers = integers; //NOW ALLOWED
        //List<Number> numbers = integers; if allowed then that Number type would have been erased in run-time in Java
        //that's why inconvertible cast in below line
        List<Number> numbers = (List<Number>) integers; //INCONVERTIBLE CAST.

        numbers.add(5.0);
        //This means numbers is being consumed

        Integer integer = integers.get(0); //The call to 'get' always fails as an argument is out of bounds
    }

    */
/*Type Erasure
    //Java is strongly typed language. Generic provides API flexibility but it should know its type during compile time
    //This is where Type Erase thing must happen

    Generics were introduced to the Java language to provide tighter type checks at compile time and to support generic programming. To implement generics, the Java compiler applies type erasure to:

    Replace all type parameters in generic types with their bounds or Object if the type parameters are unbounded. The produced bytecode, therefore, contains only ordinary classes, interfaces, and methods.
    Insert type casts if necessary to preserve type safety.
    Generate bridge methods to preserve polymorphism in extended generic types.
    Type erasure ensures that no new classes are created for parameterized types; consequently, generics incur no runtime overhead.*//*




}
*/
