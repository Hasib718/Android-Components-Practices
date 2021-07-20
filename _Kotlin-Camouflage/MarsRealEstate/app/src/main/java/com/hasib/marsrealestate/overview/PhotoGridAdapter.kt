package com.hasib.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hasib.marsrealestate.databinding.GridViewItemBinding
import com.hasib.marsrealestate.network.MarsProperty

class PhotoGridAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class MarsPropertyViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            marsProperty: MarsProperty,
            onClickListener: OnClickListener = OnClickListener { }
        ) {
            binding.property = marsProperty
            binding.marsImage.setOnClickListener { onClickListener.onClick(marsProperty) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MarsPropertyViewHolder {
                val binding =
                    GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MarsPropertyViewHolder(binding)
            }
        }
    }

    class OnClickListener(private val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }
}
