package com.javohirbekcoder.mvvm_retrofit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.javohirbekcoder.mvvm_retrofit.adapter.UserAdapter
import com.javohirbekcoder.mvvm_retrofit.databinding.ActivityMainBinding
import com.javohirbekcoder.mvvm_retrofit.databinding.CreateUserDialogBinding
import com.javohirbekcoder.mvvm_retrofit.databinding.PatchUserDialogBinding
import com.javohirbekcoder.mvvm_retrofit.factory.MainViewModelFactory
import com.javohirbekcoder.mvvm_retrofit.model.User
import com.javohirbekcoder.mvvm_retrofit.repository.MainRepository
import com.javohirbekcoder.mvvm_retrofit.viewModel.MainViewModel
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var userAdapter: UserAdapter
    private lateinit var usersList: ArrayList<User>

    companion object {
        const val LOADING = "loading"
        const val ERROR = "error"
        const val SUCCESS = "success"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository())
        )[MainViewModel::class.java]

        viewModel.getAllUsers().observe(this) {
            usersList = it
            userAdapter.submitList(usersList)
            binding.recyclerUser.adapter = userAdapter

            userAdapter.setOnBtnDeleteClickListener { index -> viewModel.deleteUser(usersList[index].id.toString()) }

            userAdapter.setOnBtnEditClickListener { index -> showUpdateUserDialog(index) }
        }

        binding.addUserBtn.setOnClickListener { showCreateUserDialog() }

        viewModel.getStatus().observe(this) {
            when (it) {
                SUCCESS -> {
                    binding.progressHorizontal.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Action completed successfully", Toast.LENGTH_SHORT).show()
                }
                LOADING -> {
                    binding.progressHorizontal.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, "Loading...", Toast.LENGTH_SHORT).show()
                }
                ERROR -> {
                    binding.progressHorizontal.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this@MainActivity, "Status error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showUpdateUserDialog(position: Int) {
        val dialog = Dialog(this)
        val dialogBinding = PatchUserDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)

        val selectedUser = usersList[position]

        dialogBinding.edName.setText(selectedUser.name)
        dialogBinding.edMail.setText(selectedUser.email)
        dialogBinding.checkboxUserStatus.isChecked = selectedUser.status == "active"

        dialogBinding.btnSaveAndPostUser.setOnClickListener {
            val status = if (dialogBinding.checkboxUserStatus.isChecked) "active" else "inactive"

            with(dialogBinding) {
                if (edName.text.isNotEmpty() && edMail.text.isNotEmpty()) {
                    viewModel.updateUser(
                        selectedUser.id.toString(),
                        User(
                            selectedUser.id,
                            edName.text.toString(),
                            edMail.text.toString(),
                            "Male",
                            status
                        )
                    )
                    userAdapter.notifyItemRangeChanged(0, usersList.lastIndex)
                    dialog.dismiss()
                }
            }

        }

        dialog.show()
    }

    private fun showCreateUserDialog() {
        val dialog = Dialog(this)
        val dialogBinding = CreateUserDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)

        var gender = ""

        dialogBinding.radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.maleRadioBtn -> {
                    gender = "male"
                }

                R.id.femaleRadioBtn -> {
                    gender = "female"
                }

                else -> {
                    gender = ""
                    Toast.makeText(this@MainActivity, "Gender error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialogBinding.btnSaveAndPostUser.setOnClickListener {
            val status = if (dialogBinding.checkboxUserStatus.isChecked) "active" else "inactive"

            with(dialogBinding) {
                if (edName.text.isNotEmpty() && edMail.text.isNotEmpty() && gender.isNotEmpty()) {
                    viewModel.addUser(
                        User(
                            Random().nextInt(324580) + 1,
                            edName.text.toString(),
                            edMail.text.toString(),
                            gender,
                            status
                        )
                    )
                    userAdapter.notifyItemRangeChanged(0, usersList.lastIndex)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

}