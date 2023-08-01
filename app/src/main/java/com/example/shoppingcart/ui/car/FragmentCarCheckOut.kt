package com.example.shoppingcart.ui.car

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.ProductsItem
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.FragmentCartCheckoutBinding
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.domain.models.Payment
import com.example.shoppingcart.extensions.gone
import com.example.shoppingcart.extensions.observe
import com.example.shoppingcart.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

const val DIALOG_CAR_CHECKOUT = "DIALOG_CAR_CHECKOUT"

@AndroidEntryPoint
class FragmentCarCheckout() : DialogFragment(){

    private val viewModel: FragmentCarViewModel by viewModels()
    private lateinit var viewDialog: FragmentCartCheckoutBinding
    private val productsAdapter: ProductsCartAdapter by lazy { ProductsCartAdapter(ProductsAdapterManager()) }

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
        viewDialog = FragmentCartCheckoutBinding.inflate(layoutInflater)
        return viewDialog.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDialog.handler = this
        initRecyclerView()
        setObserver()
    }


    fun close(){
        this@FragmentCarCheckout.dismiss()
    }

    private fun initRecyclerView(){
        viewDialog.productsRecycler.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setObserver(){
        with(viewModel) {
            observe(payment, ::showResult)
        }
        viewModel.products.observe(this, Observer {
            viewModel.originProducts.value = it.toList()
            filterList(it)

        })
    }

    private fun showResult(response: Result<Payment>?){
        when (response) {
            is Result.Loading -> {
                if(response.show){
                    viewDialog.progressBar.root.visible()
                }else{
                    viewDialog.progressBar.root.gone()
                }
            }

            is Result.Success -> {
                viewModel.deleteAllCart()
                Toast.makeText(requireActivity(), getString(R.string.purchase), Toast.LENGTH_LONG).show()
                this@FragmentCarCheckout.dismiss()
            }

            is Result.Failure -> {
                Toast.makeText(requireActivity(), getString(R.string.error_endpoint), Toast.LENGTH_LONG).show()
            }

            else -> {
            }
        }
    }

    private fun filterList(data: MutableList<ProductsItem>) {
        val listProducts:MutableList<com.example.shoppingcart.domain.models.ProductsItem> = mutableListOf()

        var total = 0.0

        for(element in data){

            if(listProducts.none { list -> list.id == element.id }){

                val quantity=data.filter { list -> list.id == element.id  }.size

                listProducts.add(com.example.shoppingcart.domain.models.ProductsItem(
                    id=element.id,
                    nombre =element.name,
                    imagen =element.image,
                    precio =element.price,
                    descripcion =element.description,
                    quantity = quantity
                ))

                total += element.price * quantity
            }

        }

        viewDialog.total.text = getString(R.string.total, total)

        if(listProducts.isEmpty()){
            viewDialog.buttonPay.gone()
        }else{
            viewDialog.buttonPay.setOnClickListener {
                viewModel.payment()
            }
        }

        productsAdapter.swapItems(listProducts)
    }


    inner class ProductsAdapterManager : ProductsCartAdapter.ProductManagerAdapter{
        override fun onProductSelect(product: com.example.shoppingcart.domain.models.ProductsItem) {

            val original=viewModel.originProducts.value?.toMutableList()

            original?.removeIf { it.id ==product.id }

            viewModel.deleteAllCart()

            viewModel.addProducts(
                original!!
            )


        }

    }


}