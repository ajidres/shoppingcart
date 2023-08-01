package com.example.shoppingcart.ui.car

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ViewItemProductsCartListBinding
import com.example.shoppingcart.domain.models.ProductsItem

class ProductsCartAdapter(private val manager: ProductManagerAdapter) : RecyclerView.Adapter<ProductsCartAdapter.ContactsViewHolder>(){

    private val items = mutableListOf<ProductsItem>()
    lateinit var context: Context


    interface ProductManagerAdapter {
        fun onProductSelect(product: ProductsItem)
    }

    class ContactsViewHolder(itemView: ViewItemProductsCartListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var mView: ViewItemProductsCartListBinding = itemView
    }

    fun swapItems(new: List<ProductsItem>) {
        items.clear()
        items.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        context = parent.context

        val mView: ViewItemProductsCartListBinding = ViewItemProductsCartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ContactsViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {

        with(items[position]){
            with(holder.mView){
                name.text=nombre
                price.text= "$precio    X    $quantity    =    ${precio*quantity}"

                Glide
                    .with(context)
                    .load(imagen)
                    .fitCenter()
                    .placeholder(R.drawable.ic_car)
                    .into(image)
            }
        }

        holder.mView.imageDelete.setOnClickListener {
            manager.onProductSelect(items[position])
        }

    }





}