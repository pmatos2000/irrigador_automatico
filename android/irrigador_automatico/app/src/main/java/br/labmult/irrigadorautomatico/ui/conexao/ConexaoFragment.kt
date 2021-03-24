package br.labmult.irrigadorautomatico.ui.conexao

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.labmult.irrigadorautomatico.R

class ConexaoFragment : Fragment() {

    private lateinit var conexaoViewModel:  ConexaoViewModel

    val REQUEST_ENABLE_BT = 48

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context, intent: Intent) {
            Toast.makeText(context, "Achou", Toast.LENGTH_LONG).show()
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val dispositivo = device?.let { BluetoothInfo(device.name, device.address) }
                    dispositivo?.let { conexaoViewModel.addDispositivo(dispositivo) }
                }
            }
        }
    }

    fun buscarBluetooth(){
        Log.d("teste", "buscarBluetooth");
        //Usa o adaptador Bluetooth padrão para iniciar o processo de descoberta.
        val  btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        else{
            btAdapter?.bondedDevices?.forEach {
                val dispositivo = BluetoothInfo(it.name, it.address)
                conexaoViewModel.addDispositivo(dispositivo)
            }

            btAdapter.startDiscovery()
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            activity?.registerReceiver(broadCastReceiver, filter);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadCastReceiver)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        conexaoViewModel =
                ViewModelProvider(this).get(ConexaoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_conexao, container, false)
        val containerConexoes = root.findViewById<LinearLayout>(R.id.containerConexoes)
        conexaoViewModel.listaDispositivos.observe(viewLifecycleOwner, Observer { listaDispositivos ->
            listaDispositivos.forEach {
                val cardConexao = inflater.inflate(R.layout.card_conexao, null)
                cardConexao.findViewById<TextView>(R.id.nomeDispositivo).text = it.nome
                cardConexao.findViewById<TextView>(R.id.enderecoDispositivo).text = it.endereco
                containerConexoes.addView(cardConexao)
            }
        })

        val btnBuscarConexao = root.findViewById<Button>(R.id.btnBuscarConexao);
        btnBuscarConexao.setOnClickListener {
            containerConexoes.removeAllViews()
            buscarBluetooth()
        }

        return root
    }
}