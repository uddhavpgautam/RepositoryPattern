package com.example.repositorypattern.utils

// A wrapper for handling failing requests
sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}

