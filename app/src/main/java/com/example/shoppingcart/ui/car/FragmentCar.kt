package com.example.shoppingcart.ui.car

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.FragmentCarBinding
import com.example.shoppingcart.domain.models.ProductsItem
import dagger.hilt.android.AndroidEntryPoint

const val DIALOG_CAR = "DIALOG_CAR"

@AndroidEntryPoint
class FragmentCar(private val detail:ProductsItem) : DialogFragment(){

    private val viewModel: FragmentCarViewModel by viewModels()
    private lateinit var viewDialog: FragmentCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,
            R.style.FullScreenDialog)

    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDialog = FragmentCarBinding.inflate(layoutInflater)
        return viewDialog.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDialog.handler = this

        setData()

        setObserver()
    }

    private fun setData(){

        with(detail){
            with(viewDialog){
                name.text=nombre
                price.text= precio.toString()
                description.text = descripcion

                Glide
                    .with(requireActivity())
                    .load(imagen)
                    .fitCenter()
                    .placeholder(R.drawable.ic_car)
                    .into(image)
            }
        }
    }

    fun close(){
        this@FragmentCar.dismiss()
    }

    fun car(){
        FragmentCarCheckout().show(childFragmentManager, DIALOG_CAR_CHECKOUT)
    }

    fun setObserver(){
        viewModel.products.observe(this, Observer {
            var image = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_empty_car)
            if(it.size>0){
                image = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_loaded_car)
            }

            Glide
                .with(requireActivity())
                .load(image)
                .fitCenter()
                .into(viewDialog.cart)
        })
    }

    fun addCart(){
        Toast.makeText(requireActivity(), "Product Added to Cart", Toast.LENGTH_LONG).show()
        viewModel.addProducts(
            mutableListOf(
                com.example.shoppingcart.ProductsItem.newBuilder()
                    .setId(detail.id)
                    .setName(detail.nombre)
                    .setDescription(detail.descripcion)
                    .setImage(detail.imagen)
                    .setPrice(detail.precio)
                    .build()
            )
        )
    }


}