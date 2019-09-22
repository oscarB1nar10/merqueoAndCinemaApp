package com.example.merqueoandcinemaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.merqueoandcinemaapp.repository.MainFragmentRepository.application
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_main.*

abstract class BaseFragment : Fragment(){

    //const
    private val REQUESTPERMISSION = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialSetUp()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val coordinatorLayout: CoordinatorLayout = layoutInflater.inflate(R.layout.fragment_base, null) as CoordinatorLayout
        val activityContainer: FrameLayout = coordinatorLayout.findViewById(R.id.fl_general_container)
        layoutInflater.inflate(initialLayout(), activityContainer, true)
        return  coordinatorLayout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponents(view)
        checkPermissions()
    }


    abstract fun initialLayout(): Int
    abstract fun initComponents(view: View)
    abstract fun initialSetUp()


    private fun checkPermissions(){

        if (ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_NETWORK_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    REQUESTPERMISSION)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       when(requestCode){
           REQUESTPERMISSION -> {
               if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
               } else { Toast.makeText(activity,"Se deben dar permisos para poder continuar", Toast.LENGTH_LONG).show() }
               return }
           else -> {
               // Ignore all other requests.
           }
       }
    }
}