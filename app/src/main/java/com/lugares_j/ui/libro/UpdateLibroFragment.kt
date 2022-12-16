package com.lugares_j.ui.libro

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lugares_j.R
import com.lugares_j.databinding.FragmentUpdateLibroBinding
import com.lugares_j.model.Libro
import com.lugares_j.viewmodel.LibroViewModel

class UpdateLibroFragment : Fragment() {

    //Se define un objeto para obtener los argumentos pasados al fragmento.
    private val args by navArgs<UpdateLibroFragmentArgs>()

    //Objeto para interactuar finalmanete con la tabla
    private lateinit var libroViewModel: LibroViewModel

    private var _binding: FragmentUpdateLibroBinding? = null
    private val binding get() = _binding!!

    //Objeto mediaPlayer para escucar audio desde la nube
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        libroViewModel =
            ViewModelProvider(this).get(LibroViewModel::class.java)

        _binding = FragmentUpdateLibroBinding.inflate(inflater, container, false)

        binding.etNombre.setText(args.libro.nombre)
        binding.etCorreo.setText(args.libro.correo)
        binding.etTelefono.setText(args.libro.telefono)
        binding.etCategoria.setText(args.libro.categoria)


        binding.btUpdate.setOnClickListener { updateLibro() }
        binding.btDelete.setOnClickListener { deleteLibro() }

        return binding.root
    }

    private fun escribirCorreo() {
        val valor = binding.etCorreo.text.toString()
        if (valor.isNotEmpty()){ //Si el correo tiene algo... entonces se intenta enviar correo
            val intent = Intent(Intent.ACTION_SEND)
            intent.type="message/rfc822" //correo electronico para android
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(valor))
            intent.putExtra(Intent.EXTRA_SUBJECT,
            getString(R.string.msg_saludos)+" "+binding.etNombre.text)
            intent.putExtra(Intent.EXTRA_TEXT,
            getString(R.string.msg_mensaje_correo))
            startActivity(intent)
        }else { //Si no hay info no se puede realizar la accion
            Toast.makeText(requireContext(),
            getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }


    private fun enviarWhatsapp() {

        val valor = binding.etTelefono.text.toString()
        if (valor.isNotEmpty()){ //Si el telefono tiene algo... entonces se intenta enviar correo
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone=506$valor&text="+
                getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }else { //Si no hay info no se puede realizar la accion
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }

    }

    private fun deleteLibro() {

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_libro)
        alerta.setMessage(getString(R.string.msg_pregunta_delete)+" ${args.libro.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)) {_,_ ->
            libroViewModel.deleteLibro(args.libro) //Efectivamente borra el lugar
            Toast.makeText(requireContext(),getString(R.string.msg_libro_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateLibroFragment_to_nav_libro)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)) {_,_ ->}
        alerta.create().show()
    }

    private fun updateLibro() {

        val nombre = binding.etNombre.text.toString() //Obtiene el texto de lo que el usuario escribio
        if (nombre.isNotEmpty()) { //si escribio algo en el nombre se puede guardar el lugar

            val correo = binding.etCorreo.text.toString() //Obtiene el texto de lo que el usuario escribio
            val telefono = binding.etTelefono.text.toString() //Obtiene el texto de lo que el usuario escribio
            val categoria = binding.etCategoria.text.toString()
            val libro = Libro(args.libro.id,nombre,correo,telefono,categoria)

            //Se procede a actualizar el nuevo lugar...
            libroViewModel.saveLibro(libro)
            Toast.makeText(requireContext(),
                getString(R.string.msg_libro_updated),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateLibroFragment_to_nav_libro)
        } else { //No se puede actualizar el lugar ... falta info
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