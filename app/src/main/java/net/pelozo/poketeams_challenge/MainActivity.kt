package net.pelozo.poketeams_challenge

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import net.pelozo.poketeams_challenge.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController.also { it.setGraph(R.navigation.nav_graph, intent.extras) }

    }



    /*
    override fun onBackPressed() =
        if (navController.currentDestination?.id == R.id.HomeFragment)
            finish()
        else
            super.onBackPressed()


    fun login(build: AuthUI.IdpConfig): Boolean {
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    //build
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    //AuthUI.IdpConfig.FacebookBuilder().build()
                )
            )
            .build()
        signInLauncher.launch(intent)
        return true
    }
    */


}