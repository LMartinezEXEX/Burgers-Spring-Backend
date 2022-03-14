package com.burgers.app.Data;

import com.burgers.app.Domain.Coupon;

import org.springframework.data.repository.CrudRepository;

public interface CouponRepository extends CrudRepository<Coupon, Long>{
    Coupon findByCode(String code);
}
