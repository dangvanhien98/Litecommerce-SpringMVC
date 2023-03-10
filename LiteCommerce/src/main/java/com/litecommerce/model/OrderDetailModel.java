package com.litecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Table(name = "OrderDetail")
@Entity(name = "OrderDetailModel")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OrderDetailID", columnDefinition = "int")
	private int orderDetailID;
	
	@ManyToOne
	@JoinColumn(name = "ProductID")
	private ProductModel product;
    
	@ManyToOne
	@JoinColumn(name = "OrderID")
	private OrderModel order;

	@Column(name = "Quantity", columnDefinition = "int")
	private int quantity;
	
	@Transient
	private int productID;
	
	@Transient
	private int orderID;
	
}
