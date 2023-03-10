package br.com.alura.helloapp.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState>
        get() = _uiState.asStateFlow()

    init {
        buscaTodosContatos()
    }

    private fun buscaTodosContatos(){
        viewModelScope.launch {
            val contatos = contatoDao.buscaTodos()

            contatos.collect() { contatosBuscados ->
                _uiState.value = _uiState.value.copy(
                    contatos = contatosBuscados
                )
            }
        }
    }

    fun showPesquisa(){
        buscaTodosContatos()
        _uiState.value = _uiState.value.copy(
            isShowPesquisa = !_uiState.value.isShowPesquisa,
            textoPesquisa = ""
        )
    }

    fun buscaContato(entrada: String) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                textoPesquisa = entrada
            )

            contatoDao.buscaContato(entrada).collect{
                _uiState.value = _uiState.value.copy(
                    contatos = it,
                )
            }
        }
    }

    suspend fun desloga() {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(name = "logado")] = false
        }
    }


}