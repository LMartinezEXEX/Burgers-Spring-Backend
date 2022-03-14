package com.burgers.app.Domain;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
public class Coupon {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String code;

    @NotNull
    @Range(min = 0)
    private Long discount = 0L;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime availableUntil;

    public Coupon(){
    }

    public Coupon(Long discount, LocalDateTime availableUntil){
        this.code = generateCode(6);
        this.discount = discount;
        this.createdAt = LocalDateTime.now();
        this.availableUntil = availableUntil;
    }

    private String generateCode(int codeLenght){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for(int i = 0; i < codeLenght; i++){
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        this.code = sb.toString();

        return this.code;
    }
}
