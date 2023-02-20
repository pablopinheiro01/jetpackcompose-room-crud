package br.com.alura.helloapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val contatoDao = HelloAppDatabase.getDatabase(this).contatoDao()
//
//        CoroutineScope(IO).launch {
////            contatoDao.insere(contatosExemplo.first())
//            val buscaTodos = contatoDao.buscaTodos()
//            Log.i(TAG_LISTA_ACTIVITY, "onCreate: buscando todos os dados")
//            Log.i(TAG_LISTA_ACTIVITY, "onCreate: ${buscaTodos}")
//        }


        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                HelloAppNavHost(
                    navController = navController,
                )
            }
        }
    }

    companion object{
        const val TAG_LISTA_ACTIVITY = "ListaContatosActivity"

    }
}