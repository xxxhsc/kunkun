package com.hsc.kunkun.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/26 19:08
 */
public interface FileService {

    /**
     * 文件上传并返回media_id的json包
     * @param accessToken
     * @param file
     * @return
     */
    String uploadFile(String accessToken, MultipartFile file);

}
