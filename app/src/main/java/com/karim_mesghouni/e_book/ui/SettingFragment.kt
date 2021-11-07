package com.karim_mesghouni.e_book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.databinding.FragmentSettingsBinding
import com.karim_mesghouni.e_book.domain.User
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.getUserId

/**
 * This [Fragment] will show app settings .
 */
class SettingFragment :Fragment(){
    private lateinit var  repository:IRepository<User>
    private lateinit var binding:FragmentSettingsBinding
    //private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository =  Repository(User::class.java,Constants.USER_COLLECTION,requireContext())

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
        repository.get(getUserId(requireContext())).addOnCompleteListener {
            val user = it.result!!
            binding.userName.text = user.name
            binding.userAccount.text = user.email
        }

    }





}