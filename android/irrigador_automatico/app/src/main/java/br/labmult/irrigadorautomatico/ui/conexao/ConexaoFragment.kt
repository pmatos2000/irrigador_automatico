package br.labmult.irrigadorautomatico.ui.conexao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.labmult.irrigadorautomatico.R

class ConexaoFragment : Fragment() {

    private lateinit var galleryViewModel: ConexaoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(ConexaoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_conexao, container, false)
        return root
    }
}