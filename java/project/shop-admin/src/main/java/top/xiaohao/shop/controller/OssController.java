package top.xiaohao.shop.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohao.shop.utils.AliyunOssUtil;
import top.xiaohao.shop.utils.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin
@Slf4j
public class OssController {

    @Autowired
    private AliyunOssUtil ossUtil;

    @RequestMapping("/uploadImage")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> upload(@RequestParam("name") String folderName,
                         @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                File newFile = new File(fileName);
                try (FileOutputStream os = new FileOutputStream(newFile)) {
                    os.write(file.getBytes());
                    file.transferTo(newFile);
                    String path = ossUtil.upload(folderName, newFile);
                    log.info("文件上传成功，路径：{}", path);
                    return new Result<>(200, "上传成功", path);
                } catch (Exception e) {
                    log.error("文件上传失败", e);
                    return new Result<>().error(500, "上传失败");
                } finally {
                    if (newFile.exists()) {
                        FileUtils.forceDelete(newFile);
                    }
                }
            }
        }
        return new Result<>().error(500, "上传失败");
    }
}
