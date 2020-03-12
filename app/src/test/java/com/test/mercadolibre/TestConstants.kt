package com.test.mercadolibre

import com.test.mercadolibre.model.Product
import com.test.mercadolibre.model.ResponseProduct

object TestConstants {
    const val ERROR_MESSAGE = "Error"
    const val PRODUCT_SEARCH = "moto"
    val RESPONSE_PRODUCT = ResponseProduct(
        "",
        listOf(
            Product("2222", "Aysa","","","","",1,2.222F, true),
            Product("2222", "Aysa","","","","",2,2.222F, true),
            Product("2222", "Aysa","","","","",2,2.222F, true)
        )
    )

    val RESPONSE_PRODUCT_RESULT_EMPTY = ResponseProduct(
        "",
        ArrayList<Product>()
    )
}
