package com.PingShoping.bean;

import java.util.Objects;

public class CartInfo {

    private Integer cart_id;//购物车单号id
    private Integer user_id;//
    private Integer goods_id;// 商品id
    private String goods_name;// 商品名
    private String goods_desc;// 商品描述
    private String goods_photo;// 商品图像
    private String goods_price;//商品价格
    private String goods_amount;// -- 商品数量
    private String goods_reserve;//  商品库存

    public CartInfo() {
        super();
    }

    public CartInfo(Integer user_id, Integer goods_id, String goods_name, String goods_desc, String goods_photo,
                    String goods_price, String goods_amount, String goods_reserve) {
        super();
        this.user_id = user_id;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.goods_desc = goods_desc;
        this.goods_photo = goods_photo;
        this.goods_price = goods_price;
        this.goods_amount = goods_amount;
        this.goods_reserve = goods_reserve;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_photo() {
        return goods_photo;
    }

    public void setGoods_photo(String goods_photo) {
        this.goods_photo = goods_photo;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
    }

    public String getGoods_reserve() {
        return goods_reserve;
    }

    public void setGoods_reserve(String goods_reserve) {
        this.goods_reserve = goods_reserve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartInfo cartInfo = (CartInfo) o;
        return Objects.equals(user_id, cartInfo.user_id) && Objects.equals(goods_id, cartInfo.goods_id) && Objects.equals(goods_name, cartInfo.goods_name) && Objects.equals(goods_desc, cartInfo.goods_desc) && Objects.equals(goods_photo, cartInfo.goods_photo) && Objects.equals(goods_price, cartInfo.goods_price) && Objects.equals(goods_amount, cartInfo.goods_amount) && Objects.equals(goods_reserve, cartInfo.goods_reserve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, goods_id, goods_name, goods_desc, goods_photo, goods_price, goods_amount, goods_reserve);
    }

    @Override
    public String toString() {
        return "CartInfo{" +
                "user_id=" + user_id +
                ", goods_id=" + goods_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_desc='" + goods_desc + '\'' +
                ", goods_photo='" + goods_photo + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", goods_amount='" + goods_amount + '\'' +
                ", goods_reserve='" + goods_reserve + '\'' +
                '}';
    }
}
