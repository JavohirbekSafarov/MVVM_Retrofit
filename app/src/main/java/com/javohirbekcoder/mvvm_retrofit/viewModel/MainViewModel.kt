package com.javohirbekcoder.mvvm_retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.javohirbekcoder.mvvm_retrofit.model.User
import com.javohirbekcoder.mvvm_retrofit.repository.MainRepository


/*
Created by Javohirbek on 11.07.2023 at 16:05
*/
class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAllUsers(): LiveData<ArrayList<User>> {
        mainRepository.getAllUser()
        return mainRepository.getUsersList()
    }

    fun addUser(user: User) {
        mainRepository.createUser(user)
    }

    fun deleteUser(id: String) {
        mainRepository.deleteUser(id)
    }

    fun getStatus(): LiveData<String> {
        return mainRepository.getStatus()
    }

    fun updateUser(id: String, user: User) {
        mainRepository.updateUser(id, user)
    }
}