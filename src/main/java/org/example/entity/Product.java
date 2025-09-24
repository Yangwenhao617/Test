    package org.example.entity;

    import lombok.Data;
    import lombok.experimental.Accessors;

    @Data
    @Accessors(chain = true)
    public class Product {
        private int id;
        private String goodsname;
        private double price;
        private int typeid;
        private Category category;
    }
