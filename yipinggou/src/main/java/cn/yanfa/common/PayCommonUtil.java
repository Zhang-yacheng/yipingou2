package cn.yanfa.common;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
//import com.cx.wisdom.utils.gt.MD5Util;

public class PayCommonUtil {
    public static final String TIME = "yyyyMMddHHmmss";

    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";

    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;

    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize = 1000;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeOrderNum()
    {
        try
        {
            // 最终生成的订单号
            String finOrderNum = "";
            synchronized (lockObj)
            {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                long nowLong = Long.parseLong(
                        new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
                if (orderNumCount >= maxPerMSECSize)
                {
                    orderNumCount = 0L;
                }
                // 组装订单号
                String countStr = maxPerMSECSize + orderNumCount + "";
                finOrderNum = "PAYORDERNO-N" + nowLong + countStr.substring(1);
                orderNumCount++ ;
            }
            return finOrderNum;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static String getRemoteHost(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    // 随机字符串生成
    public static String getRandomString(int length)
    { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++ )
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // 生成签名
    public static String createSign(String key, String characterEncoding,
                                    SortedMap<String, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        System.out.println(sb.toString());
        String sign = MD5Util.encode(sb.toString()).toUpperCase();
        return sign;
    }

    // 请求xml组装
    public static String getRequestXml(SortedMap<String, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key)
                    || "sign".equalsIgnoreCase(key))
            {
                sb.append("<" + key + ">" + "</" + key + ">");
            }
            else
            {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("");
        return sb.toString();
    }

    // 请求方法
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr)
    {
        try
        {

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr)
            {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        }
        catch (ConnectException ce)
        {
            System.out.println("连接超时：{}" + ce);
        }
        catch (Exception e)
        {
            System.out.println("https请求异常：{}" + e);
        }
        return null;
    }

    // xml解析
    public static Map doXMLParse(String strxml)
            throws JDOMException, IOException
    {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml))
        {
            return null;
        }
        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext())
        {
            Element e = (Element)it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty())
            {
                v = e.getTextNormalize();
            }
            else
            {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        // 关闭流
        in.close();
        return m;
    }

    public static String getChildrenText(List children)
    {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty())
        {
            Iterator it = children.iterator();
            while (it.hasNext())
            {
                Element e = (Element)it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty())
                {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 再次签名，支付
     */
    public static SortedMap<Object, Object> startWXPay(String result)
    {
        try
        {
            PayConfig config = PayConfig.getInstance();
            Map<String, String> map = PayCommonUtil.doXMLParse(result);
            SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
            parameterMap.put("appid", config.getWxAppid());
            parameterMap.put("partnerid", config.getWxMchid());
            parameterMap.put("prepayid", map.get("prepay_id"));
            parameterMap.put("package", "Sign=WXPay");
            parameterMap.put("noncestr", PayCommonUtil.CreateNoncestr());
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap.put("timestamp", Long.parseLong(
                    String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
            String sign = PayCommonUtil.createSign("UTF-8", parameterMap);
            parameterMap.put("sign", sign);
            return parameterMap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建随机数
     *
     * @param
     * @return
     */
    public static String CreateNoncestr()
    {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++ )
        {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * @Description：创建sign签名
     * @param characterEncoding
     *            编码格式
     * @param parameters
     *            请求参数
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters)
    {
        PayConfig payConfig = PayConfig.getInstance();
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + payConfig.getWxSecretKey());
        String sign = MD5Util.encode(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding,
                                       SortedMap<Object, Object> packageParams)
    {
        PayConfig payConfig = PayConfig.getInstance();
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + payConfig.getWxSecretKey());
        // 算出摘要
        String mysign = MD5Util.encode(sb.toString()).toLowerCase();
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();
        return tenpaySign.equals(mysign);
    }

    /**
     * 接收微信的异步通知
     *
     * @throws IOException
     */
    public static String reciverWx(HttpServletRequest request)
            throws IOException
    {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null)
        {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }
}
