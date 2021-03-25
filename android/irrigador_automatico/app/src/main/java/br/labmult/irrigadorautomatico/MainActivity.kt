package br.labmult.irrigadorautomatico

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView


class MainActivity : ComunicacaoComum, AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    var bluetoothInfo: BluetoothInfo? = null
    var conexaoBluetooth: ConexaoBluetooth? = null

    companion object {
        var ref: MainActivity? = null
        var fragmentAtual: EnviaMensagemParaFragment? = null
        var mensagem: String? = ""
        val handler = object: Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val bundle = msg.getData()
                val data = bundle.getByteArray("data")
                mensagem = data?.let {
                    String(it)
                }
                enviaMsgFragment()
            }
        }
        fun enviaMsgFragment(){
            val navHostFragment = ref?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment)
            val fragmentAtual = navHostFragment?.childFragmentManager?.fragments?.get(0)
            if(fragmentAtual is EnviaMensagemParaFragment){
                mensagem?.let { fragmentAtual!!.mensagem(it) }
            }
            Log.d("teste", fragmentAtual.toString())
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        ref = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_inicio,
                        R.id.nav_conexao,
                        R.id.nav_configuracao,
                        R.id.nav_relatorio,
                        R.id.nav_controle),
                drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun modificaDispositivoBluetooth(dispositivo: BluetoothInfo) {
        bluetoothInfo = dispositivo
        conexaoBluetooth = ConexaoBluetooth(dispositivo)
        conexaoBluetooth!!.start()
    }

    override fun obterDispositivoBluetoot(): BluetoothInfo? {
        return bluetoothInfo
    }

}