package com.example.shoppingcart.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.FragmentHomeBinding
import com.example.shoppingcart.domain.models.ProductsItem
import com.example.shoppingcart.extensions.observe
import com.example.shoppingcart.ui.base.BaseFragment
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.extensions.gone
import com.example.shoppingcart.extensions.visible
import com.example.shoppingcart.ui.car.DIALOG_CAR
import com.example.shoppingcart.ui.car.DIALOG_CAR_CHECKOUT
import com.example.shoppingcart.ui.car.FragmentCar
import com.example.shoppingcart.ui.car.FragmentCarCheckout
import com.example.shoppingcart.ui.car.FragmentCarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(){

    private val viewModel: HomeViewModel by viewModels()
    private val viewModelCart: FragmentCarViewModel by viewModels()
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter(ProductsAdapterManager()) }

    override fun initBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initView(view: View, savedInstanceState: Bundle?) {
        progressBar = binding.progressBar.root
        initRecyclerView()
        setObservers()
        setObserverCart()
        setSearchListener()
        goCart()
    }

    private fun initRecyclerView(){
        binding.productsRecycler.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setObservers(){
        with(viewModel) {
            observe(products, ::loadData)
        }
        viewModel.getProductsList()
    }

    private fun loadData(response: Result<List<ProductsItem>>?){
        when (response) {
            is Result.Loading -> {
                showProgress(response.show)
            }

            is Result.Success -> {
                viewModel.originProducts.value = response.data
                productsAdapter.swapItems(response.data)
            }

            is Result.Failure -> {
                Toast.makeText(requireActivity(), getString(R.string.error_endpoint), Toast.LENGTH_LONG).show()
            }

            else -> {
            }
        }
    }

    inner class ProductsAdapterManager : ProductsAdapter.ProductManagerAdapter{
        override fun onProductSelect(product: ProductsItem) {
            FragmentCar(product).show(childFragmentManager, DIALOG_CAR)
        }
    }

    private fun setSearchListener() {
        

        binding.edtSearch.setOnClickListener {
            binding.edtSearch.isIconified=false
        }

        binding.edtSearch.setOnCloseListener {
            onSearch("")
            false
        }

        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onSearch(newText ?: "")
                return true
            }
        })

    }

    private fun onSearch(data:String){

        productsAdapter.swapItems(viewModel.filterData(data))
    }

    private fun setObserverCart(){
        viewModelCart.products.observe(this, Observer {

            if(it.size>0){
                binding.cart.visible()
                val image = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_loaded_car)

                Glide
                    .with(requireActivity())
                    .load(image)
                    .fitCenter()
                    .into(binding.cart)
            }else{
                binding.cart.gone()
            }



        })
    }

    private fun goCart(){
        binding.cart.setOnClickListener {
            FragmentCarCheckout().show(childFragmentManager, DIALOG_CAR_CHECKOUT)
        }

    }

}