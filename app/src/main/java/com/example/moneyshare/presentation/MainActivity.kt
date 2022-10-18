package com.example.moneyshare.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moneyshare.R
import com.example.moneyshare.auth.Auth
import com.example.moneyshare.presentation.navigation.MainNavigation
import com.example.moneyshare.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            val snackbarScope = rememberCoroutineScope()
            AppTheme {

                Scaffold(
                    scaffoldState = scaffoldState,
                ) { padding ->
                    val p = padding
                    MainNavigation(
                        navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState,
                        snackbarScope = snackbarScope
                    )
                }
            }
        }
    }
}