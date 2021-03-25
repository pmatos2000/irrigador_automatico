package br.labmult.irrigadorautomatico.ui.configuracao

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.labmult.irrigadorautomatico.R
import java.text.SimpleDateFormat
import java.util.*

class ConfiguracaoFragment : Fragment() {

    private lateinit var slideshowViewModel: ConfiguracaoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProvider(this).get(ConfiguracaoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_configuracao, container, false)
        val textHoraAtual = root.findViewById<TextView>(R.id.textHoraAtual);
        val textDataAtual = root.findViewById<TextView>(R.id.textDataAtual)
        val btnAjustarHora = root.findViewById<Button>(R.id.btnAjustarHora);
        val btnAjustarData = root.findViewById<Button>(R.id.btnAjustarData);
        val cal = Calendar.getInstance()
        btnAjustarHora.setOnClickListener {
            val seletorHoras = TimePickerDialog.OnTimeSetListener{ timePicker, hora, min ->
                cal.set(Calendar.HOUR_OF_DAY, hora)
                cal.set(Calendar.MINUTE, min)
                textHoraAtual.text = SimpleDateFormat("HH:mm").format(cal.time);
            }
            TimePickerDialog(context, seletorHoras, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        btnAjustarData.setOnClickListener {
            val seletorData = DatePickerDialog.OnDateSetListener { dataPicker, ano, mes, dia ->
                cal.set(Calendar.YEAR, ano)
                cal.set(Calendar.MONTH, mes)
                cal.set(Calendar.DAY_OF_MONTH, dia)
                textDataAtual.text = SimpleDateFormat("dd/MM/YYYY").format(cal.time)
            }
            activity?.let { it1 -> DatePickerDialog(it1, seletorData, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show() }
        }
        return root
    }
}