package com.liyan.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Author : sun
 * Date: 2024/11/14 11:09
 * Description:
 */
class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val HOME_PAGE_SELECTED_INDEX = "home_page_selected_index"
    private val mSelectLiveData = MutableLiveData<Int>()
    fun getSelectedIndex(): LiveData<Int> {
        if (mSelectLiveData.value == null) {
            val index = savedStateHandle.get<Int>(HOME_PAGE_SELECTED_INDEX) ?: 0
            mSelectLiveData.postValue(index)
        }
        return mSelectLiveData
    }
    fun saveSelectIndex(selectIndex: Int) {
        savedStateHandle.set(HOME_PAGE_SELECTED_INDEX, selectIndex)
        mSelectLiveData.value = selectIndex
    }


}