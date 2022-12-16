package com.lugares_j.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lugares_j.databinding.LibroFilaBinding
import com.lugares_j.model.Libro
import com.lugares_j.ui.libro.LibroFragmentDirections

class LibroAdapter : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    //Clase interna que se encrga d finalmente dibujar la informacion
    inner class LibroViewHolder(private val itemBinding: LibroFilaBinding)
        :RecyclerView.ViewHolder(itemBinding.root){
        fun dibuja(libro: Libro){
            itemBinding.tvNombre.text = libro.nombre
            itemBinding.tvCorreo.text = libro.correo
            itemBinding.tvTelefono.text = libro.telefono
            itemBinding.tvCategoria.text = libro.categoria



            itemBinding.vistaFila.setOnClickListener{
                //Creo una accion para navegar a update lugar pasando un argumento lugar
                val action = LibroFragmentDirections
                    .actionNavLibroToUpdateLibroFragment(libro)
                //Efectivamente se pasa al fragmento...
                itemView.findNavController().navigate(action)
            }

        }
    }

    //La lista donde estan los objettos Lugar a dibujarse...
    private var listaLibros = emptyList<Libro>()

    //Esta funcion crea "cajitas" para cada lugar... en memoria
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
    val itemBinding = LibroFilaBinding
        .inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return LibroViewHolder(itemBinding)
    }

    //Esta funcion toma un lugar y lo envia a dibujar
    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
    val lugar = listaLibros[position]
        holder.dibuja(lugar)
    }

    //Esta funcion devuelve la cantidad de elementos a dibujar...(cajitas)
    override fun getItemCount(): Int {
        return listaLibros.size
    }

    fun setListaLibros(libros: List<Libro>){
        this.listaLibros = libros
        notifyDataSetChanged()
    }
}