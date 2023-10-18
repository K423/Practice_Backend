package com.lzh.forum.controller;

import com.lzh.forum.common.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Api(tags = "文件控制器")
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${img.path}")
    private String basePath;

    @PostMapping("/upload")
    public Result upload(MultipartFile file){

        //获取原文件名
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        //使用uuid创建新文件名，防止文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        //文件存储目录
        File dir = new File(basePath);
        //判断目录是否存在
        if (!dir.exists()){
            //如果不存在直接创建
            dir.mkdirs();
        }

        try {
            //将文件存储
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(fileName);
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        try {
            //用输入流读取文件
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //使用输出流回显文件
            ServletOutputStream outputStream = response.getOutputStream();
            //文件类型为jpg
            response.setContentType("image/jepg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                /**
                 * len = fileInputStream.read(bytes);
                 *     if (len == -1) {
                 *         break;
                 *     }
                 */
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
