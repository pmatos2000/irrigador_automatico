package br.labmult.irrigadorautomatico.ui.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.labmult.irrigadorautomatico.R

class InicioFragment : Fragment() {

    private lateinit var homeViewModel: InicioViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(InicioViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_inicio, container, false)

        val cardConexao = root.findViewById<CardView>(R.id.cardConexao);
        cardConexao.setOnClickListener {
            findNavController().navigate(R.id.acao_card_conexao)
        }

        return root
    }
}