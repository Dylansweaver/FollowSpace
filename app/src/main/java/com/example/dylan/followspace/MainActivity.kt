package com.example.dylan.followspace

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton




class MainActivity : AppCompatActivity() {
    internal var loginButton: TwitterLoginButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Twitter.initialize(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton.setCallback(object:Callback<TwitterSession>() {
           override fun success(result:Result<TwitterSession>) {

                val session = TwitterCore.getInstance().getSessionManager().getActiveSession()
                val authToken = session.getAuthToken()
                val token = authToken.token
                val secret = authToken.secret
                //Calling login method and passing twitter session
                login(session)
            }

          override fun failure(exception: TwitterException) {
                // Do something on failure
                Toast.makeText(this@MainActivity, "Authentication Failed", Toast.LENGTH_LONG).show();
            }
        })

    }
    fun login(session:TwitterSession) {
        val username = session.getUserName()
        val intent = Intent(this@MainActivity, HomePage::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data)
    }

}



