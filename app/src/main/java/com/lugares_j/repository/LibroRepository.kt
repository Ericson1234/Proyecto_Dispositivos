package com.lugares_j.repository

import androidx.lifecycle.MutableLiveData
import com.lugares_j.data.LibroDao
import com.lugares_j.model.Libro

class LibroRepository(private val libroDao: LibroDao) {

    fun saveLibro(libro : Libro) {
        libroDao.saveLibro(libro)
    }
;
    fun deleteLibro(libro: Libro){
        libroDao.deleteLibro(libro)

    }

    val getLibros : MutableLiveData<List<Libro>> = libroDao.getLibros()
}