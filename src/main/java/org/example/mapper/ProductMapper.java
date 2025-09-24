package org.example.mapper;
import org.example.entity.Product;
import org.example.entity.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductMapper {

    /**
     * 根据分类名称获取产品信息
     * @param categoryName 分类名称
     * @return 产品信息列表，每个产品以Map形式表示
     */
    List<Map<String, Object>> getProductsByCategoryName(String categoryName);

    /**
     * 根据分类名称获取产品信息
     * @param categoryName 分类名称
     * @return 产品信息列表，每个产品以ProductDTO对象表示
     */
    List<ProductDTO> getProductsByCategoryNameWithDTO(String categoryName);

    /**
     * 根据分类名称获取产品及其分类信息
     * @param categoryName 分类名称
     * @return 产品列表，包含产品及其分类信息
     */
    List<Product> getProductsWithCategoryByCategoryName(String categoryName);

    /**
     * 获取所有产品及其分类信息
     * @return 所有产品列表，包含产品及其分类信息
     */
    List<Product> getAllProductsWithCategory();

    /**
     * 根据产品ID获取产品及其分类信息
     * @param id 产品ID
     * @return 产品对象，包含产品及其分类信息
     */
    Product getProductWithCategoryById(Integer id);
}

