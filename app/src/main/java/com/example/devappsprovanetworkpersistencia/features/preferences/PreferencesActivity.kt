package com.example.devappsprovanetworkpersistencia.features.preferences

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.devappsprovanetworkpersistencia.Constant
import com.example.devappsprovanetworkpersistencia.MainActivity
import com.example.devappsprovanetworkpersistencia.R
import kotlinx.android.synthetic.main.activity_preferences.*

class PreferencesActivity : AppCompatActivity() {

    //Estudar o Lazy
    //Obter a instância da SharedPreferences
     val prefs by lazy{
        getSharedPreferences(Constant.PREF_NAME,Context.MODE_PRIVATE)
    }

    var unid : String = ""
    var ling : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        ler()
        btSalvar.setOnClickListener { salvar() }
    }


  private fun salvar(){

        val editor = prefs.edit()

        rgTemp.check(
            if (rbTempC.isChecked){
                unid = "metric"
                editor.putString(Constant.PREF_TEMP_C,unid)
                Log.d("salvar",  unid)
            }else{
                unid = "imperial"
                editor.putString(Constant.PREF_TEMP_F,unid)
                Log.d("salvar",  unid)
            })

        rgLing.check(
            if (rb_desc_pt.isChecked){
                ling = "pt"
                editor.putString(Constant.PREF_LANG_PT,ling)
                Log.d("salvar",  ling)
            }else{
                ling = "en"
                editor.putString(Constant.PREF_LANG_EN,ling)
                Log.d("salvar",  ling)
            })
        editor.apply()
        passandoUnidade()


    }

    fun ler(){

        unid = (prefs.getString(Constant.PREF_TEMP_C, "C"))
        ling = (prefs.getString(Constant.PREF_LANG_PT, "pt"))
        Log.d("ler",  unid + ling)

    }

    fun passandoUnidade(){

        val intent= Intent(this,MainActivity::class.java)
        intent.putExtra("key",unid)
        intent.putExtra("key_ling",ling)
        startActivity(intent)
    }


}
