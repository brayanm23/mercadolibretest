package com.test.mercadolibre.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.mercadolibre.R
import com.test.mercadolibre.base.view.BaseFragment
import com.test.mercadolibre.model.Product
import com.test.mercadolibre.view.SearchFragment.Companion.SEARCH_PRODUCT
import com.test.mercadolibre.view.adapter.ProductAdapter
import com.test.mercadolibre.viewModel.ProductListViewModel
import com.test.mercadolibre.viewModel.ProductListViewModel.State
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment: BaseFragment<ProductListViewModel, State>(), ProductAdapter.OnClickItemListener {

    companion object {
        const val ERROR_DESCRIPTION = "error_description_key"
        const val PRODUCT = "product_key"
    }

    override fun getLayoutId(): Int = R.layout.fragment_product_list

    override fun onStateChanged(state: State) {
        when (state) {
            is State.Success -> goToProductList(state.response.results)
            is State.Error -> goToUnespectedError(state.error)
            is State.ListIsEmpty -> showMessageListEmpty()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (viewModel.getState().value) {
            is State.Loading ->  arguments?.let { viewModel.searchProduct(it.getString(SEARCH_PRODUCT,"")) }
            is State.Success -> goToProductList((viewModel.getState().value as State.Success).response.results)
            is State.ListIsEmpty -> showMessageListEmpty()
            is State.Error -> goToUnespectedError((viewModel.getState().value as State.Error).error)
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onItemClick(product: Product) {
        val args = Bundle()
        args.putSerializable(PRODUCT, product)
        findNavController().navigate(R.id.action_productListFragment_to_detailProductFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (productList.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()?.let { viewModel.savePositionProductList(it) }
    }

    private fun showMessageListEmpty() {
        findNavController().navigate(R.id.action_productListFragment_to_resultNoFoundFragment)
    }

    private fun goToProductList(products: List<Product>) {
        showList()
        val productAdapter = ProductAdapter(this)
        productList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        productAdapter.setData(products)
        productList.scrollToPosition(viewModel.getPositionProductList())
    }

    private fun showList(){
        circleAnimation.visibility = View.INVISIBLE
        productList.visibility = View.VISIBLE
    }

    private fun goToUnespectedError(error: String){
        val args = Bundle()
        args.putString(ERROR_DESCRIPTION, error)
        findNavController().navigate(R.id.action_productListFragment_to_unespectedErrorFragment, args)
    }

}