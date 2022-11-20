package com.lugares_j.ui.lugar

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
import com.lugares_j.R
import com.lugares_j.databinding.FragmentUpdateLugarBinding
import com.lugares_j.model.Lugar
import com.lugares_j.viewmodel.LugarViewModel

class UpdateLugarFragment : Fragment() {

    //Se define un objeto para obtener los argumentos pasados al fragmento.
    private val args by navArgs<UpdateLugarFragmentArgs>()

    //Objeto para interactuar finalmanete con la tabla
    private lateinit var lugarViewModel: LugarViewModel

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel =
            ViewModelProvider(this).get(LugarViewModel::class.java)

        _binding = FragmentUpdateLugarBinding.inflate(inflater, container, false)

        binding.etNombre.setText(args.lugar.nombre)
        binding.etCorreo.setText(args.lugar.correo)
        binding.etTelefono.setText(args.lugar.telefono)
        binding.etWeb.setText(args.lugar.web)
        binding.tvLongitud.text = args.lugar.longitud.toString()
        binding.tvLatitud.text = args.lugar.latitud.toString()
        binding.tvAltura.text = args.lugar.altura.toString()

        binding.btUpdate.setOnClickListener { updateLugar() }
        binding.btDelete.setOnClickListener { deleteLugar() }

        binding.btEmail.setOnClickListener { escribirCorreo() }
        binding.btPhone.setOnClickListener { llamarlugar() }
        binding.btWhatsapp.setOnClickListener { enviarWhatsapp() }
        binding.btWeb.setOnClickListener { verweb() }
        binding.btLocation.setOnClickListener { verEnMapa() }

        if(args.lugar.ruta_audio?.isNotEmpty()==true) {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(args.lugar.rutaAudio)
            mediaPlayer.prepare()
            binding.btPlay.isEnable = true
        }else{
            binding.btPlay.isEnable=false

        }
        binding.btPlay.setOnClickListener {mediaPlayer.start()}


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

    private fun llamarlugar() {

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

    private fun verweb() {

        val valor = binding.etWeb.text.toString()
        if (valor.isNotEmpty()){ //Si el sitio web tiene algo... entonces se intenta enviar correo
            val uri = "http://$valor"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }else { //Si no hay info no se puede realizar la accion
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }

    }

    private fun verEnMapa() {

    }

    private fun deleteLugar() {

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_lugar)
        alerta.setMessage(getString(R.string.msg_pregunta_delete)+" ${args.lugar.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)) {_,_ ->
            lugarViewModel.deleteLugar(args.lugar) //Efectivamente borra el lugar
            Toast.makeText(requireContext(),getString(R.string.msg_lugar_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)) {_,_ ->}
        alerta.create().show()
    }

    private fun updateLugar() {

        val nombre = binding.etNombre.text.toString() //Obtiene el texto de lo que el usuario escribio
        if (nombre.isNotEmpty()) { //si escribio algo en el nombre se puede guardar el lugar
            val correo = binding.etCorreo.text.toString() //Obtiene el texto de lo que el usuario escribio
            val telefono = binding.etTelefono.text.toString() //Obtiene el texto de lo que el usuario escribio
            val web = binding.etWeb.text.toString() //Obtiene el texto de lo que el usuario escribio
            val lugar = Lugar(args.lugar.id,nombre,correo,telefono,web,
                args.lugar.latitud,
                args.lugar.longitud,
                args.lugar.altura,
                args.lugar.ruta_audio,
                args.lugar.ruta_imagen)

            //Se procede a actualizar el nuevo lugar...
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),
                getString(R.string.msg_lugar_updated),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
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