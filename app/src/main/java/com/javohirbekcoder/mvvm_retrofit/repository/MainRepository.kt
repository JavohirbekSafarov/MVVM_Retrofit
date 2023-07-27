package com.javohirbekcoder.mvvm_retrofit.repository

import androidx.lifecycle.MutableLiveData
import com.javohirbekcoder.mvvm_retrofit.MainActivity
import com.javohirbekcoder.mvvm_retrofit.api.ApiService
import com.javohirbekcoder.mvvm_retrofit.api.RetrofitClient
import com.javohirbekcoder.mvvm_retrofit.model.DeleteUser
import com.javohirbekcoder.mvvm_retrofit.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*
Created by Javohirbek on 11.07.2023 at 15:57
*/
class MainRepository {
    private val usersList = MutableLiveData<ArrayList<User>>()

    private var status = MutableLiveData<String>()

    private val api = RetrofitClient.getRetrofit().create(ApiService::class.java)

    fun getUsersList(): MutableLiveData<ArrayList<User>> = usersList

    fun getStatus(): MutableLiveData<String> = status

    fun getAllUser() {
        status.postValue(MainActivity.LOADING)
        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    usersList.postValue(response.body() as ArrayList<User>)
                    status.postValue(MainActivity.SUCCESS)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                status.postValue(MainActivity.ERROR)
                t.printStackTrace()
            }
        })
    }

    fun createUser(user: User) {
        status.postValue(MainActivity.LOADING)
        api.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val resUser = response.body()!!
                    usersList.value?.add(0, resUser)
                    usersList.postValue(usersList.value)
                    status.postValue(MainActivity.SUCCESS)
                }
                if (response.code() in 100..299) {
                    status.postValue(MainActivity.SUCCESS)
                } else if (response.code() in 400..599) {
                    status.postValue(MainActivity.ERROR)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                status.postValue(MainActivity.ERROR)
                t.printStackTrace()
            }
        })
    }

    fun deleteUser(id: String) {
        status.postValue(MainActivity.LOADING)

        api.deleteUser(id).enqueue(object : Callback<DeleteUser> {
            override fun onResponse(call: Call<DeleteUser>, response: Response<DeleteUser>) {
                if (response.isSuccessful) {
                    val index = usersList.value?.indexOfFirst {
                        it.id == id.toInt()
                    }
                    if (index != null) {
                        usersList.value?.removeAt(index)
                        usersList.postValue(usersList.value)
                        status.postValue(MainActivity.SUCCESS)
                    }
                }
                if (response.code() in 400..499) {
                    status.postValue(MainActivity.ERROR)
                }
            }

            override fun onFailure(call: Call<DeleteUser>, t: Throwable) {
                status.postValue(MainActivity.ERROR)
                t.printStackTrace()
            }
        })
    }

    fun updateUser(userId:String, user: User){
        status.postValue(MainActivity.LOADING)

        api.updateUser(userId, user).enqueue(object :Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val resUser = response.body()!!

                    val index = usersList.value!!.indexOfFirst {
                        it.id == userId.toInt()
                    }

                    usersList.value!!.removeAt(index)
                    usersList.value!!.add(index, resUser)

                    usersList.postValue(usersList.value)

                    status.postValue(MainActivity.SUCCESS)
                }
                if (response.code() in 100..299) {
                    status.postValue(MainActivity.SUCCESS)
                } else if (response.code() in 400..599) {
                    status.postValue(MainActivity.ERROR)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                status.postValue(MainActivity.ERROR)
                t.printStackTrace()
            }
        })
    }

}