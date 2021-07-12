package com.sha.springbootbookseller.repository.projection;

import java.time.LocalDateTime;

/**
 * @author sa
 * @date 3.07.2021
 * @time 17:58
 */
public interface IPurchaseItem
{
    String getTitle();
    Double getPrice();
    LocalDateTime getPurchaseTime();
}
