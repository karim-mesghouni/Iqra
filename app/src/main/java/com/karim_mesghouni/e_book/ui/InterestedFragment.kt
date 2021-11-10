package com.karim_mesghouni.e_book.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

import com.google.firebase.auth.FirebaseAuth
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentInterestedBinding
import com.karim_mesghouni.e_book.domain.User
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.SharedPref

class InterestedFragment : Fragment() {
    private lateinit var binding: FragmentInterestedBinding
    private val list: MutableList<String> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.artType.setOnClickListener { selectTypes(binding.artType) }
        binding.childrenType.setOnClickListener { selectTypes(binding.childrenType) }
        binding.crimeType.setOnClickListener { selectTypes(binding.crimeType) }
        binding.economyType.setOnClickListener { selectTypes(binding.economyType) }
        binding.foodtype.setOnClickListener { selectTypes(binding.foodtype) }
        binding.healthType.setOnClickListener { selectTypes(binding.healthType) }
        binding.historyType.setOnClickListener { selectTypes(binding.historyType) }
        binding.literaryType.setOnClickListener { selectTypes(binding.literaryType) }
        binding.memoirType.setOnClickListener { selectTypes(binding.memoirType) }
        binding.moneyType.setOnClickListener { selectTypes(binding.moneyType) }
        binding.politicsType.setOnClickListener { selectTypes(binding.politicsType) }
        binding.productivityType.setOnClickListener { selectTypes(binding.productivityType) }
        binding.programmingType.setOnClickListener { selectTypes(binding.programmingType) }
        binding.scienceFictionType.setOnClickListener { selectTypes(binding.scienceFictionType) }
        binding.selfHelpType.setOnClickListener { selectTypes(binding.selfHelpType) }
        binding.youngType.setOnClickListener { selectTypes(binding.youngType) }
        binding.techType.setOnClickListener { selectTypes(binding.techType) }
        binding.next.setOnClickListener {
            if (list.size < 3) {
                Toast.makeText(context, R.string.you_show_select_atleast, LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveUser()
            addUser()
        }
    }

    private fun finish() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }

    private fun saveUser() {
        val rep: IRepository<User> = Repository(User::class.java, Constants.USER_COLLECTION,requireContext())
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val user = User().apply {
            name = firebaseUser?.displayName
            id = firebaseUser?.uid
            email = firebaseUser?.email
            imageUrl = firebaseUser?.photoUrl.toString()
            interested = list
        }
        rep.add(user)
        finish()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterestedBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun selectTypes(type: TextView) {
        if (type.text !in list) {
            type.background =
                AppCompatResources.getDrawable(type.context, R.drawable.interested_bg2)
            type.setTextColor(context?.getColor(R.color.white)!!)
            list.add(type.text.toString())

        } else {
            type.background =
                AppCompatResources.getDrawable(type.context, R.drawable.intrested_bg)
            type.setTextColor(context?.getColor(R.color.costume_black)!!)
            list.remove(type.text.toString())
        }
    }
    // add user to shared preferences
    private fun addUser(){
        SharedPref.init(activity?.baseContext)
        SharedPref.write(SharedPref.IS_THERE,true)
        SharedPref.write(SharedPref.USER_ID,FirebaseAuth.getInstance().currentUser?.uid)
        SharedPref.write(SharedPref.USER_NAME,FirebaseAuth.getInstance().currentUser?.displayName)
        SharedPref.write(SharedPref.USER_ID,FirebaseAuth.getInstance().currentUser?.email)
    }
}