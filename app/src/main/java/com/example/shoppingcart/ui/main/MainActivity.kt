package com.example.shoppingcart.ui.main

import NetworkStatusTracker
import NetworkStatusViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.shoppingcart.R
import com.example.shoppingcart.generated.callback.OnClickListener
import com.example.shoppingcart.ui.login.StartFragmentViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: NetworkStatusViewModel by lazy {
        ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {

                    val networkStatusTracker = NetworkStatusTracker(this@MainActivity)
                    return NetworkStatusViewModel(networkStatusTracker) as T
                }
            },
        ).get(NetworkStatusViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.state.observe(this) { state ->
            when (state) {
                MyState.Fetched -> {}
                MyState.Error ->  {

                    val mySnackbar = Snackbar.make(findViewById(R.id.main_container),
                        getString(R.string.no_connection_internet), Snackbar.LENGTH_INDEFINITE)
                    mySnackbar.setAction(getString(R.string.ok), View.OnClickListener {
                        finishAndRemoveTask()
                    })
                    mySnackbar.show()

                }
                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()


    }


}
