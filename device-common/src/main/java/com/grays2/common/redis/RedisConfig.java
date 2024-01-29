package com.grays2.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class RedisConfig {
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Resource
    private RedisConnectionFactory factory;

    /**
     * redisTemplate 序列化使用的jdkSerializable, 存储二进制字节码, 所以自定义序列化类
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //创建一个字符串序列化器对象，用于将字符串类型的数据转换成二进制格式存储到Redis中。
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //创建一个字符串序列化器对象，用于将字符串类型的数据转换成二进制格式存储到Redis中。
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //创建一个字符串序列化器对象，用于将字符串类型的数据转换成二进制格式存储到Redis中。
        ObjectMapper om = new ObjectMapper();
        //支持序列化反序列化 java.time  LocalDateTime，LocalDate，LocalTime 并添加支持格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        om.registerModule(javaTimeModule);
        //设置ObjectMapper对象的属性访问器可见性，使其能够访问所有的属性。
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //启用默认类型识别，避免在序列化过程中出现类型错误。
        //新的方法
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        //忽略null属性
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //将ObjectMapper对象设置为JSON序列化器的属性访问器。
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //将ObjectMapper对象设置为JSON序列化器的属性访问器。
        template.setConnectionFactory(factory);
        //key序列化方式,将ObjectMapper对象设置为JSON序列化器的属性访问器。
        template.setKeySerializer(redisSerializer);
        //value序列化,将ObjectMapper对象设置为JSON序列化器的属性访问器。
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化,将ObjectMapper对象设置为JSON序列化器的属性访问器。
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }
}
