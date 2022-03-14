package com.burgers.app.Service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import com.burgers.app.Domain.Coupon;
import com.burgers.app.Exception.BurgersException;

public interface CouponService {
    
    public Coupon apply(String code, HttpSession session) throws BurgersException;

    public void remove(HttpSession session);
    
    public Coupon getCoupon(String code);

    public Iterable<Coupon> getAllAvailable();

    public Coupon generate(Long discount, LocalDateTime availableUntil);
}
