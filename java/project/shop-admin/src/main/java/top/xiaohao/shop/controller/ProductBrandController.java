package top.xiaohao.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.domain.entitiy.Product;
import top.xiaohao.shop.domain.entitiy.ProductBrand;
import top.xiaohao.shop.service.ProductBrandService;
import top.xiaohao.shop.service.ProductService;
import top.xiaohao.shop.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/productBrand")
public class ProductBrandController {

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private ProductService productService;

    @GetMapping("/findAllName")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> findAllName() {
        List<ProductBrand> brandList = productBrandService.list(new QueryWrapper<ProductBrand>().select("brand_name"));
        List<String> names = new ArrayList<>();
        brandList.forEach(item -> {
            names.add(item.getBrandName());
        });
        return new Result<>(200, "查询成功", names);
    }

    @GetMapping("/findAll")
    public Result<?> findAll() {
        return new Result<>(200, "查询所有品牌", productBrandService.list());
    }

    @GetMapping("/existBrandName/{brandName}")
    public Result<?> existBrandName(@PathVariable("brandName") String brandName) {
        ProductBrand one = productBrandService.getOne(new QueryWrapper<ProductBrand>().eq("brand_name", brandName));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到一个品牌", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @GetMapping("/existBrandName/{brandId}/{brandName}")
    public Result<?> existBrand(@PathVariable("brandId") Integer brandId, @PathVariable("brandName") String brandName) {
        ProductBrand one = productBrandService.getOne(new QueryWrapper<ProductBrand>().eq("brand_id", brandId).and(i -> {
            i.eq("brand_name", brandName);
        }));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到一个品牌", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @PostMapping("/add")
    public Result<?> addBrand(@RequestBody ProductBrand productBrand) {
        boolean status = productBrandService.save(productBrand);
        if (status) {
            return new Result<>(200, "添加品牌成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @PutMapping("/update")
    public Result<?> updateBrand(@RequestBody ProductBrand productBrand) {
        UpdateWrapper<ProductBrand> wrapper = new UpdateWrapper<>();
        wrapper.set("brand_name", productBrand.getBrandName())
                .set("brand_describe", productBrand.getBrandDescribe());
        wrapper.eq("brand_id", productBrand.getBrandId());
        boolean status = productBrandService.update(wrapper);
        if (status) {
            return new Result<>(200, "更新成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @GetMapping("/existProduct/{name}")
    public Result<?> existProduct(@PathVariable("name") String brandName) {
        ProductBrand one = productBrandService.getOne(new QueryWrapper<ProductBrand>().eq("brand_name", brandName));
        if (!Objects.isNull(one)) {
            Product product = productService.getOne(new QueryWrapper<Product>().eq("product_brand", one.getBrandName()));
            if (!Objects.isNull(product)) {
                return new Result<>(200, "查询到一个商品", product);
            } else {
                return new Result<>(200, "暂无记录");
            }
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @DeleteMapping("delete/{brandId}")
    public Result<?> deleteBrand(@PathVariable("brandId") Integer brandId) {
        boolean status = productBrandService.removeById(brandId);
        if (status) {
            return new Result<>(200, "删除成功");
        }
        return new Result<>(500,AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }
}
