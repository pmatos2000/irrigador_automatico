package br.labmult.irrigadorautomatico

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Message
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class ConexaoBluetooth(val dispositivo: BluetoothInfo): Thread() {

    private var btSocket: BluetoothSocket? = null
    private var entrada: InputStream? = null
    private var saida: OutputStream? = null
    private val UUID_SERIAL = "00001101-0000-1000-8000-00805F9B34FB"
    private var _executando = false
    var executando: Boolean = false
        get() = _executando
    companion object {
        val CONEXAO_ERRO = "---ERRO_CONEXAO"
        val CONEXAO_SUCESSO = "---SUCESSO_CONEXAO"
    }

    fun paraControleLampadaActivity(data: ByteArray){
        val message = Message()
        val bundle = Bundle()
        bundle.putByteArray("data", data);
        message.data = bundle
        MainActivity.handler.sendMessage(message)
    }

    override fun run() {
        super.run()
        val btAdapter = BluetoothAdapter.getDefaultAdapter()
        try {
            val btDevice = btAdapter.getRemoteDevice(dispositivo.endereco)
            Log.d("teste", dispositivo.endereco)
            btAdapter.cancelDiscovery()
            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.randomUUID());
        }
        catch (e: IOException){
            e.printStackTrace()
            btSocket = null;
            paraControleLampadaActivity(CONEXAO_ERRO.toByteArray())
        }

        try {
            if(btSocket != null){
                btSocket!!.connect()
                entrada = btSocket!!.inputStream
                saida = btSocket!!.outputStream
                var bytes = 0
                var buffer = ByteArray(1024)
                _executando = true
                while (_executando){
                    bytes = entrada!!.read(buffer)
                    paraControleLampadaActivity(buffer.copyOfRange(0, bytes));
                }
            }
        }
        catch (e: IOException){
            e.printStackTrace()
            btSocket!!.close()
            paraControleLampadaActivity(CONEXAO_ERRO.toByteArray())
        }

    }
}