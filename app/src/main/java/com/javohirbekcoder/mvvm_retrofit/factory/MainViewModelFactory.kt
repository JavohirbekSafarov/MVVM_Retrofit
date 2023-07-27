package com.javohirbekcoder.mvvm_retrofit.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javohirbekcoder.mvvm_retrofit.repository.MainRepository
import com.javohirbekcoder.mvvm_retrofit.viewModel.MainViewModel
import java.lang.IllegalStateException


/*
Created by Javohirbek on 11.07.2023 at 16:37
*/
class MainViewModelFactory(private val mainRepository: MainRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mainRepository) as T
        }else{
            throw IllegalArgumentException("Illegal argument")
        }
    }
}