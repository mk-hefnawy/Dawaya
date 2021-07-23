package com.example.dawaya.interfaces;

import com.example.dawaya.models.ProductModel;

public interface ProductsInterface {
    void onAddToCartClicked(ProductModel product);
    void onFavouriteClicked(ProductModel product);

}
