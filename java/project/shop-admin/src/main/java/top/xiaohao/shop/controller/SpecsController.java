package top.xiaohao.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.domain.entitiy.ProductSpecs;
import top.xiaohao.shop.domain.entitiy.Specs;
import top.xiaohao.shop.service.ProductSpecsService;
import top.xiaohao.shop.service.SpecsService;
import top.xiaohao.shop.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/specs")
@Slf4j
public class SpecsController {

    @Autowired
    private SpecsService specsService;

    @Autowired
    private ProductSpecsService productSpecsService;

    @GetMapping("/findAll")
    public Result<?> findAll() {
        return new Result<>(200, "查询所有规格", specsService.list());
    }

    @RequestMapping("/addBatch")
    public Result<?> addBatch(Integer productId, Integer[] specsIds) {
        if (!Objects.isNull(specsIds)) {
            ProductSpecs productSpecs;
            log.info("商品id -> {}", productId);
            List<ProductSpecs> productSpecsList = new ArrayList<>();
            for (Integer specsId : specsIds) {
                productSpecs = new ProductSpecs();
                productSpecs.setProductId(productId);
                productSpecs.setSpecsId(specsId);
                productSpecsList.add(productSpecs);
            }
            if (productSpecsService.saveBatch(productSpecsList)) {
                return new Result<>(200, "添加成功", productSpecsList);
            }
            return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
        }
        return new Result<>(500, "数据不存在");
    }

    @GetMapping("/existSpecsName/{productType}/{specsName}")
    public Result<?> existsSpecsName(@PathVariable("productType") String productType, @PathVariable("specsName") String specsName) {
        Specs one = specsService.getOne(new QueryWrapper<Specs>().eq("product_type", productType).and(i -> {
            i.eq("specs_name", specsName);
        }));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到规格", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @PostMapping("/add")
    public Result<?> addSpecs(@RequestBody Specs specs) {
        boolean status = specsService.save(specs);
        if (status) {
            return new Result<>(200, "添加成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @GetMapping("/existSpecsName/{specsId}/{productType}/{specsName}")
    public Result<?> existsSpecsName(@PathVariable("specsId") Integer specsId, @PathVariable("productType") String productType, @PathVariable("specsName") String specsName) {
        Specs one = specsService.getOne(new QueryWrapper<Specs>().eq("specs_id", specsId).and(i -> {
            i.eq("product_type", productType).and(k -> {
                k.eq("specs_name", specsName);
            });
        }));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到规格", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @PutMapping("/update")
    public Result<?> updateSpecs(@RequestBody Specs specs) {
        UpdateWrapper<Specs> wrapper = new UpdateWrapper<>();
        wrapper.set("specs_name", specs.getSpecsName())
                .set("product_type", specs.getProductType());
        wrapper.eq("specs_id", specs.getSpecsId());
        boolean status = specsService.update(wrapper);
        if (status) {
            return new Result<>(200, "更新成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @DeleteMapping("/delete/{specsId}")
    public Result<?> deleteSpecs(@PathVariable("specsId") Integer specsId) {
        boolean status = specsService.removeById(specsId);
        if (status) {
            return new Result<>(200, "删除成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }
}
