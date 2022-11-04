package com.example.repositorypattern.utils

interface Mapper<I, O> {
    fun map(input: I): O
}