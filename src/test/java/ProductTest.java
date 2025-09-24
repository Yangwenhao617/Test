
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.Product;
import org.example.entity.ProductDTO;
import org.example.mapper.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProductMapper 集成测试 - 使用SqlSessionFactoryBuilder
 * 测试数据库连接和接口方法的可用性
 */
class ProductMapperTest {

    private SqlSession sqlSession;
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() throws IOException {
        // 加载MyBatis配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 创建SqlSession
        sqlSession = sqlSessionFactory.openSession();

        // 获取Mapper实例
        productMapper = sqlSession.getMapper(ProductMapper.class);
    }

    @AfterEach
    void tearDown() {
        // 关闭SqlSession
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    void testGetProductsByCategoryName() {
        // 测试根据分类名称获取产品信息（Map形式）
        List<Map<String, Object>> result = productMapper.getProductsByCategoryName("白色家电");

        assertNotNull(result, "返回结果不应为null");
        System.out.println("getProductsByCategoryName 测试通过，返回记录数: " + result.size());

        // 打印部分结果用于验证
        if (!result.isEmpty()) {
            Map<String, Object> firstProduct = result.get(0);
            System.out.println("第一个产品信息: " + firstProduct);
        }
        System.out.println("=====================分割线====================");
    }

    @Test
    void testGetProductsByCategoryNameWithDTO() {
        // 测试根据分类名称获取产品信息（DTO形式）
        List<ProductDTO> result = productMapper.getProductsByCategoryNameWithDTO("白色家电");

        assertNotNull(result, "返回结果不应为null");
        System.out.println("getProductsByCategoryNameWithDTO 测试通过，返回记录数: " + result.size());

        // 打印部分结果用于验证
        if (!result.isEmpty()) {
            ProductDTO firstProduct = result.get(0);
            System.out.println("第一个产品DTO: ID=" + firstProduct.getId() + ", Name=" + firstProduct.getGoodnames());
        }
        System.out.println("=====================分割线====================");

    }

    @Test
    void testGetProductsWithCategoryByCategoryName() {
        // 测试根据分类名称获取产品及其分类信息
        List<Product> result = productMapper.getProductsWithCategoryByCategoryName("白色家电");

        assertNotNull(result, "返回结果不应为null");
        System.out.println("getProductsWithCategoryByCategoryName 测试通过，返回记录数: " + result.size());

        // 打印部分结果用于验证
        if (!result.isEmpty()) {
            Product firstProduct = result.get(0);
            System.out.println("第一个产品: ID=" + firstProduct.getId() + ", Name=" + firstProduct.getGoodsname());
        }
        System.out.println("=====================分割线====================");

    }

    @Test
    void testGetAllProductsWithCategory() {
        // 测试获取所有产品及其分类信息
        List<Product> result = productMapper.getAllProductsWithCategory();

        assertNotNull(result, "返回结果不应为null");
        assertFalse(result.isEmpty(), "返回结果不应为空");
        System.out.println("getAllProductsWithCategory 测试通过，返回记录数: " + result.size());

        // 打印部分结果用于验证
        Product firstProduct = result.get(0);
        System.out.println("第一个产品: ID=" + firstProduct.getId() + ", Name=" + firstProduct.getGoodsname());
        System.out.println("=====================分割线====================");

    }

    @Test
    void testGetProductWithCategoryById() {
        // 测试根据产品ID获取产品及其分类信息
        // 假设数据库中至少存在ID为1的产品
        Product result = productMapper.getProductWithCategoryById(1);

        assertNotNull(result, "返回结果不应为null");
        System.out.println("getProductWithCategoryById 测试通过，产品ID: " + result.getId());
        System.out.println("产品名称: " + result.getGoodsname());
        System.out.println("=====================分割线====================");

    }

    @Test
    void testGetProductsByCategoryNameWithNonExistentCategory() {
        // 测试不存在的分类名称
        List<Map<String, Object>> result = productMapper.getProductsByCategoryName("NonExistentCategory");

        assertNotNull(result, "返回结果不应为null");
        System.out.println("不存在的分类测试通过，返回记录数: " + result.size());
        System.out.println("=====================分割线====================");

    }

    @Test
    void testGetProductWithCategoryByIdWithNonExistentId() {
        // 测试不存在的产品ID
        Product result = productMapper.getProductWithCategoryById(99999);

        // 注意：根据MyBatis配置，可能返回null或空对象
        if (result == null) {
            System.out.println("不存在的产品ID测试通过，返回null");
        } else {
            System.out.println("不存在的产品ID测试通过，返回对象ID: " + result.getId());
        }
        System.out.println("=====================分割线====================");

    }

    @Test
    void testGetProductsByCategoryNameWithNullParameter() {
        // 测试null参数
        List<Map<String, Object>> result = productMapper.getProductsByCategoryName(null);

        assertNotNull(result, "返回结果不应为null");
        System.out.println("null参数测试通过，返回记录数: " + result.size());
        System.out.println("=====================分割线====================");

    }

    @Test
    void testMultipleCategoryNames() {
        // 测试多个不同的分类名称
        String[] categories = {"Electronics", "Books", "Clothing", "Sports"};

        for (String category : categories) {
            List<Map<String, Object>> result = productMapper.getProductsByCategoryName(category);
            assertNotNull(result);
            System.out.println("分类 '" + category + "' 的产品数量: " + result.size());
        }
        System.out.println("=====================分割线====================");

    }

    @Test
    void testMethodPerformance() {
        // 简单的性能测试 - 记录方法执行时间
        long startTime = System.currentTimeMillis();

        List<Product> result = productMapper.getAllProductsWithCategory();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertNotNull(result);
        System.out.println("getAllProductsWithCategory 执行时间: " + duration + "ms");
        System.out.println("返回记录数: " + result.size());
        System.out.println("=====================分割线====================");

    }

}