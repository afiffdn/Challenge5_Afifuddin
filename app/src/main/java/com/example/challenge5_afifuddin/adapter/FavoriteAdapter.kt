package com.example.challenge5_afifuddin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge5_afifuddin.databinding.ActivityItemBinding
import com.example.challenge5_afifuddin.favorite.Favorite

class FavoriteAdapter(private val onItemClick: OnClickListener): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<Favorite>(){
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id_favorite == newItem.id_favorite
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<Favorite>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ActivityItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ActivityItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: Favorite){
            binding.apply {
                tvJudul.text = data.movie_name
                tvTanggal.text = data.tahun
                Glide.with(binding.root)
                    .load( data.image)
                    .centerCrop()
                    .into(ivListMovie)
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener {
        fun onClickItem(data: Favorite)
    }
}


