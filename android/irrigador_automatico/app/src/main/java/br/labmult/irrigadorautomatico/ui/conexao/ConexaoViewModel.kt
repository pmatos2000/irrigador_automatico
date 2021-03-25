package br.labmult.irrigadorautomatico.ui.conexao


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.labmult.irrigadorautomatico.BluetoothInfo


class ConexaoViewModel : ViewModel() {

    val listaDispositivos =  MutableLiveData<MutableList<BluetoothInfo>>()

    init {
        listaDispositivos.value = ArrayList()
    }
    fun addDispositivo(dispositivo: BluetoothInfo){
        Log.d("teste", "addDispositivo");
        listaDispositivos.value = arrayListOf(dispositivo)
    }
}