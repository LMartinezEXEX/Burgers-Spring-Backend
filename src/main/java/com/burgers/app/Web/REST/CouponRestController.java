package com.burgers.app.Web.REST;

import javax.servlet.http.HttpServletRequest;

import com.burgers.app.Domain.Coupon;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Security.MessageResponse;
import com.burgers.app.Service.CouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupon")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class CouponRestController {
    
    @Autowired
    private CouponService couponService;

    @PostMapping("/apply")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> applyCoupon(@RequestBody Coupon coupon, HttpServletRequest request) {

        try {
            return ResponseEntity.ok(couponService.apply(coupon.getCode(), request.getSession()));
        } catch(BurgersException be){
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> removeCoupon(HttpServletRequest request) {

        couponService.remove(request.getSession());

        return ResponseEntity.ok(new MessageResponse("Coupon removed from order"));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){

        return ResponseEntity.ok(couponService.getAllAvailable());
    }

    @PostMapping("/generate")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> generate(@RequestBody Coupon coupon){
        
        return ResponseEntity.ok(couponService.generate(coupon.getDiscount(), coupon.getAvailableUntil()));
    }
}
