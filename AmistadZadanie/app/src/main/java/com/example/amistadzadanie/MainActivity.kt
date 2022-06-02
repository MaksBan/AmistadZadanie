package com.example.amistadzadanie

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amistadzadanie.databinding.ActivityMainBinding
import com.example.amistadzadanie.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException


class MainActivity : AppCompatActivity() {


    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var routeAdapter: RouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupRecyclerView()
        displayRouteList()

    }

    private fun displayRouteList() {
        viewModel.loadRoutes()
        viewModel.mutableState.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        routeAdapter.routes = it.toMutableList()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let {
                        Log.e("TAG", "An error occurred: $it")

                    }
                    AlertDialog.Builder(this).apply {
                        setTitle(R.string.dialog_title)
                        setMessage(R.string.dialog_description)
                        setPositiveButton(R.string.dialog_positive_button,
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                viewModel.loadRoutes()
                            })
                        setNegativeButton(R.string.dialog_negative_button,
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                            })
                        create()
                    } ?: throw IllegalStateException("Activity cannot be null")

                }
            }
        }
    }

    private fun setupRecyclerView() {
        routeAdapter = RouteAdapter {
            val bundle = Bundle()
            bundle.putInt("key1", it.id)

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        val recyclerView = binding.rvRoutes
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = routeAdapter
    }

    private fun showProgressBar(){
        binding.progressBar.isVisible = true
    }

    private fun hideProgressBar(){
        binding.progressBar.isVisible = false
    }
}