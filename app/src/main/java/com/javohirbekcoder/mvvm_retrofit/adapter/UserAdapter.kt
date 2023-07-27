package com.javohirbekcoder.mvvm_retrofit.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javohirbekcoder.mvvm_retrofit.R
import com.javohirbekcoder.mvvm_retrofit.databinding.UserItemBinding
import com.javohirbekcoder.mvvm_retrofit.model.User


/*
Created by Javohirbek on 11.07.2023 at 16:42
*/
class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onBtnDeleteClicked: OnBtnDeleteClicked
    private lateinit var onBtnEditClicked: OnBtnEditClicked

    private val usersList = ArrayList<User>()

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User) {
            binding.tvUserId.text = user.id.toString()
            binding.tvUserMail.text = user.email
            binding.tvUserName.text = user.name

            if (user.gender == "male") binding.imGender.setImageResource(R.drawable.male)
            else  binding.imGender.setImageResource(R.drawable.female)
            if (user.status == "active") binding.cardStatus.setBackgroundColor(Color.parseColor("#37F355"))
            else binding.cardStatus.setBackgroundColor(Color.parseColor("#FF4343"))


            binding.btnDelete.setOnClickListener {
                onBtnDeleteClicked.setOnBtnDeleteClickListener(adapterPosition)
            }

            binding.btnEdit.setOnClickListener {
                onBtnEditClicked.setOnBtnEditClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = usersList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(usersList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<User>){
        this.usersList.clear()
        this.usersList.addAll(list)
        notifyDataSetChanged()
    }

    fun interface OnBtnDeleteClicked{
        fun setOnBtnDeleteClickListener(position: Int)
    }

    fun interface OnBtnEditClicked{
        fun setOnBtnEditClickListener(position: Int)
    }

    fun setOnBtnDeleteClickListener(listener:OnBtnDeleteClicked){
        onBtnDeleteClicked = listener
    }

    fun setOnBtnEditClickListener(listener:OnBtnEditClicked){
        onBtnEditClicked = listener
    }


}