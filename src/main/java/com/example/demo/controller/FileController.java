package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author zhangwenjuan
 * @date 2022/5/16
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    private static String ip = "http://localhost";


    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {//这里的file用来接收前台传过来的对象
        String originalFilename = file.getOriginalFilename();//从file对象里获取到文件的名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //获取到当前项目所在的文件夹的路径，然后加上后面的具体路径和文件名称就能获取到这个文件
        String rootFilePath = System.getProperty("user.dir") + "/zwjpro_springboot/src/main/resources/files/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        return Result.success(ip + ":" + port + "/files/" + flag); // 返回结果 url
    }

    /**
     * 下载接口
     * @param flag
     * @param response
     */
    @GetMapping("/flag")
    public void getFiles(@PathVariable String flag, HttpServletResponse response){//把前台传入的flag作为参数传进来
        OutputStream os;//新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/zwjpro_springboot/src/main/resources/files"; //定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath); //获取到根路径下面的文件的所有文件名称
        //根据唯一表识flag从文件里面去找，找到相匹配的文件然后通过输出流返回出来
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if(StrUtil.isNotEmpty(fileName)){//判断这个文件名是否为空，即是否为存在这个文件
                response.addHeader("Content-Dispostition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);//通过文件的路径读取文件字节流
                os = response.getOutputStream(); //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            System.out.println("文件下载失败");
        }

    }
}