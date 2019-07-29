package com.hsc.kunkun.service.impl;

import com.hsc.kunkun.service.FileService;
import com.hsc.kunkun.util.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/26 19:08
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private static String uploadFile_url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    @Override
    public String uploadFile(String accessToken, MultipartFile file) {
        uploadFile_url = uploadFile_url.replace("ACCESS_TOKEN", accessToken).replace("TYPE","file");
        File file1 = new File("e:/"+file.getOriginalFilename());
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(uploadFile_url,file1);
        System.out.println("jsonObject:" + jsonObject.toString());
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                System.out.println("素材上传失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg"));
            }
        }
        String media_idjson="{\"media_id\":\""+jsonObject.getString("media_id")+"\"}";//转成json格式 返回
        return media_idjson;
    }

}
