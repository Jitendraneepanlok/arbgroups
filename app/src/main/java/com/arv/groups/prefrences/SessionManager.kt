package com.arv.groups.prefrences

import android.content.Context
import android.preference.PreferenceManager

import android.content.SharedPreferences


class SessionManager(context: Context?) {
    private val sharedPreferences: SharedPreferences
    fun setValue(key: String?, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getValue(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setslider(key: String?, value: Boolean?) {
        sharedPreferences.edit().putBoolean(key, value!!).apply()
    }

    fun getSlider(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getUserData(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setValueBoolean(key: String?, value: Boolean?) {
        sharedPreferences!!.edit().putBoolean(key, value!!).apply()
    }

    fun getValueBoolean(key: String?): Boolean {
        return sharedPreferences!!.getBoolean(key, false)
    }

    fun logout() {
        //val slider = getValue("slider")
        sharedPreferences.edit().clear().apply()
        //setValue("slider", slider)

    }

    companion object {
        var NAME = "Name"
        var CAREOFNAME = "CareOfName"
        var PHONENO = "PhoneNumber"
        var VALUE = "value"
    }

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
}