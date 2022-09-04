package top.xiaohao.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xiaohao.shop.domain.entitiy.Product;
import top.xiaohao.shop.service.ProductService;
import top.xiaohao.shop.service.ProductTypeService;

import java.util.Collection;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Test
    public void test1() {
        Collection<Product> products = productService.list();
        products.forEach(System.out::println);
    }
}
