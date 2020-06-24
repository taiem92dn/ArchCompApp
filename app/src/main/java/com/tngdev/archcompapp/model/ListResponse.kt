package com.tngdev.archcompapp.model

class ListResponse<T> {

    var count : Int = 0
    lateinit var next : String
    lateinit var previous : String
    lateinit var results : List<T>
}