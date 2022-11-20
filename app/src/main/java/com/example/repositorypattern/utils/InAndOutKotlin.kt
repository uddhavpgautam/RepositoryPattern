package com.example.repositorypattern.utils

class DoubleSource: Source<Double> {
    override fun nextT(): Double {
        return 5.0
    }
}

class IntSource: Source<Int> {
    override fun nextT(): Int {
        return 5
    }
}

//Source of T that goes out
interface Source<out T> {
    fun nextT(): T
}

//what it can actually compare when implemented
class NumberComparable: Comparable<Number> {
    override fun compareTo(other: Number): Int {
        return 1
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

    println(y1.compareTo(2.0))
    println(y2.compareTo(2))
    println(y3.compareTo(2L))

    //out, different machines to out Int and Double, but those machine can be safely
    //create from their parent typed machine

    //Any but Any is not like Object for Java although it is like Object
    //because of out variable Any is bound to be a type of Number
    val anySource: Source<Any> = DoubleSource() //safely create child class instance from parent class
    val intSource: Source<Any> = IntSource() //safely create child class instance from parent class

    anySource.nextT()
    intSource.nextT()

    println(anySource.nextT())
    println(intSource.nextT())
}