package cn.yanfa.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload.realPath}")
    private String realPath;
    @Value("${upload.urlRoot}")
    private String urlRoot;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(urlRoot+"/**").addResourceLocations("file:" +realPath);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }
    /**
     * 定义访问图片转换器
     * @return
     */
    @Bean
    public BufferedImageHttpMessageConverter addConverter(){
        return new BufferedImageHttpMessageConverter();
    }
    /**
     * 添加图片转换器
     * @return
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

}
