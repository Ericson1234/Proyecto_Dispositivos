package com.lugares_j.ui.libro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.lugares_j.R
import com.lugares_j.databinding.FragmentAddLibroBinding
import com.lugares_j.model.Libro
import com.lugares_j.utiles.AudioUtiles
import com.lugares_j.utiles.ImagenUtiles
import com.lugares_j.viewmodel.LibroViewModel

class AddLibroFragment : Fragment() {

    //Objeto para interactuar finalmanete con la tabla
    private lateinit var libroViewModel: LibroViewModel

    private var _binding: FragmentAddLibroBinding? = null
    private val binding get() = _binding!!


    private fun addLibro() {
        binding.msgMensaje.text = getString((R.string.msg_subiendo_lugar))
        val nombre =
            binding.etNombre.text.toString() //Obtiene el texto de lo que el usuario escribio
        if (nombre.isNotEmpty()) { //si escribio algo en el nombre se puede guardar el lugar

            val correo =
                binding.etCorreo.text.toString() //Obtiene el texto de lo que el usuario escribio
            val telefono =
                binding.etTelefono.text.toString() //Obtiene el texto de lo que el usuario escribio
            val categoria = binding.etCategoria.text.toString()
            val libro = Libro("", nombre, correo, telefono, categoria)

            //Se procede a registrar el nuevo lugar...
            libroViewModel.saveLibro(libro)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_libro_added),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addLibroFragment_to_nav_libro)
        } else { //No se puede registrar el lugar ... falta info
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





