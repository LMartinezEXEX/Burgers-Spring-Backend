package com.burgers.app.Service.Impl;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import com.burgers.app.Data.CouponRepository;
import com.burgers.app.Domain.Coupon;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Service.CouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService{

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon apply(String code, HttpSession session) throws BurgersException{
        Coupon coupon = getCoupon(code);
        if(Objects.isNull(coupon)){
            throw new BurgersException("Error: invalid coupon");
        }

        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(coupon.getCreatedAt()) && now.isBefore(coupon.getAvailableUntil())){
            session.setAttribute("userCoupon", coupon);
            return coupon;
        }
        
        throw new BurgersException("Error: Out of date coupon");
    }

    @Override
    public Iterable<Coupon> getAllAvailable() {
        Iterable<Coupon> coupons = couponRepository.findAll();
        return coupons;
    }

    @Override
    public Coupon generate(Long discount, LocalDateTime avalableUntil) {
        Coupon coupon = new Coupon(discount, avalableUntil);

        return couponRepository.save(coupon);
    }

    @Override
    public Coupon getCoupon(String code){
        return couponRepository.findByCode(code);
    }

    @Override
    public void remove(HttpSession session) {
        session.removeAttribute("userCoupon");
    }
    
}
