package com.kb.service;

import com.kb.Entity.Order;
import com.kb.Entity.User;
import java.util.List;
import java.util.Optional;

public interface OrderService {
	Order placeOrder(Order order);

	Order updateOrder(Long orderId, Order order);

	void cancelOrder(Long orderId);

	Optional<Order> getOrderById(Long orderId);

	List<Order> getOrdersByUser(User user);

	List<Order> getOrdersByCake(Long cakeId);

	List<Order> getOrdersByTime(String time);

	Order confirmOrder(Order order);

	List<Order> getOrdersByStatus(Boolean isConfirm);
	
	void deleteOrderById(Long orderId);
}
