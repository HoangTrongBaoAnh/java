package com.springboot.first.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.first.app.Model.OrderLineItems;

public interface OrderDetailRepository extends JpaRepository<OrderLineItems, Long>{

}
