package com.example.repositorypattern.utils

class DoubleSource<out T : Number> : Source<Double> {
    override fun nextT(): Double {
        return 5.0
    }

    //can't have any function that takes T type as parameter
    /*fun consumeValue(anyGenericOfSameType: T *//* not allowed *//*) {
        var genericVal2: T? = anyGenericOfSameType
    }*/

}

class IntSource : Source<Int> {
    override fun nextT(): Int {
        return 5
    }
}

//Source of T that goes out. Always emits only, never consumes
interface Source<out T> {
    fun nextT(): T
}

//what it can actually compare when implemented
class NumberComparable : Comparable<Number> {
    override fun compareTo(other: Number): Int {
        return when (other) {
            is Int -> other.compareTo(1)
            is Double -> other.compareTo(1.0)
            is Long -> other.compareTo(1L)
            else -> 100 //default value for now
        }
    }
}

//just compares whatever comes in
interface Comparable<in T> {
    fun compareTo(other: T): Int
}


fun main() {
    //in, Double (or Int) values in to the machine to compare them
    val y1: Comparable<Double> = NumberComparable()
    val y2: Comparable<Int> = NumberComparable()
    val y3: Comparable<Long> = NumberComparable()

    println(y1.compareTo(0.0))
    println(y2.compareTo(2))
    println(y3.compareTo(2L))

    //out, different machines to out Int and Double, but those machine can be safely
    //create from their parent typed machine

    //Any but Any is not like Object for Java although it is like Object
    //because of out variable Any is bound to be a type of Number
    val anySource: Source<Any> =
        DoubleSource<Number>() //safely create child class instance from parent class
    val intSource: Source<Any> = IntSource() //safely create child class instance from parent class

    anySource.nextT()
    intSource.nextT()

    println(anySource.nextT())
    println(intSource.nextT())
}
/*
Java has wildcards *. But Kotlin doesn't have.
Instead, Kotlin has declaration-site variance and type projections.

1) Use bounded wildcards to increase API flexibility. (no implementation just define in the Root class)
2) invariant: never changing

Function's parameter's type is invariant => That type of parameter is never changing. E.g.,
for any two distinct types Type1 and Type2, List<Type1> is neither a subtype
nor a supertype of List<Type2>. Although it is counterintuitive that List<String> is not
a subtype of List<Object>, it really does make sense. You can put any object into a
List<Object>, but you can put only strings into a List<String>.
Since a List<String> can’t do everything a List<Object> can,
it isn’t a subtype (by the Liskov substitution principal, Item 10).

Objects of a superclass shall be replaceable with objects of its subclasses
without breaking the application.

List<String> is not subtype of List<Object>, why?
Because while String extends Object, List<String> does not extend List<Object>
Update:
In general, if Foo is a subtype (subclass or sub-interface) of Bar,
and G is some generic type declaration, it is not the case that G<Foo> is
a subtype of G<Bar>.This is because collections do change.

What is the meaning of String extends Object
String class has a template (a surface area structure defined as private, public, protected, and default.
Similarly, Object class has a template. When String extends Object, String has to receive the public, protect, default
surface area template from Object.

When List<String> extends List<Objects>, what happens?
the template keeps changing when items added/deleted from list. Template is where we create instances.
Template should be invariant, never changing. Because this is like a template to contract a brick


The key to understanding why this works is rather simple: if you can only take items from a collection,
 then using a collection of Strings and reading Objects from it is fine. Conversely, if you can
 only put items into the collection, it's okay to take a collection of Objects and put Strings into it:
 in Java there is List<? super String>, a supertype of List<Object>. Called Contra-variance.

 */