package com.nc.med.util;

public interface WebUrl {

    String USER = "user";
    String USER_FORGET_PASSWORD = "user/forgetPassword/";
    String USER_FORGET_PASSWORD_EMAILID = "user/forgetPassword/{emailId}";
    String USER_BY_EMAILID_PASSWORD = "user/byEmailAndPassword";

    String CATEGORY_BY_NAME = "/category/byName/";
    String CATEGORY = "/category";

    String PRODUCTS = "/products";
    String PRODUCT_AND_CATEGORYID = "/product/{categoryID}";
    String PRODUCT_BY_PRODUCTID = "/products/{productID}";
    String PRODUCTS_ADD = "/productsAdd";
    String PRODUCTS_REMOVE = "/productsDelete";
    String PRODUCTS_REMOVE_TEMP = "/productsTempDelete";
    String PRODUCTS_GET_TEMP = "/productsGetTemp";

    String CART_BY_USERID = "/cart/{userID}";
    String CART_BY_CARTID = "/cart/{cartID}";
    String CARTS_ADD = "/cartsAdd";
    String CARTS_REMOVE = "/cartsDelete";
    String CARTS_REPORT = "/cartsReport/{startDate}/{endDate}";

    String ADVANCED_SEARCH = "/advancedSearch/{categoryID}/{productName}";
}
