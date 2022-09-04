package top.xiaohao.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.domain.vo.SupplierVO;
import top.xiaohao.shop.domain.entitiy.Product;
import top.xiaohao.shop.domain.entitiy.Purchase;
import top.xiaohao.shop.domain.entitiy.Supplier;
import top.xiaohao.shop.service.ProductService;
import top.xiaohao.shop.service.PurchaseService;
import top.xiaohao.shop.service.SupplierService;
import top.xiaohao.shop.utils.Result;
import top.xiaohao.shop.utils.WebUtil;

import java.util.*;

/**
 * 运营商Controller
 * @author Twac
 */
@RestController
@RequestMapping("/supplier")
public class PurchaseController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductService productService;

    @GetMapping("/findAllUsable")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> findUsable() {
        List<Supplier> supplierList = supplierService.list(new QueryWrapper<Supplier>().eq("status", 1));
        if (supplierList != null) {
            return new Result<>(200, "运营商查询成功", supplierList);
        }
        return new Result<>(500, "查询失败");
    }

    @GetMapping("/findAll")
    public Result<?> findAll() {
        return new Result<>(200, "查询成功", purchaseService.list());
    }

    @GetMapping("/all")
    public Result<?> findAllSupplier() {
        List<Supplier> supplierList = supplierService.list();
        List<SupplierVO> supplierVOList = new ArrayList<>();
        supplierList.forEach(supplier -> {
            SupplierVO vo = new SupplierVO();
            vo.setContactWay(supplier.getContactWay());
            vo.setPrincipal(supplier.getPrincipal());
            vo.setProductType(supplier.getProductType());
            vo.setSupplierId(supplier.getSupplierId());
            vo.setSupplierNo(supplier.getSupplierNo());
            vo.setSupplierName(supplier.getSupplierName());
            vo.setStatus(supplier.getStatus() == 1);
            supplierVOList.add(vo);
        });
        return new Result<>(200, "查询成功", supplierVOList);
    }

    /**
     * 采购商品确认收货
     * @param productNo 商品编号
     * @param purchaseId 采购ID
     * @param purchaseNumber 采购数量
     * @return result
     */
    @RequestMapping("/receipts")
    public Result<?> receipts(String productNo, Integer purchaseId, Integer purchaseNumber) {
        Integer productId = productService.getOne(new QueryWrapper<Product>().eq("product_no", productNo)).getProductId();
        Product product = productService.getById(productId);
        Integer lowestStock = product.getLowestStock();
        Integer productStock = product.getProductStock();
        product.setProductStock(productStock + purchaseNumber);
        if (product.getProductStock() < lowestStock) { //缺货
            product.setIsStockout(1);
        } else {
            product.setIsStockout(0);
        }
        if (productService.updateById(product)) {
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(purchaseId);
            purchase.setReceiptStatus(1);
            purchase.setReceiptTime(new Date());
            if (purchaseService.updateById(purchase)) {
                return new Result<>(200, "收货成功", purchaseId);
            }
            return new Result<>(500, "采购信息收集失败");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @GetMapping("/existSupplier/{supplierName}")
    public Result<?> existSupplier(@PathVariable("supplierName")String supplierName) {
        Supplier one = supplierService.getOne(new QueryWrapper<Supplier>().eq("supplier_name", supplierName));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到运营商", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @GetMapping("/existSupplier/{supplierId}/{supplierName}")
    public Result<?> existSupplier(@PathVariable("supplierId")Integer supplierId, @PathVariable("supplierName") String supplierName) {
        Supplier one = supplierService.getOne(new QueryWrapper<Supplier>().eq("supplier_id", supplierId).and(i -> {
            i.eq("supplier_name", supplierName);
        }));
        if (!Objects.isNull(one)) {
            return new Result<>(200, "查询到运营商", one);
        } else {
            return new Result<>(200, "暂无记录");
        }
    }

    @PostMapping("/add")
    public Result<?> addSupplier(@RequestBody SupplierVO supplierVO) {
        Supplier supplier = new Supplier();
        supplier.setContactWay(supplierVO.getContactWay());
        supplier.setPrincipal(supplierVO.getPrincipal());
        supplier.setProductType(supplierVO.getProductType());
        supplier.setSupplierId(supplierVO.getSupplierId());
        supplier.setSupplierName(supplierVO.getSupplierName());
        supplier.setSupplierNo(WebUtil.getRandomString(10));
        supplier.setStatus(1);
        boolean status = supplierService.save(supplier);
        if (status) {
            return new Result<>(200, "添加成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @PutMapping("/updateSupplier")
    public Result<?> updateSupplier(@RequestBody SupplierVO supplierVO) {
        Supplier supplier = new Supplier();
        supplier.setProductType(supplierVO.getProductType());
        supplier.setSupplierId(supplierVO.getSupplierId());
        supplier.setContactWay(supplierVO.getContactWay());
        supplier.setPrincipal(supplierVO.getPrincipal());
        supplier.setSupplierName(supplierVO.getSupplierName());
        boolean status = supplierService.updateById(supplier);
        if (status) {
            return new Result<>(200, "更新成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @DeleteMapping("/deleteSupplier/{supplierId}")
    public Result<?> deleteSupplier(@PathVariable("supplierId")Integer supplierId) {
        boolean status = supplierService.removeById(supplierId);
        if (status) {
            return new Result<>(200, "删除成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }

    @PutMapping("/updateState/{supplier_id}/{status}")
    public Result<?> updateState(@PathVariable("supplier_id") Integer supplier_id, @PathVariable("status") boolean status) {
        Supplier supplier = supplierService.getById(supplier_id);
        if (status) {
            supplier.setStatus(1);
        } else {
            supplier.setStatus(0);
        }
        boolean update = supplierService.updateById(supplier);
        if (update) {
            return new Result<>(200, "更新成功", true);
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }
}
