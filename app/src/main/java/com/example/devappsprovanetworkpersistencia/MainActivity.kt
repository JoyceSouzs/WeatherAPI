package com.example.devappsprovanetworkpersistencia

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.devappsprovanetworkpersistencia.api.RetrofitManager
import com.example.devappsprovanetworkpersistencia.database.RoomManager
import com.example.devappsprovanetworkpersistencia.entity.City
import com.example.devappsprovanetworkpersistencia.entity.FavoritosCidade
import com.example.devappsprovanetworkpersistencia.entity.Resultado
import com.example.devappsprovanetworkpersistencia.features.list.WeatherAdapter
import com.example.devappsprovanetworkpersistencia.features.preferences.PreferencesActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val adapter = WeatherAdapter { saveFavorite(it)  }
    var unidade: String? = null
    var linguagem: String? = null
    val listaCodigos = arrayListOf<Int>()
    var listaFav: List<FavoritosCidade>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoritos()
        initUI()
        unidade = pegarUni()
        linguagem = pegarLing()
        txt.text = recuperar().toString()

    }

    /**
     *  Salva as cidades no banco
     */
    private fun saveFavorite(city: City) {
        RoomManager.instance(this).favoritosDao().apply {
            val (id, name) = city
            insert(FavoritosCidade(id, name))

            selectAll().forEach {
                Log.d("saveFavorite", it.id.toString() + " Cidade: " + it.name)
            }

        }
    }

    /**
     *  Remove as cidades no banco
     */
    private fun removeFavorito(city: City) {
        RoomManager.instance(this).favoritosDao().apply {
            val (id, name) = city
            delete(FavoritosCidade(id, name))

            selectAll().forEach {
                Log.d("saveFavorite", it.id.toString() + " Cidade: " + it.name)
            }

        }
    }


    /**
     *  Recupera as cidades salvas no banco
     */
    fun recuperar(): List<Int>? {

        RoomManager.instance(this).favoritosDao().apply {

            listaFav = selectAll()

            listaFav?.forEach {

                listaCodigos.addAll(listOf(it.id))
                Log.d("selecionar", it.id.toString())

                }

            }

            return  listaCodigos
        }


    private fun refreshList() {
        val rm = RetrofitManager()
        val call = rm.weatherService().find(
            edCidade.text.toString(),
            unidade.toString(),
            linguagem.toString(),
            Constant.key
        )


        call.enqueue(object : Callback<Resultado> {

            override fun onFailure(call: Call<Resultado>, t: Throwable) {

            }

            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                if (response.isSuccessful) {
                    response.body()?.let { Resultado ->
                        adapter.updataData(Resultado.list)

                        Resultado.list.forEach { city ->
                            Log.d("ok", "${city.id} - ${city.name}")
                        }
                    }
                }
            }

        })
    }

    private fun favoritos() {
        val codigos = recuperar()

        val rm = RetrofitManager()
        val call = rm.weatherService().group(
            TextUtils.join(",", codigos),
            unidade.toString(),
            linguagem.toString(),
            Constant.key
        )


        call.enqueue(object : Callback<Resultado> {

            override fun onFailure(call: Call<Resultado>, t: Throwable) {

            }

            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                if (response.isSuccessful) {
                    response.body()?.let { Resultado ->
                        adapter.updataData(Resultado.list)

                        Resultado.list.forEach { city ->
                            Log.d("ok", "${city.id} - ${city.name}")
                        }
                    }
                }
            }

        })

        // RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    /**
     * Inicializa os componentes da UI
     */
    private fun initUI() {
        btPesquisar.setOnClickListener {
            if (conexao() &&  edCidade.text.toString() == "") {
                favoritos()
            } else if (conexao() &&  edCidade.text.toString() != ""){
                refreshList()
            }else{
                Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show()
            }
        }

        // RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }



    /**
     *  Métodos para pegar Unidade e Linguagem no SharedPreferences
     */
    fun pegarUni(): String {

        val bundle: Bundle? = intent.extras
        val unidade: String? = bundle?.getString("key")
        return unidade.toString()
    }


    fun pegarLing(): String {

        val bundle: Bundle? = intent.extras
        val ling: String? = bundle?.getString("key_ling")
        return ling.toString()
    }


    /**
     * Checa se tem conexão de dados
     * Estudar ConnectivityManager um pouco mais...
     */
    private fun conexao(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

    }


    /**
     * Métodos do Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.config, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.settings_action) {
            startActivity(Intent(this, PreferencesActivity::class.java))
        }

        return true
    }
}
