package top.xiaohao.shop.utils;


import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class WebUtil {

    /**
     * 将字符串渲染到客户端
     * @param response 渲染对象
     * @param string 待渲染字符串
     * return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Object json = JSON.toJSON(string);
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result<?> renderResult(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
