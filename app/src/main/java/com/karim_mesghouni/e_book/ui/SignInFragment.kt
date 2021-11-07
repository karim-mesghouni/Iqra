package com.exemple.e_book.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.karim_mesghouni.e_book.helpers.checkNetwork
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karim_mesghouni.e_book.R
import com.karim_mesghouni.e_book.domain.User
import com.karim_mesghouni.e_book.repository.IRepository
import com.karim_mesghouni.e_book.repository.Repository
import com.karim_mesghouni.e_book.ui.InterestedFragment
import com.karim_mesghouni.e_book.ui.MainActivity
import com.karim_mesghouni.e_book.utils.Constants
import com.karim_mesghouni.e_book.utils.SharedPref

class SignInFragment:Fragment() {

    private lateinit var googleSignIn: TextView
    /** declare auth**/
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** config sing in**/
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        /** Initialize Firebase Auth**/
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleSignIn = view.findViewById(R.id.sign_in)
        googleSignIn.setOnClickListener {
            if (!checkNetwork(context)){
                Toast.makeText(context, R.string.check_your_connection, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
                signIn()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up,container,false)
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(SignInFragment.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(SignInFragment.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(SignInFragment.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    checkUser(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Log.w(SignInFragment.TAG, "signInWithCredential:failure", task.exception)

                }
            }
    }



    private fun checkUser(user: FirebaseUser?){
        val repo : IRepository<User> = Repository(User::class.java, Constants.USER_COLLECTION,requireContext())
//        var isExist:Boolean
        repo.get(user?.uid!!).addOnCompleteListener {
            it.result?.let {
                if (null == it.id){
                    // here probably error
                    activity?.supportFragmentManager?.commit {
                        setReorderingAllowed(true)
                        replace<InterestedFragment>(R.id.fragment_container)
                    }
                }else {
                    Log.d("TAG", it.id!!)
                   addUser()
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
            }
        }

//
//        if (!isExist!!){
//            // here probably error
//            activity?.supportFragmentManager?.commit {
//                setReorderingAllowed(true)
//                replace<InterestedFragment>(R.id.fragment_container)
//            }
//        }else {
//            startActivity(Intent(activity, MainActivity::class.java))
//            activity?.finish()
//        }
    }
    private fun addUser(){
        SharedPref.init(activity?.baseContext)
        SharedPref.write(SharedPref.IS_THERE,true)
        SharedPref.write(SharedPref.USER_ID,FirebaseAuth.getInstance().currentUser?.uid)
    }

    companion object {
        private const val TAG = "GoogleActivity"
    }
}