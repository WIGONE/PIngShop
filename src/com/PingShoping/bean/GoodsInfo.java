package com.PingShoping.bean;

import java.io.Serializable;

public class GoodsInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 属性私有化
	private Integer goods_id ;
	private String goods_name ; //商品名
	private String goods_photo ; //商品图像	
	private String goods_price;  // 商品价格
	private String goods_type ;//商品类型
	private String goods_desc ; //商品描述
	private String goods_reserve;   //商品库存   
	private Integer goods_status; // 状态   0 售空  1 可售     2  禁售 3库存警告
	
	public GoodsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoodsInfo(Integer goods_id, String goods_name, String goods_photo, String goods_price, String goods_type,
			String goods_desc, String goods_reserve, Integer goods_status) {
		super();
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.goods_photo = goods_photo;
		this.goods_price = goods_price;
		this.goods_type = goods_type;
		this.goods_desc = goods_desc;
		this.goods_reserve = goods_reserve;
		this.goods_status = goods_status;
	}
	@Override
	public String toString() {
		return "GoodsInfo [goods_id=" + goods_id + ", goods_name=" + goods_name + ", goods_photo=" + goods_photo
				+ ", goods_price=" + goods_price + ", goods_type=" + goods_type + ", goods_desc=" + goods_desc
				+ ", goods_reserve=" + goods_reserve + ", goods_status=" + goods_status + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goods_desc == null) ? 0 : goods_desc.hashCode());
		result = prime * result + ((goods_id == null) ? 0 : goods_id.hashCode());
		result = prime * result + ((goods_name == null) ? 0 : goods_name.hashCode());
		result = prime * result + ((goods_photo == null) ? 0 : goods_photo.hashCode());
		result = prime * result + ((goods_price == null) ? 0 : goods_price.hashCode());
		result = prime * result + ((goods_reserve == null) ? 0 : goods_reserve.hashCode());
		result = prime * result + ((goods_status == null) ? 0 : goods_status.hashCode());
		result = prime * result + ((goods_type == null) ? 0 : goods_type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoodsInfo other = (GoodsInfo) obj;
		if (goods_desc == null) {
			if (other.goods_desc != null)
				return false;
		} else if (!goods_desc.equals(other.goods_desc))
			return false;
		if (goods_id == null) {
			if (other.goods_id != null)
				return false;
		} else if (!goods_id.equals(other.goods_id))
			return false;
		if (goods_name == null) {
			if (other.goods_name != null)
				return false;
		} else if (!goods_name.equals(other.goods_name))
			return false;
		if (goods_photo == null) {
			if (other.goods_photo != null)
				return false;
		} else if (!goods_photo.equals(other.goods_photo))
			return false;
		if (goods_price == null) {
			if (other.goods_price != null)
				return false;
		} else if (!goods_price.equals(other.goods_price))
			return false;
		if (goods_reserve == null) {
			if (other.goods_reserve != null)
				return false;
		} else if (!goods_reserve.equals(other.goods_reserve))
			return false;
		if (goods_status == null) {
			if (other.goods_status != null)
				return false;
		} else if (!goods_status.equals(other.goods_status))
			return false;
		if (goods_type == null) {
			if (other.goods_type != null)
				return false;
		} else if (!goods_type.equals(other.goods_type))
			return false;
		return true;
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
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getGoods_desc() {
		return goods_desc;
	}
	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}
	public String getGoods_reserve() {
		return goods_reserve;
	}
	public void setGoods_reserve(String goods_reserve) {
		this.goods_reserve = goods_reserve;
	}
	public Integer getGoods_status() {
		return goods_status;
	}
	public void setGoods_status(Integer goods_status) {
		this.goods_status = goods_status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
