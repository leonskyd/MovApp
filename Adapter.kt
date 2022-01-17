package com.example.mymovie.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.mymovie.R


class Adapter : Adapter<com.example.mymovie.ui.main.Adapter.ListViewHolder>() {

    private var movie: List<Movie> = listOf()
    var listener: OnItemClick? = null

    fun setMovie(data: List<Movie>) {
        movie = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(movie[position])
    }

    override fun getItemCount(): Int = movie.size


    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.movie_item_textView)
                    .text = movie.title + movie.release.toString()
            itemView.setOnClickListener {
                listener?.OnClick(movie)
            }
        }
    }

    fun interface OnItemClick {
        fun OnClick(movie: Movie)
    }
}



