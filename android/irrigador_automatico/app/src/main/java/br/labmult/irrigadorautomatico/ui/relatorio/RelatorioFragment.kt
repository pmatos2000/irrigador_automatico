package br.labmult.irrigadorautomatico.ui.relatorio

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import br.labmult.irrigadorautomatico.R

class RelatorioFragment : Fragment() {

    companion object {
        fun newInstance() = RelatorioFragment()
    }

    private lateinit var viewModel: RelatorioViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.relatorio_fragment, container, false)

        val webView = root.findViewById<WebView>(R.id.webView);

        val strURL = "https://chart.googleapis.com/chart?" +
                "cht=lc&" + //define o tipo do gráfico "linha"
                "chxt=x,y&" + //imprime os valores dos eixos X, Y
                "chs=300x300&" + //define o tamanho da imagem
                "chd=t:10,83,42,27,17,75&" + //valor de cada coluna do gráfico
                "chl=25|26|27|28|29|30&" + //rótulo para cada coluna
                "chxr=1,0,100&" + //define o valor de início e fim do eixo
                "chds=0,100&" + //define o valor de escala dos dados
                "chg=0,5,0,0&" + //desenha linha horizontal na grade
                "chco=FFFFFFFF&" + //cor da linha do gráfico
                "chm=B,EE6200FF,0,0,0"; //fundo verde;

        webView.loadUrl(strURL);

        return root;

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RelatorioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}