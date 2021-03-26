package com.timaklokov.tickets.data.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonCountiresHelper {
    fun readCountriesFromJson(inputString: String): MutableList<String> {
        val gson = Gson()
        val countryListType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(inputString, countryListType)
    }

    fun saveCountriesToJson(countriesList: MutableList<String>): String {
        val gson = Gson()
        val countryListType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.toJson(countriesList, countryListType)
    }
}