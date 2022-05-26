package com.example.challenge5_afifuddin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge5_afifuddin.databinding.ActivityItemBinding
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_now_showing.Result

class NowMovieShowingAdapter(private val onItemCLick: onClickListener) :
    RecyclerView.Adapter<NowMovieShowingAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }
    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(value: List<Result>?) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): NowMovieShowingAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ActivityItemBinding.inflate(inflater, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }
    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding:ActivityItemBinding ) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(data :Result){
            binding.apply {
                tvJudul.text = data.title
                tvTanggal.text = data.releaseDate
                Glide.with(root)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .into(ivListMovie)
                root.setOnClickListener {
                    onItemCLick.onClickItem(data)
                }
            }
        }
    }


    interface onClickListener {
        fun onClickItem(data: Result)

    }

}


