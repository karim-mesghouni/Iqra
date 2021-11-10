package com.karim_mesghouni.e_book.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentSettingsBinding
import com.karim_mesghouni.e_book.domain.User
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.SharedPref
import com.karim_mesghouni.e_book.utils.getUserId

/**
 * This [Fragment] will show app settings .
 */
class SettingFragment :Fragment(){

    private lateinit var binding:FragmentSettingsBinding
    //private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         binding.backSetting.setOnClickListener {
             findNavController().popBackStack()
         }
         setData()


        binding.logout.setOnClickListener {
          // signOut()
            //throw RuntimeException("Test Crash") // Force a crash
            //Firebase.crashlytics.recordException(Throwable())
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        SharedPref.init(context)
        SharedPref.write(SharedPref.IS_THERE,false)
        SharedPref.write(SharedPref.USER_ID,"")
        SharedPref.write(SharedPref.USER_NAME,"")
        SharedPref.write(SharedPref.USER_EMAIL,"")
        startActivity(Intent(this.activity,AuthActivity::class.java))
        activity?.finish()
    }

    private fun setData(){
       binding.userAccount.text =  SharedPref.read(SharedPref.USER_EMAIL,"")
        binding.userName.text = SharedPref.read(SharedPref.USER_NAME,"")
    }





}