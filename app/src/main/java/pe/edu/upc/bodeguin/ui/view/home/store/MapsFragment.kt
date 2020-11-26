package pe.edu.upc.bodeguin.ui.view.home.store

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.data.network.model.response.data.StoreData
import pe.edu.upc.bodeguin.ui.viewModel.store.StoreViewModel
import pe.edu.upc.bodeguin.ui.viewModel.store.StoreViewModelFactory
import pe.edu.upc.bodeguin.util.snackBar

class MapsFragment : Fragment(), OnMapReadyCallback, StoreListener, KodeinAware {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var storeViewModel: StoreViewModel

    override val kodein by kodein()
    private val factory: StoreViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeViewModel = ViewModelProvider(this, factory).get(StoreViewModel::class.java)

        val sharedPreferences = activity!!.getSharedPreferences("localData", 0)
        val token = sharedPreferences.getString("token", "")
        storeViewModel.setToken(token.toString())
        storeViewModel.storeListener = this

        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true
        setUpMap(map)
        storeViewModel.getStores(map)
    }

    private fun placeMarker(stores: List<StoreData>){
        for (store in stores){
            val markerOptions = MarkerOptions()
            markerOptions
                .position(LatLng(store.latitude, store.longitude))
                .title(store.name)
                .snippet(store.direction)
            map.addMarker(markerOptions)
        }
    }

    private fun setUpMap(map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15.0f))
            }
        }
    }

    override fun onSuccess(stores: List<StoreData>) {
        Log.d("test", stores[0].latitude.toString())
        Log.d("test", stores[0].longitude.toString())
        placeMarker(stores)
    }

    override fun onFailure(message: String) {
        clMaps.snackBar(message)
    }
}