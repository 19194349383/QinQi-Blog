package com.qinqi.utils;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
public class CosUtils {

    // COS的SecretId
    private static String secretId = "AKIDVfeTBi7QbjfNsCIApNhnfJfnhN3thNr6";
    // COS的SecretKey
    private static String secretKey = "ESXSM6kXN2XvDOOG5nMkI7uZ8awTuAUh";
    //文件上传后访问路径的根路径，后面要最佳文件名字与类型
    private static String rootSrc = "https://article-picture-1315647008.cos.ap-chengdu.myqcloud.com/";
    //上传的存储桶的地域，可参考根路径https://qq-test-1303******.cos.地域.myqcloud.com,此参数在COS的后台能查询。
    private static String bucketAddr = "ap-chengdu";
    //存储桶的名字，是自己在存储空间自己创建的，我创建的名字是：qq-test-1303******
    private static String bucketName = "article-picture-1315647008";

    private static COSClient getCosClient() {

        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2.1 设置存储桶的地域（上文获得）
        Region region = new Region(bucketAddr);
        ClientConfig clientConfig = new ClientConfig(region);
        // 2.2 使用https协议传输
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 返回COS客户端
        return cosClient;
    }


    public static String upLoadFile(MultipartFile file, String folderName) {
        try {
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();

            // 避免文件覆盖，获取文件的原始名称，如123.jpg,然后通过截取获得文件的后缀，也就是文件的类型
            String originalFilename = file.getOriginalFilename();
            //获取文件的类型
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            //使用UUID工具  创建唯一名称，放置文件重名被覆盖，在拼接上上命令获取的文件类型
            String fileName = UUID.randomUUID().toString() + fileType;
            // 指定文件上传到 COS 上的路径，即对象键 "images/"。最终文件会传到存储桶名字中的images文件夹下的fileName名字
            String key = folderName + fileName;
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // - 使用输入流存储，需要设置请求长度
            objectMetadata.setContentLength(inputStream.available());
            // - 设置缓存
            objectMetadata.setCacheControl("no-cache");
            // - 设置Content-Type
            objectMetadata.setContentType(fileType);
            //上传文件
            PutObjectResult putResult = getCosClient().putObject(bucketName, key, inputStream, objectMetadata);
            // 创建文件的网络访问路径
            String url = rootSrc + key;
            //关闭 cosClient，并释放 HTTP 连接的后台管理线程
            getCosClient().shutdown();
            return url;

        } catch (Exception e) {
            e.printStackTrace();
            // 发生IO异常、COS连接异常等，返回空
            return null;
        }
    }


    public static String deleteFile(String fileName, String folderName) {

        COSClient client = getCosClient();
        String key = folderName + fileName;
        try {
            client.deleteObject(bucketName, key);
            return "删除成功";
        } catch (CosClientException e) {
            return "删除失败";
        } finally {
            client.shutdown();
        }
    }

}
