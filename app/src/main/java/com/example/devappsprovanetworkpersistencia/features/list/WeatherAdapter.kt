package com.example.devappsprovanetworkpersistencia.features.list

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.devappsprovanetworkpersistencia.R
import com.example.devappsprovanetworkpersistencia.entity.City
import kotlinx.android.synthetic.main.city_layout.view.*
import android.widget.Toast.makeText as makeText1

class WeatherAdapter(private val callback: (City) -> Unit) : RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    private var list: List<City>? = null
    private var posicao: Int = 0


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.city_layout, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(vh: MyViewHolder, position: Int) {
        list?.let {
            vh.bind(it[position], callback)
        }

        list?.isEmpty()
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(city: City, callback: (City) -> Unit) {
            itemView.txtRegiao.text = "${city.name}, ${city.pais.country}"

            //Lib-> biblioteca de armazenamento e cache de imagens
            //Carregando a imagem da API
            Glide.with(itemView.context)
                .load("http://openweathermap.org/img/w/${city.weathers[0].icon}.png")
                .placeholder(R.drawable.w_01d)
                .into(itemView.imgIcon)

            itemView.txtDescricaoTempo.text = "${city.weathers[0].description}"

            //take() retorna os caracteres da sequência
            itemView.txtTempo.text = "${city.temperatura.temp.take(2)}"

            itemView.txtDetalhes.text =
                "wind ${city.vento.speed} m/s | nuvens ${city.nuvem.all}% | ${city.temperatura.pressure} hpa"


            itemView.imgFav.setOnClickListener {
                // mudando a cor do favorito
                itemView.imgFav.setImageResource(R.drawable.ic_action_name_amarelo)
                Log.d("ci", "Clicado!" + city.id + " Nome: " + city.name)
                callback(city)
            }

            itemView.imgFav.setImageResource(R.drawable.ic_action_name)

        }


        private fun AlterandoImagem(favorite: Boolean) {
            if (favorite) {
                itemView.imgFav.setImageBitmap(
                    BitmapFactory.decodeResource(
                        itemView.context.resources,
                        R.drawable.ic_action_name_amarelo
                    )
                )
            } else {
                itemView.imgFav.setImageBitmap(
                    BitmapFactory.decodeResource(
                        itemView.context.resources,
                        R.drawable.ic_action_name
                    )
                )
            }
        }
    }

    fun updataData(list: List<City>) {
        this.list = list
        notifyDataSetChanged()
    }

}