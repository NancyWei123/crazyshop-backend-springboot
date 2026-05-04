package org.target.shop.dto;

import lombok.Data;

@Data
public class CreateShopRequest {

    private String name;

    private String description;

    private String logoUrl;
}