package org.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class ProductDTO {
        private int id;
        private String goodnames;
        private double price;
        private int typeid;
}
