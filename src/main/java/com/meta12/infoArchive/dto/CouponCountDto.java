package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponCountDto {
    private String couponName;
    private Long unused;
    private Long used;
    private Long expired;

    public CouponCountDto(Long unused, Long used, Long expired)
    {
        this.used = used;
        this.unused = unused;
        this.expired = expired;
    }
}
