package com.dalididilo.moviehome.index.controller;


import com.dalididilo.moviehome.index.bean.ControlDevice;
import com.dalididilo.moviehome.index.service.IndexService;
import com.dalididilo.moviehome.utils.HttpTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Controller
public class IndexController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    IndexService indexService;

    // 总的请求个数
    public static final int requestTotal = 10;

    // 同一时刻最大的并发线程的个数
    public static final int concurrentThreadNum = 5;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("name",indexService.getEmpById("10001"));
        redisTemplate.opsForValue().set("name","大离弟弟咯。");
        Map<String,String> options = new HashMap<>();
        options.put("01","大离");
        options.put("02","弟弟咯");
        redisTemplate.opsForValue().set("map",options);
        return "index";
    }


    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/user/toLogin")
    public String login(Model model,HttpServletRequest request){
//        ModelAndView model = new ModelAndView("user/login");
        model.addAttribute("ctx", request.getServletContext().getContextPath());
        return "user/login";
    }

    @RequestMapping("/user/unAuth")
    public String unAuth(Model model){
//        ModelAndView model = new ModelAndView("user/login");
        model.addAttribute("msg", "未授权用户无法进行访问。");
        return "user/unAuth";
    }

    @RequestMapping("/user/logout")
    public ModelAndView logout(ModelAndView model){
//        ModelAndView model = new ModelAndView("user/login");
        model.setViewName("index");
        model.addObject("msg", "您已退出系统");
        return model;
    }
    /**
     * 控制设备的开关
     * @param initControlDeviceJson
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Controller3")
    public String Controller3(@RequestBody ControlDevice initControlDeviceJson){
        System.out.println(initControlDeviceJson.toString());
        String[] result = new String[]{"{\"code\":1,\"info\":\"下发命令成功\"}"
                ,"{\"code\":0,\"info\":\"当前控制模块已经掉线\"}"
                ,"{\"code\":-1,\"info\":\"上传字段空值\"}"};
//        int computer=(int)(Math.random()*3);
        int computer=0;

        System.out.println(result[computer]);

        return result[computer];

//        String requestJson = JSON.toJSONString(initControlDeviceJson); // 请求体。
//        System.out.println(requestJson);
//        Result result = new Result(true);
//        try {
//            result.setMessage(HttpTool.sendPost(requestJson,"http://112.53.82.25:9000/khcontrol/api/control/ControlDevice",""));
//            result.setStatusCode(200);
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.setSuccess(false);
//            result.setStatusCode(500);
//            result.setMessage("错误" + e.getMessage());
//        }
////        System.out.println(requestJson);
//        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Controller1")
    public String Controller1() throws InterruptedException{
//        线程池并发请求
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
        Semaphore semaphore = new Semaphore(concurrentThreadNum);
        for (int i = 0; i< requestTotal; i++) {
            executorService.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "创建成功   " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
//                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString());
                    semaphore.acquire();
                    try {
                        testRequestUri();
//                        System.out.println(result);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
       return "请求完成";
    }

    private static String testRequestUri() throws IOException {
        String cjsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String[] params = new String[]{
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019001\",\"sblx\":\"134\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random()*100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019002\",\"sblx\":\"135\",\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +  "\",\"sbvalue\":\""+(Math.random()*100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019003\",\"sblx\":\"101\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(int)(Math.random()*1)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019004\",\"sblx\":\"108\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(int)(Math.random()*1)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019005\",\"sblx\":\"103\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019006\",\"sblx\":\"104\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019007\",\"sblx\":\"105\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"201900401\",\"sblx\":\"120\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"201900402\",\"sblx\":\"122\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"201900403\",\"sblx\":\"124\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"201900404\",\"sblx\":\"121\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019010\",\"sblx\":\"131I\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"Ia\":\""+(Math.random() *100)+"\",\"Ib\":\""+(Math.random() *100)+"\",\"Ic\":\""+(Math.random() *100)+"\",\"In\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1000\",\"data\":[{\"sbbh\":\"03430524E43148636\",\"sblx\":\"cqtsdl_znjdx\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"ua\":\""+(Math.random() *100)+"\",\"ub\":\""+(Math.random() *100)+"\",\"uc\":\""+(Math.random() *100)+"\",\"ia\":\""+(Math.random() *100)+"\",\"ib\":\""+(Math.random() *100)+"\",\"ic\":\""+(Math.random() *100)+"\",\"tmp\":\""+(Math.random() *100)+"\",\"hum\":\""+(Math.random() *100)+"\",\"install_addr\":\"重庆市沙坪坝\"}]}",
                "{\"glbh\":\"1000\",\"data\":[{\"sbbh\":\"2019011\",\"sblx\":\"155\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"pa\":\""+(Math.random() *100)+"\",\"pb\":\""+(Math.random() *100)+"\",\"pc\":\""+(Math.random() *100)+"\",\"zt_a\":\""+(Math.random() *100)+"\",\"zt_b\":\""+(Math.random() *100)+"\",\"zt_c\":\""+(Math.random() *100)+"\",\"count_a\":\""+(Math.random() *100)+"\",\"count _b\":\""+(Math.random() *100)+"\",\"count_c\":\""+(Math.random() *100)+"\",\"pha\":\""+(Math.random() *100)+"\",\"phb\":\""+(Math.random() *100)+"\",\"phc\":\""+(Math.random() *100)+"\"}]}",
                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019012\",\"sblx\":\"150\",\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\"sbvalue\":\""+(int)(Math.random()*1)+"\"}]}",
//                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"1290\",\"sblx\":\"107\",\"cjsj\":\"2019-11-05 09:44:21\",\"sbvalue\":\"0\"}]}",
//                "{\"glbh\":\"1024\",\"data\":[{\"sbbh\":\"2019010\",\"sblx\":\"149\",\"cjsj\":\"2019-11-05 09:44:52\",\"sbvalue\":\"1\"}]}",

//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"1324\",\t\t\"cjsj\":\""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\",\t\t\"sbvalue\":\"88.1,88.2,88.3,88.4,88.5\"\t}]}",
//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"101\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"0\"\t},{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"101\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"0\"\t}]}",
//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"134\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"79.6\"\t}]}",
//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"135\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"79.6\"\t}]}",
//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"121\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"79.6\"\t}]}",
//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"122\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"79.6\"\t}]}",
//                "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"124\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"79.6\"\t}]}",
//                "{\t\"glbh\":\"10000\",\t\"data\":[{\t\t\"sbbh\":\"3\",\t\t\"sblx\":\"120\",\t\t\"cjsj\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\t\t\"sbvalue\":\"79.6\"\t}]}",

        };
//        String params = "{\t\"glbh\":\"1000\",\t\"data\":[{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"134\",\t\t\t\"cjsj\":\""+ cjsj +" \",\t\t\t\"sbvalue\":\""+ (Math.random()*100) +"\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"135\",\t\t\t\"cjsj\":\"" + cjsj + "\",\t\t\t\"sbvalue\":\""+ (Math.random()*100) +"\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"121\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\""+ (Math.random()*100) +"\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"122\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\""+ (Math.random()*100) +"\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"124\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\""+ (Math.random()*100) +"\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"120\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\""+ (Math.random()*100) +"\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"101\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\"0\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"101\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\"1\"\t\t},{\t\t\t\"sbbh\":\"3\",\t\t\t\"sblx\":\"1324\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"sbvalue\":\"88.1,88.2,88.3,88.4,88.5\"\t\t},{\t\t\t\"sbbh\":\"1\",\t\t\t\"sblx\":\"131I\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"Ia\":\""+ (Math.random()*100) +"\",\t\t\t\"Ib\":\""+ (Math.random()*100) +"\",\t\t\t\"Ic\":\""+ (Math.random()*100) +"\",\t\t\t\"In\":\""+ (Math.random()*100) +"\"\t\t},\t\t{\t\t\t\"sbbh\":\"2\",\t\t\t\"sblx\":\"131W\",\t\t\t\"cjsj\":\""+ cjsj +"\",\t\t\t\"Wa\":\""+ (Math.random()*100) +"\",\t\t\t\"Wb\":\""+ (Math.random()*100) +"\",\t\t\t\"Wc\":\""+ (Math.random()*100) +"\"\t\t}\t]}";
//        return HttpTool.sendPost(params[(int)Math.random()*9], "http://192.168.1.114:8080/cqdl/api/Controller1", "");
//        String params = "{\"access_token\":\"89701fee-4e34-4f0f-aee5-e15e683f131d\",\"sbbh\":\"1007\",\"glbh\":\"1024\",\"sblx\":\"104\",\"controlCode\":\"0\"}";
        return HttpTool.sendPost(params[(int)(Math.random()*14)], "http://192.168.1.150:8080/cqdl/api/Controller1", "");
    }


//    @RequestMapping("/user/Login")
//    public String Login(String name,String password,Model model){
//        /**
//         * 编写Shiro的逻辑认证
//         */
//
////        1、获取Subject
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
//
//        return getString(model, subject, token);
//    }
}
