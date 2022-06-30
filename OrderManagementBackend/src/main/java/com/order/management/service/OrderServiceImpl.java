package com.order.management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.management.model.Order;
import com.order.management.model.Product;
import com.order.management.repository.OrderRepo;
import com.order.management.repository.ProductRepo;

@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderRepo orderRepo;
	private ProductRepo productRepo;
	
	@Autowired
	public OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo) {
		this.orderRepo = orderRepo;
		this.productRepo = productRepo;
	}
	
//	ORDER------------------------------------------

	@Override
	public void newOrder(Order order) {
		order.setCreated(LocalDateTime.now());
		orderRepo.save(order);
		
	}

	@Override
	public List<Order> getOrders() {
		return orderRepo.findAll();
	}

	@Override
	public void updateOrder(Order order) {
		orderRepo.save(order); //this might need to be changed, documentation says save also acts as a merge
	}

	@Override
	public void deleteOrder(Order order) { //contingent upon the custom findallbyorder method within the productrepo
//	get products, iterate and delete then remove order
		List<Product> products = productRepo.findAllByOrder(order);
		
		for (Product x : products) {
			deleteProduct(x);
		}
		
		orderRepo.delete(order);
	}
	
//	PRODUCT---------------------------------------

	@Override
	public List<Product> getProductsByOrder(Order order) {
		return productRepo.findAllByOrder(order);
	}

	@Override
	public void newProduct(Product product) {
		productRepo.save(product);
		
	}

	@Override
	public void updateProduct(Product product) {
		productRepo.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		productRepo.delete(product);
	}
	
	
}
