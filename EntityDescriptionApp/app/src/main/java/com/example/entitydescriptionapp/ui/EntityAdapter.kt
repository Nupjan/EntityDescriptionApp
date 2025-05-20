package com.example.entitydescriptionapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.entitydescriptionapp.databinding.ItemEntityBinding

class EntityAdapter(
    private val items: List<Map<String, Any?>>
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(
        val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        return EntityViewHolder(
            ItemEntityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val item = items[position]
        bindItemTitle(holder, item)
        setupItemClick(holder, item)
    }

    private fun bindItemTitle(holder: EntityViewHolder, item: Map<String, Any?>) {
        holder.binding.textProperty1.text = findDisplayTitle(item)
    }

    private fun findDisplayTitle(item: Map<String, Any?>): String {
        return item.values.firstOrNull { value ->
            value is String && value.isNotBlank()
        }?.toString() ?: "Untitled"
    }

    private fun setupItemClick(holder: EntityViewHolder, item: Map<String, Any?>) {
        holder.binding.root.setOnClickListener {
            holder.itemView.context.startActivity(
                createDetailsIntent(item, holder)
            )
        }
    }

    private fun createDetailsIntent(item: Map<String, Any?>, holder: EntityViewHolder): Intent {
        return Intent(holder.itemView.context, DetailsActivity::class.java).apply {
            item.forEach { (key, value) ->
                putExtra(key, value?.toString() ?: "N/A")
            }
        }
    }

    override fun getItemCount(): Int = items.size
}