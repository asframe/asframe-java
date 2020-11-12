package com.asframe.game.rule;

import com.asframe.utils.ClassUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 基础策划规则数据管理器，解析规则数据
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/3
 */
public class BasicRuleManager
{
    private static Logger logger = LoggerFactory.getLogger(BasicRuleManager.class);
    protected HashMap<String, HashMap<Integer, Object>> jsonMap;
    protected HashMap<String, TreeSet<Integer>> jsonKeyMap;
    protected HashMap<String, IHotUpdateTable> hotUpdateMap;
    protected String packPath;
    protected String jsonPath;

    protected Class<?> ruleManagerClass;

    public void initJsonTable(String headPath,String packPath,String jsonPath){
        this.jsonMap = new HashMap<>();
        this.jsonKeyMap = new HashMap<>();
        this.hotUpdateMap = new HashMap<>();

        this.packPath = packPath;
        this.jsonPath = jsonPath;


        //通过路径获取到所所有json的名字，通过名字匹配绑定   json名字首字母大写，
        //比如 macth.json  对应的Bean  MatchBean
        File file = new File(headPath);
        File[] tempList = file.listFiles();
        System.out.println("该目录下对象个数:"+ tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                if(tempList[i].getName().indexOf("orig") != -1){
                    continue;
                }
                String[] a = tempList[i].getName().split("\\.");
                String b = a[0]+"Bean";
                String bean = b.substring(0,1).toUpperCase()+b.substring(1);
                System.out.println(bean);
                parseJsonTable(a[0],packPath+bean,jsonPath+tempList[i].getName(),false);
            }
        }
    }

    /**
     * 添加一条json的配置数据
     * @param id 配置的id
     * @param data 配置数据
     */
    public void addJsonData(int id,Object data)
    {
        HashMap<Integer, Object> map = this.jsonMap.get(data.getClass().getName());
        if(map == null)
        {
            logger.error(data.getClass().getName() + "为空");
        }
        map.put(id,data);
    }

    /**
     * 监听热更完成事件
     * @param hotTable 热更表
     * @param hotUpdateTable 回调接口
     */
    public void registerHotUpdateEvent(String hotTable,IHotUpdateTable hotUpdateTable)
    {
        this.hotUpdateMap.put(hotTable,hotUpdateTable);
    }

    protected void parseJsonTable(String fileName,String classPath,String filePath,boolean isHotUpodate) {

        Gson gson = new Gson();
        Class onwClass = null;
        try {
            onwClass = Class.forName(classPath);
        } catch (Exception e) {
            System.out.println(classPath + "：此类有错误");
            return;
        }
        HashMap<Integer, Object> list = new HashMap<>();
        TreeSet<Integer> keySet = new TreeSet<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(new File(filePath)))) {
            JsonObject jsonobject = gson.fromJson(reader, JsonObject.class);
            Set var2 = jsonobject.entrySet();
            Object[] var3 = var2.toArray();
            for (Object aVar3 : var3) {
                Map.Entry tmp = (Map.Entry) aVar3;
                Object item = gson.fromJson(tmp.getValue().toString(), onwClass);
                list.put(Integer.parseInt((String) tmp.getKey()), item);
                keySet.add(Integer.parseInt((String) tmp.getKey()));
            }
            this.jsonKeyMap.put(classPath,keySet);
            this.jsonMap.put(classPath, list);
            regRule(fileName,list);
            //查看是否需要自动注入到管理器中来

            // 这里准备热更
            if(isHotUpodate) {
                IHotUpdateTable hotUpdateTable = this.hotUpdateMap.get(fileName);
                if (hotUpdateTable != null) {
                    hotUpdateTable.hotUpdateTable(fileName);
                }
            }
        } catch (Exception e)
        {
            logger.error("转换策划表报错：" + fileName,e);
        }
    }

    protected void regRule(String fileName,Map<Integer, Object> beanMap)
    {
        Class clazz = this.ruleManagerClass;
        try
        {
            Field field = clazz.getField(fileName + "Rule");
            if(field != null)
            {
                field.setAccessible(true);
                //首字母变大写
                fileName = fileName.substring(0,1).toUpperCase() + fileName.substring(1);
                //进行自动反射
                Class<?> ruleClazz = Class.forName("ddz.rule." + fileName + "Rule");
                IRule rule = (IRule) ClassUtils.createObject(ruleClazz);
                field.set(null,rule);
                //初始化rule
                rule.init(beanMap);
            }
        }
        catch (Exception e)
        {
//            logger.error("反射Rule策划表报错：" + fileName,e);
        }
    }

    /**
     * 热更新配置
     * @param classPath 完整的类名
     */
    public void hotImportJsonTable(String fileName,String classPath,String filePath) {
//        parseJsonTable(fileName,classPath,filePath);
    }




    public String listJsons(){
        StringBuilder stringBuilder = new StringBuilder();
        jsonMap.keySet().forEach(s -> stringBuilder.append(s).append("<br>"));
        return stringBuilder.toString();
    }

    /**
     * 表转成
     * @param key
     * @return
     */
    public String showJson(String key){
        StringBuilder stringBuilder = new StringBuilder();
        jsonMap.get(key).values().forEach(o -> stringBuilder.append(o.toString()).append("<br>"));
        return stringBuilder.toString();
    }

    public HashMap getCacheBean(String fileName,String classPath,String filePath){
        Gson gson = new Gson();
        Class onwClass = null;
        try {
            onwClass = Class.forName(classPath);
        } catch (Exception e) {
            System.out.println(classPath + "：更新单个表此类有错误");
            return null;
        }
        HashMap<Integer, Object> list = new HashMap<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(new File(filePath)))) {
            JsonObject jsonobject = gson.fromJson(reader, JsonObject.class);
            Set var2 = jsonobject.entrySet();
            Object[] var3 = var2.toArray();
            for (Object aVar3 : var3) {
                Map.Entry tmp = (Map.Entry) aVar3;
                Object item = gson.fromJson(tmp.getValue().toString(), onwClass);
                list.put(Integer.parseInt((String) tmp.getKey()), item);
            }
        } catch (Exception e)
        {
            logger.error("更新单个策划表报错：" + fileName,e);
            return null;
        }
        return list;
    }
}
