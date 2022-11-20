package com.lugares_j.ui.lugar

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.lugares_j.R
import com.lugares_j.databinding.FragmentAddLugarBinding
import com.lugares_j.databinding.FragmentLugarBinding
import com.lugares_j.model.Lugar
import com.lugares_j.viewmodel.LugarViewModel

class AddLugarFragment : Fragment() {

    //Objeto para interactuar finalmanete con la tabla
    private lateinit var lugarViewModel: LugarViewModel

    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel =
            ViewModelProvider(this).get(LugarViewModel::class.java)

        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)

        binding.btAdd.setOnClickListener {

            subeNota()
        }

        return binding.root
    }

    private fun subeNota(){

        val archivoLocal = audioUtilies.audioFile
        if(archivoLocal.exists() &&
                archivoLocal.isFile &&
                archivoLocal.canRead()){

            //Se fija la ruta (uri) del archivoLocal de audio
            val rutaLocal = Uri.fromFile((archivoLocal))

            //Se establece la ruta en la nube de la nota de audio
            val rutaNube = "lugaresApp/${Firebase.auth.currentUser?.email}"/audios/${archivoLocal.name}
            //ejemplo "lugaresApp/juan33@gmail.com/audios/20221117190001.mp3"

            //Se hace la referencia Real...
            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)

            //Se hace el archivo y se establece lo "listen" para saber que hacer...
            referencia.putFile(rutaLocal)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            //Se obtiene la ruta publica del archivoLocal
                            val rutaAudio = it.toString)
                            subeImagen(rutaAudio)
                }
                }
                .addOnFailureListener {
                    subeImagen("")
                }
        } else { // No hay foto o hay un error en el archivo que no puede leer
            subeImagen("")

        }

    }

    private fun subeImagen(rutaAudio: String) {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
    }

    private fun addLugar() {

        val nombre = binding.etNombre.text.toString() //Obtiene el texto de lo que el usuario escribio
        if (nombre.isNotEmpty()) { //si escribio algo en el nombre se puede guardar el lugar
            val correo = binding.etCorreo.text.toString() //Obtiene el texto de lo que el usuario escribio
            val telefono = binding.etTelefono.text.toString() //Obtiene el texto de lo que el usuario escribio
            val web = binding.etWeb.text.toString() //Obtiene el texto de lo que el usuario escribio
            val lugar = Lugar("",nombre,correo,telefono,web,
                0.0,0.0,0.0,"","")

            //Se procede a registrar el nuevo lugar...
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),
                getString(R.string.msg_lugar_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
        } else { //No se puede registrar el lugar ... falta info
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}