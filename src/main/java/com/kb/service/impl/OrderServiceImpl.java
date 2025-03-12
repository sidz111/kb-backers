package com.kb.service.impl;

import com.kb.Entity.Order;
import com.kb.Entity.User;
import com.kb.repository.OrderRepository;
import com.kb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long orderId, Order order) {
        return orderRepository.findById(orderId).map(existingOrder -> {
            existingOrder.setTime(order.getTime());
            existingOrder.setUser(order.getUser());
            existingOrder.setCake(order.getCake());
            return orderRepository.save(existingOrder);
        }).orElse(null);
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getOrdersByCake(Long cakeId) {
        return orderRepository.findByCakeId(cakeId);
    }

    @Override
    public List<Order> getOrdersByTime(String time) {
        return orderRepository.findByTimeContaining(time);
    }

	@Override
	public Order confirmOrder(Order order) {
		Optional<Order> o = orderRepository.findById(order.getId());
		if(o.isEmpty()) {
			return null;
		}
		else {
			return orderRepository.save(order);
		}
	}

	@Override
	public List<Order> getOrdersByStatus(Boolean isConfirm) {
		return this.orderRepository.findByIsConfirm(isConfirm);
	}

	@Override
	public void deleteOrderById(Long orderId) {
		orderRepository.deleteById(orderId);
	}
}
