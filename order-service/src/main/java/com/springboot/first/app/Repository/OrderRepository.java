package com.springboot.first.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
