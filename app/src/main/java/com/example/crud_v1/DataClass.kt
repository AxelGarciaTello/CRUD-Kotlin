package com.example.crud_v1

class DataClass {

    lateinit var dataTitle: String
    lateinit var dataDesc: String
    lateinit var dataLang: String
    lateinit var key: String

    constructor(dataTitle: String, dataDesc: String, dataLang: String) {
        this.dataTitle = dataTitle
        this.dataDesc = dataDesc
        this.dataLang = dataLang
    }

    constructor() {

    }

}