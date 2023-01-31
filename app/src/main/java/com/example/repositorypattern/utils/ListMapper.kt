package com.example.repositorypattern.utils

// Non-nullable to Non-nullable
interface ListMapper<I, O> : Mapper<List<I>, List<O>>
