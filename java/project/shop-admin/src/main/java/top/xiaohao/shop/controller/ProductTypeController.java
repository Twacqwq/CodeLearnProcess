package top.xiaohao.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.domain.entitiy.Product;
import top.xiaohao.shop.domain.entitiy.ProductType;
import top.xiaohao.shop.service.ProductService;
import top.xiaohao.shop.service.ProductTypeService;
import top.xiaohao.shop.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @GetMapping("/findAllName")
    public Result<?> findAllName() {
        List<ProductType> typeList = productTypeService.list(new QueryWrapper<ProductType>().select("type_name"));
        List<String> names = new ArrayList<>();
        typeList.forEach(item -> {
            names.add(item.getTypeName());
        });
        return new Result<>(200, "查询成功", names);
    }

    @GetMapping("/findAll")
    public Result<?> findAll() {
        List<ProductType> productTypeList = productTypeService.list();
        return new Result<>(200, "查询所有分类", productTypeList);
    }

    @GetMapping("/existType/{typeName}")
    public Result<?> existType(@PathVariable("typeName") String typeName) {
        ProductType productType = productTypeService.getOne(new QueryWrapper<ProductType>().eq("type_name", typeName));
        if (!Objects.isNull(productType)) {
            return new Result<>(200, "查询一条分类", productType);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @GetMapping("/existProduct/{typeName}")
    public Result<?> existType2(@PathVariable("typeName") String typeName) {
        ProductType productType = productTypeService.getOne(new QueryWrapper<ProductType>().eq("type_name", typeName));
        if (!Objects.isNull(productType)) {
            Product one = productService.getOne(new QueryWrapper<Product>().eq("product_type", productType.getTypeName()));
            if (!Objects.isNull(one)) {
                return new Result<>(200, "分类含有商品", one);
            } else {
                return new Result<>(200, "分类无商品");
            }
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @GetMapping("/existType/{typeId}/{typeName}")
    public Result<?> existType(@PathVariable("typeId") Integer typeId, @PathVariable("typeName") String typeName) {
        ProductType one = productTypeService.getOne(new QueryWrapper<ProductType>().eq("type_id", typeId).and(i -> {
            i.eq("type_name", typeName);
        }));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到一条分类", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @DeleteMapping("/delete/{typeId}")
    public Result<?> deleteType(@PathVariable("typeId") Integer typeId) {
        boolean status = productTypeService.removeById(typeId);
        if (status) {
            return new Result<>(200, "删除一条分类", typeId);
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @PostMapping("/add")
    public Result<?> addProductType(@RequestBody ProductType productType) {
        boolean status = productTypeService.save(productType);
        if (status) {
            return new Result<>(200, "插入一条分类", productType);
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @PutMapping("/update")
    public Result<?> updateType(@RequestBody ProductType productType) {
        UpdateWrapper<ProductType> wrapper = new UpdateWrapper<>();
        wrapper.set("type_name", productType.getTypeName())
                .set("type_describe", productType.getTypeDescribe())
                .set("type_url_left", productType.getTypeUrlLeft())
                .set("type_url_top", productType.getTypeUrlTop());
        wrapper.eq("type_id", productType.getTypeId());
        boolean status = productTypeService.update(wrapper);
        if (status) {
            return new Result<>(200, "更新成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }
}
