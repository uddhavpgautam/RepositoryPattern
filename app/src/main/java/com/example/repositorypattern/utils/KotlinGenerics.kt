package com.example.repositorypattern.utils

internal class KotlinGenerics<T, U>(var obj1: T, var obj2: U) {
    fun print() {
        println(obj1)
        println(obj2)
    }
}

internal object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val obj = KotlinGenerics("GfG", 15)
        obj.print()
    }
}

/*
internal class Test<T>(var `object`: T)

internal object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val iObj = Test(15)
        println(iObj.`object`)

        val sObj = Test("GeeksForGeeks")
        println(sObj.`object`)
    }
}*/

