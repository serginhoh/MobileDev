package br.com.serginho.calculadoraflex.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import br.com.serginho.calculadoraflex.R
import br.com.serginho.calculadoraflex.ui.form.FormActivity
import br.com.serginho.calculadoraflex.ui.signup.SignUpActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val newUserRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        //refresh do usuário
        mAuth.currentUser?.reload()

        if (mAuth.currentUser != null) {
            goToHome()
        }

        btLogin.setOnClickListener {

            var isValidEmail = validate(inputLoginEmail)
            var isValidPassword = validate(inputLoginPassword)

         if (isValidEmail && isValidPassword) {
             mAuth.signInWithEmailAndPassword(
                 inputLoginEmail.editText?.text.toString(),
                 inputLoginPassword.editText?.text.toString()
             ).addOnCompleteListener {
                 if (it.isSuccessful) {
                     goToHome()
                 } else {
                     Toast.makeText(
                         this@LoginActivity, it.exception?.message,
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             }
         }
        }

        btSignup.setOnClickListener {
            startActivityForResult(
                Intent(this, SignUpActivity::class.java),
                newUserRequestCode)
        }
    }

    private fun validate(control : TextInputLayout) :Boolean{

        if (control.editText?.text.toString().isEmpty()){
            control.error = "Preencha o campo!"
            return false
        }else {
            control.isErrorEnabled = false
            return true
        }
    }

    private fun goToHome() {
        val intent = Intent(this, FormActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
        */

        when(requestCode){
            newUserRequestCode -> {
                if (resultCode == Activity.RESULT_OK)
                    inputLoginEmail.editText?.setText(data?.getStringExtra("email"))
            }
            else -> {}
        }
    }
}
