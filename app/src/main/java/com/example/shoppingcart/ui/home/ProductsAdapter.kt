package com.example.shoppingcart.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ViewItemProductsListBinding
import com.example.shoppingcart.domain.models.ProductsItem

class ProductsAdapter(private val manager: ProductManagerAdapter) : RecyclerView.Adapter<ProductsAdapter.ContactsViewHolder>(){

    private val items = mutableListOf<ProductsItem>()
    lateinit var context: Context

    interface ProductManagerAdapter {
        fun onProductSelect(product: ProductsItem)
    }

    class ContactsViewHolder(itemView: ViewItemProductsListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var mView: ViewItemProductsListBinding = itemView
    }

    fun swapItems(new: List<ProductsItem>) {
        items.clear()
        items.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        context = parent.context

        val mView: ViewItemProductsListBinding = ViewItemProductsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ContactsViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {

        with(items[position]){
            with(holder.mView){
                name.text=nombre
                price.text= precio.toString()

                Glide
                    .with(context)
                    .load(imagen)
                    .fitCenter()
                    .placeholder(R.drawable.ic_car)
                    .into(image)
            }
        }

        holder.mView.container.setOnClickListener {
            manager.onProductSelect(items[position])
        }

    }



}