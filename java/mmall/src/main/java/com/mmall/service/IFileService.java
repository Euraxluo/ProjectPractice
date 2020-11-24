package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @InterfaceName IFileService
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-1 下午10:20
 */
public interface IFileService {
    String upload(MultipartFile file,String path);
}
