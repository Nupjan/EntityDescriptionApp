package com.example.entitydescriptionapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.entitydescriptionapp.databinding.ItemEntityBinding


class EntityAdapter(private val items: List<Map<String, Any?>>) :
    RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(val binding: ItemEntityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val item = items[position]

        val displayTitle = item.values.firstOrNull { value ->
            value is String && value.toString().isNotBlank()
        }?.toString() ?: "Untitled"

        holder.binding.textProperty1.text = displayTitle

        holder.binding.root.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailsActivity::class.java)
            item.forEach { (key, value) ->
                intent.putExtra(key, value?.toString() ?: "N/A")
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
