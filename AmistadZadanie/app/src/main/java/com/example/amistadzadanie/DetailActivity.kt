package com.example.amistadzadanie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.amistadzadanie.databinding.ActivityDetailBinding
import com.example.amistadzadanie.viewModel.DetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModel<DetailViewModel>()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val routeId = intent.extras!!.getInt("key1")

        viewModel.loadRouteById(routeId)
        viewModel.routeLiveData.observe(this, Observer { route ->
            Log.e("TAG3", "Route w observe: $route")

            val separatedTrack = route.track.split(" ")
            val trackToDouble = separatedTrack.map { it.toDouble() }
            val latLng = trackToDouble.chunked(2).map {
                LatLng(it[0], it[1])
            }

            val routeLatLang = LatLng(route.latitude, route.longitude)
            createPolyline(latLng)
            zoomMap(routeLatLang)
        })
    }

    private fun createPolyline(latLng: List<LatLng>) {
        mMap.addPolyline(
            PolylineOptions()
                .addAll(latLng)
        )
    }

    private fun zoomMap(latLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(latLng))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12F))
    }
}

