package top.xiaohao.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.domain.vo.ProductListVO;
import top.xiaohao.shop.domain.entitiy.Product;
import top.xiaohao.shop.service.ProductService;
import top.xiaohao.shop.utils.Result;
import top.xiaohao.shop.utils.WebUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查询商品总数量
     * @return result
     */
    @GetMapping("/count")
    public Result<?> findCount() {
        long count = productService.count();
        return new Result<>(200, "查询成功", count);
    }

    /**
     * 查询所有商品
     * @return result
     */
    @GetMapping("/findAll")
    public Result<?> findAll() {
        List<Product> products = productService.list();
        List<ProductListVO> productList = new ArrayList<>();
        products.forEach(product -> {
            ProductListVO productListVO = new ProductListVO();
            if (product.getIsNew() == 1) { //是新品
                productListVO.setIsNew(true);
            } else if (product.getIsNew() == 0) {
                productListVO.setIsNew(false);
            }
            if (product.getIsSale() == 1) { //已上架
                productListVO.setIsSale(true);
            } else if (product.getIsSale() == 0) {
                productListVO.setIsSale(false);
            }
            productListVO.setInPrice(product.getInPrice());
            productListVO.setIsStockout(product.getIsStockout());
            productListVO.setProductBrand(product.getProductBrand());
            productListVO.setProductDescribe(product.getProductDescribe());
            productListVO.setLowestStock(product.getLowestStock());
            productListVO.setProductNo(product.getProductNo());
            productListVO.setProductName(product.getProductName());
            productListVO.setProductId(product.getProductId());
            productListVO.setProductType(product.getProductType());
            productListVO.setProductUrl(product.getProductUrl());
            productListVO.setSaleTime(product.getSaleTime());
            productListVO.setOutPrice(product.getOutPrice());
            productListVO.setProductStock(product.getProductStock());
            productList.add(productListVO);
        });
        return new Result<>(200, "查询所有商品成功", productList);
    }

    /**
     * 更新商品的上架状态
     * @param productId 商品ID
     * @param isSale 上架状态
     * @return result
     */
    @PutMapping("/updateSale/{productId}/{isSale}")
    public Result<?> updateSaleState(@PathVariable("productId") Integer productId, @PathVariable("isSale") Boolean isSale) {
        Product product = productService.getById(productId);
        if (isSale) {
            product.setIsSale(1);
        } else {
            product.setIsSale(0);
        }
        productService.update(product, new UpdateWrapper<Product>().eq("product_id", productId));
        return new Result<>(200, "更新成功", null);
    }

    /**
     * 更新商品的新品状态
     * @param productId 商品ID
     * @param isNew 是否为新品
     * @return result
     */
    @PutMapping("/updateNew/{productId}/{isNew}")
    public Result<?> updateNewState(@PathVariable("productId") Integer productId, @PathVariable("isNew") Boolean isNew) {
        Product product = productService.getById(productId);
        if (isNew) {
            product.setIsNew(1);
        } else {
            product.setIsNew(0);
        }
        productService.update(product, new UpdateWrapper<Product>().eq("product_id", productId));
        return new Result<>(200, "更新成功", null);
    }

    /**
     * 更新商品信息
     * @param product 更新后的商品信息
     * @return result
     */
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> updateProduct(@RequestBody Product product) {
        UpdateWrapper<Product> wrapper = new UpdateWrapper<>();
        wrapper.set("product_name", product.getProductName())
                .set("in_price", product.getInPrice())
                .set("out_price", product.getOutPrice())
                .set("product_type", product.getProductType())
                .set("product_brand", product.getProductBrand())
                .set("lowest_stock", product.getLowestStock())
                .set("product_describe", product.getProductDescribe())
                .set("product_url", product.getProductUrl());
        wrapper.eq("product_id", product.getProductId());
        boolean status = productService.update(wrapper);
        if (status) {
            return new Result<>(200, "更新成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    /**
     * 根据商品ID删除商品
     * @param productId 商品ID
     * @return result
     */
    @DeleteMapping("/delete/{productId}")
    public Result<?> deleteProduct(@PathVariable("productId") Integer productId) {
        boolean status = productService.removeById(productId);
        if (status) {
            return new Result<>(200, "删除成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    /**
     * 添加商品
     * @param productListVO 添加商品的信息
     * @return reuslt
     */
    @PostMapping("/add")
    public Result<?> addProduct(@RequestBody ProductListVO productListVO) {
        productListVO.setSaleTime(new Date());
        Product product = new Product();
        if (productListVO.getIsNew()) {
            product.setIsNew(1);
        } else {
            product.setIsNew(0);
        }
        if (productListVO.getIsSale()) {
            product.setIsSale(1);
        } else {
            product.setIsSale(0);
        }
        product.setSaleTime(productListVO.getSaleTime());
        product.setInPrice(productListVO.getInPrice());
        product.setIsStockout(productListVO.getIsStockout());
        product.setProductBrand(productListVO.getProductBrand());
        product.setProductDescribe(productListVO.getProductDescribe());
        product.setLowestStock(productListVO.getLowestStock());
        product.setProductName(productListVO.getProductName());
        product.setProductType(productListVO.getProductType());
        product.setProductUrl(productListVO.getProductUrl());
        product.setOutPrice(productListVO.getOutPrice());
        product.setProductStock(productListVO.getProductStock());
        product.setProductNo(WebUtil.getRandomString(12));
        product.setProductId(Math.toIntExact(productService.count()) + 10);
        boolean status = productService.save(product);
        if (status) {
            return new Result<>(200, "添加商品成功", product);
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @GetMapping("/findIdByKey/{productName}")
    public Result<?> findIdByKey(@PathVariable("productName") String productName) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("product_name", productName);
        Product product = productService.getOne(wrapper);
        return new Result<>(200, "获取商品ID成功", product.getProductId());
    }
}
