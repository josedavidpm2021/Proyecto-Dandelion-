package com.tongue.dandelion.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import com.tongue.dandelion.R
import com.tongue.dandelion.databinding.FragmentGoogleMapsBinding
import java.util.*


class GoogleMapsFragment(val initialPosition: LatLng,val callback: GoogleMapReadyCallBack?): Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentGoogleMapsBinding
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGoogleMapsBinding.inflate(inflater,container,false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.isTrafficEnabled = false
        mMap.isBuildingsEnabled = false
        mMap.isIndoorEnabled = false

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(
          //  MarkerOptions()
            //.position(sydney)
            //.title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(initialPosition,13f)
        )

        callback?.onMapReady(this)

    }

    fun enableMyLocation(){
        mMap.isMyLocationEnabled = true
    }

    fun drawPolylineBetweenTwoPoints(p1: LatLng, p2: LatLng){
        mMap.addMarker(
            MarkerOptions()
                .position(p1)
        )
        mMap.addMarker(
            MarkerOptions()
                .position(p2)
        )
        drawCurvedPolyline(p1,p2,0.35)
    }

    private fun drawCurvedPolyline(p1: LatLng, p2: LatLng, k: Double) {

        // Maths
        val distance = SphericalUtil.computeDistanceBetween(p1, p2)
        val heading = SphericalUtil.computeHeading(p1, p2)
        val center = SphericalUtil.computeOffset(p1, distance * 0.5, heading)
        val x = (1 - k * k) * distance * 0.5 / (2 * k)
        val r = (1 + k * k) * distance * 0.5 / (2 * k)
        val c = SphericalUtil.computeOffset(center, x, heading + 90.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 10f))
        // Polyline
        val options = PolylineOptions()

        val patternItemList = Arrays.asList(
            Dash(30f),
            Gap(1f)
        )

        val h1 = SphericalUtil.computeHeading(c, p1)
        val h2 = SphericalUtil.computeHeading(c, p2)
        val points = 150
        val step = (h2 - h1) / points
        for (i in 0 until points) {
            val pi = SphericalUtil.computeOffset(c, r, h1 + i * step)
            options.add(pi)
        }
        mMap.addPolyline(
            options.width(5f).color(Color.BLACK).geodesic(false).pattern(patternItemList)
        )
    }

    fun alignCameraOnNextPosition(position: LatLng){
        val target = mMap.cameraPosition.target
        val bearing = bearingBetweenLocations(target,position).toFloat()
        mMap.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(position).zoom(18f).bearing(bearing).tilt(45f).build()
            )
        )
    }

    private fun bearingBetweenLocations(latLng1: LatLng, latLng2: LatLng): Double {
        val PI = 3.14159
        val lat1 = latLng1.latitude * PI / 180
        val long1 = latLng1.longitude * PI / 180
        val lat2 = latLng2.latitude * PI / 180
        val long2 = latLng2.longitude * PI / 180
        val dLon = long2 - long1
        val y = Math.sin(dLon) * Math.cos(lat2)
        val x = Math.cos(lat1) * Math.sin(lat2) - (Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon))
        var brng = Math.atan2(y, x)
        brng = Math.toDegrees(brng)
        brng = (brng + 360) % 360
        return brng
    }

    interface GoogleMapReadyCallBack{
        fun onMapReady(mapsFragment: GoogleMapsFragment)
    }

}