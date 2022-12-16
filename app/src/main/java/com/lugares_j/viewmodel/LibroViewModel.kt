package com.lugares_j.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares_j.data.LibroDao
import com.lugares_j.model.Libro
import com.lugares_j.repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibroViewModel(application: Application) : AndroidViewModel(application) {

    private val libroRepository: LibroRepository = LibroRepository(LibroDao())

    val getLibros : MutableLiveData<List<Libro>> = libroRepository.getLibros

    fun saveLibro(libro: Libro){
        viewModelScope.launch ( Dispatchers.IO ) {
            libroRepository.saveLibro(libro)
        }
    }

    fun deleteLibro(libro: Libro){
        viewModelScope.launch ( Dispatchers.IO ) {
            libroRepository.deleteLibro(libro)
        }
    }
}