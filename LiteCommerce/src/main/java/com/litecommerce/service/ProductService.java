package com.litecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.litecommerce.model.ProductModel;
import com.litecommerce.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository prRepository;
	
	public List<ProductModel> findAll(){
		return prRepository.findAll();
	}
	
	public ProductModel findById(int id){
		return prRepository.findById(id);
	}
	
	public List<ProductModel> getNewArrivalsProduct(){
		return prRepository.getNewArrivalsProduct();
	}
	
	public List<ProductModel> getNewArrivalsProductByGroupId(Integer id){
		return prRepository.getNewArrivalsProductByGroupId(id);
	}
	
	public List<ProductModel> getAllProductByGroupId(Integer id){
		return prRepository.getAllProductByGroupId(id);
	}
	
	public List<ProductModel> getAllProduct(){
		return prRepository.getAllProduct();
	}
	
	public void deleteById(Integer id) {
		prRepository.deleteById(id);
	}
	
	public void updateQuantity(int quantity, int productid) {
		prRepository.updateQuantity(quantity, productid);
	}
	
	public ProductModel insert(ProductModel product) {
		return prRepository.save(product);
	}
	
	public List<ProductModel> getProductsByName(String name){
		return prRepository.getProductsByName(name);
	}
	
	public Page<ProductModel> findPaginated(org.springframework.data.domain.Pageable pageable){
		return prRepository.findAllPage(pageable);
	}
	
	public Page<ProductModel> findByProductNameContaining(String productName, Pageable pageable){
		return prRepository.findByProductNameContaining(productName, pageable);
	}
}
