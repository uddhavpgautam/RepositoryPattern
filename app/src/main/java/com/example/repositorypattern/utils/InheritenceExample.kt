package com.example.repositorypattern.utils


interface ParentInterface1 {
    private val privVal: Int
        get() = 1
    val pubVal1: Int
        get() = 4
    val pubVal2: Int
}

abstract class ParentInterface2 {
    private val privVal1: Int
        get() = 1

    /* private abstract val privVal2: Int. private can't be abstract */
    val pubVal1: Int
        get() = 4
    abstract val pubVal2: Int
}


class ChildClass1 : ParentInterface1 {
    override val pubVal2: Int
        get() = TODO("Not yet implemented")

}

class ChildClass2 : ParentInterface2() {
    override val pubVal2: Int
        get() = TODO("Not yet implemented")
}

fun main() {
    val childClass1:ParentInterface1 = ChildClass1()
    val childClass2:ParentInterface2 = ChildClass2()

    //although we created from Parent Type, when created the instance constructed from Child Class template
    //that is why we only could call pubVal1 and pubVal2 only, not the private privVal1 from Parent
    //because privVal1 was never inherited when we extended the parent class.
    childClass1.pubVal1
    childClass1.pubVal2

    childClass2.pubVal1
    childClass2.pubVal2

    //When we extend List<Object> in List<String> class, then what will be the inherited template in List<String>
    //we don't have answer to it. Thus, List<String> not subtype of List<Object>
}

// Java
/*val strs: MutableList<String> = ArrayList()
val objs: MutableList<Any> = strs // !!! A compile-time error here saves us from a runtime exception later.
objs.add(1) // Put an Integer into a list of Strings
val s = strs[0] // !!! ClassCastException: Cannot cast Integer to String*/


//But, above have implications, consider below. Doing below is safe for runtime.
//In java compile time vs runtime inconsistencies
// Java
/*
interface Collection<E> ... {
    void addAll(Collection<E> items);
}

// Java
void copyAll(Collection<Object> to, Collection<String> from) {
    to.addAll(from);
    // !!! Would not compile with the naive declaration of addAll:
    // Collection<String> is not a subtype of Collection<Object>
}*/

//add all Collection from one to another is safe to do for runtime. Because add all of already existing
//items of collections. That Template surface area of Collections remain intact. Hence extends definition valid
//hence no problem for runtime. That is based on the semantics of what is inside, we have to
//carefully define what we can extend or not, but Java, default rule, prevents on during compile time
//even for those which are run-time safe!!!

/*The wildcard type argument ? extends E indicates that this method accepts a collection of objects
of E or a subtype of E, not just E itself. This means that you can safely read E's from items
(elements of this collection are instances of a subclass of E), but cannot write to it as you don't
know what objects comply with that unknown subtype of E. In return for this limitation, you get the
desired behavior: Collection<String> is a subtype of Collection<? extends Object>. In other words,
the wildcard with an extends-bound (upper bound) makes the type covariant.*/

