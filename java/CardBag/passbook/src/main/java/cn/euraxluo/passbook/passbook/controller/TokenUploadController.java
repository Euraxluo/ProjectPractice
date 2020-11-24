package cn.euraxluo.passbook.passbook.controller;

import cn.euraxluo.passbook.passbook.constant.Constants;
import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.RedirectLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.controller
 * TokenUploadController
 * 2020/2/25 11:47
 * author:Euraxluo
 * PassTemplate Token Upload
 */
@Slf4j
@Controller
public class TokenUploadController {
    private final StringRedisTemplate redisTemplate;

    //StringRedisTemplate默认采用的是String的序列化策略。
    //RedisTemplate默认采用的是JDK的序列化策略.保存的是字节数组.

    @Autowired
    public TokenUploadController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }

    @PostMapping("/token")
    public String tokenFileUpload(@RequestParam("merchantsId") String merchantsId,
                                  @RequestParam("passTemplateId")String passTemplateId,
                                  @RequestParam("file") MultipartFile file,
                                  RedirectAttributes redirectAttributes){
        //todo 判断商户id是否存在并且通过审核
        if (null == passTemplateId || file.isEmpty()){
            redirectAttributes.addFlashAttribute("message",
                    "passTemplateId or file is NULL");
            return "redirect:/uploadStatus";
        }
        try{
            File curfile = new File(Constants.TOKEN_DIR+merchantsId);
            if (!curfile.exists()){
                log.info("Create File: {}",curfile.mkdir());
            }
            Path path = Paths.get(Constants.TOKEN_DIR,merchantsId,passTemplateId);
            Files.write(path,file.getBytes());
            if (!writeTokenToRedis(path,passTemplateId)){
                redirectAttributes.addFlashAttribute("message",
                        "write token error");
            }else {
                redirectAttributes.addFlashAttribute("message",
                        "You successucefully uploaded '"+file.getOriginalFilename()+"'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("uploadStatus")
    public String uploadStatus(){
        return "uploadStatus";
    }

    /***
     * 将token写入redis 服务(可以写到service中)
     * @param path token 文件的路径对象
     * @param key token的redisKey
     * @return
     */
    private boolean writeTokenToRedis(Path path,String key){
        Set<String> tokens;
        try (Stream<String> stream = Files.lines(path)){
            tokens = stream.collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (!CollectionUtils.isEmpty(tokens)){
            //强转,然后得到连接,executePipelined会将多个add操作一次性发送到服务器执行
            //单机才支持,集群部署的redis不支持此操作
            redisTemplate.executePipelined((RedisCallback<Object>)connect->{
                for (String token:tokens){
                    connect.sAdd(key.getBytes(),token.getBytes());
                }
                return null;
            });
            return true;
        }
        return false;
    }


}
