package br.labmult.irrigadorautomatico.ui.controlemanual

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.labmult.irrigadorautomatico.R

class ControleManualFragment : Fragment() {

    companion object {
        fun newInstance() = ControleManualFragment()
    }

    private lateinit var viewModel: ControleManualViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_controle_manual, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ControleManualViewModel::class.java)
        // TODO: Use the ViewModel
    }

}