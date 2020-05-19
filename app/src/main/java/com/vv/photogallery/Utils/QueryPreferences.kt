package com.example.photogallerykotlinversion.Utils

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.photogallerykotlinversion.Constants.IS_POLLING
import com.example.photogallerykotlinversion.Constants.LAST_RESULT_ID
import com.example.photogallerykotlinversion.Constants.SEARCH_QUERY

object QueryPreferences {
    fun getStoredQuery(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(SEARCH_QUERY, " ")!!
    }

    fun setStoredQuery(context: Context, query: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit {
                putString(SEARCH_QUERY, query)
            }
    }

    fun getLastResultId(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(LAST_RESULT_ID, " ")!!
    }

    fun setLastResultId(context: Context, lastResultId: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putString(LAST_RESULT_ID, lastResultId)
        }
    }

    fun isPolling(context: Context):Boolean{
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_POLLING,false)
    }

    fun setPolling(context: Context,isOn:Boolean){
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putBoolean(IS_POLLING,isOn)
        }
    }
}