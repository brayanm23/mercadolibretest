package com.test.mercadolibre.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.mercadolibre.R
import com.test.mercadolibre.base.extensions.loadImageURL
import com.test.mercadolibre.base.extensions.replaceHttpToHttps
import com.test.mercadolibre.model.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_product.view.*

class ProductAdapter (private val onClickItemListener: OnClickItemListener
) : RecyclerView.Adapter<ProductAdapter.ProductItemViewHolder>() {

    private var products = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product, parent, false)
        return ProductItemViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int ) {
        holder.bind(products[position])
    }

    fun setData(list: List<Product>) {
        products.addAll(list)
        notifyDataSetChanged()
    }

    inner class ProductItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(product: Product) {
            containerView.title.text = product.title
            containerView.condition.text = product.condition
            containerView.price.text = product.price.toString()
            containerView.imageProduct.loadImageURL(product.thumbnail.replaceHttpToHttps())
            containerView.setOnClickListener {
                onClickItemListener.onItemClick(product)
            }
        }
    }

    interface OnClickItemListener {
        fun onItemClick(product: Product)
    }
}