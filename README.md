# Microservices-Redis-Application-RadisDB-Redis-Different-Service

# Pom Dependencies for Redis 
1. spring data Redis.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

2. Jedis dependency
```xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
 <groupId>redis.clients</groupId>
 <artifactId>jedis</artifactId>
 <version>4.4.6</version>
</dependency>
```

Note : above version less than <version>5.X.X</version>

3. add @EnableCaching in main class 

4. Create a RedisConfig class in config package.
```java
package com.redisdatabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisCacheConfig {

    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration);
    }

    // access this above connection with redis template
    @Bean
    public RedisTemplate<String, Object> template(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}

```
5. implements Serializable in both entity and dto class 

6. In ServiceImpl class there are 2 ways in which I can save the data in redis.
```properties
this.redisTemplate.opsForHash().put(HASH_KEY, product.getProductId(), product); // save as a hash value 
this.redisTemplate.opsForValue().set(product.getProductId(), product); // save from key and value.
```

7. For GetById, DeleteById, Update operation use
```java
@Cacheable(key = "#productId", value = "Product") // get
@CacheEvict(key = "#productId", value = "Product") // delete
@CachePut(key = "#productId", value = "Product") // update 
```

# Project 2 
# User-Redis-SpringBoot-service and User-FeignClient-Service.
```properties
1. Here we will create a redis-spring boot user-service to communicate with mysql and implement redis.
2. From User-Feign-Client service expose api's to communicate with postman.
3. GetById, Update and Delete operation use redis for all this and check the behaviour.
```

# NOTE : To Get the same Exception of the called service from the caller service use 
```properties
@ExceptionHandler(FeignNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(FeignNotFoundException ex) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int end = ex.getMessage().indexOf("}");
        int start = ex.getMessage().indexOf("{");

        ApiResponse apiResponse = objectMapper.readValue(ex.getMessage().substring(start, end + 1), ApiResponse.class);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
```
# Note :
```properties
 either use @cachable or use redistemplate.optforhash.set, since both will store individually.
 Also redistemplate will give more power to user to set the ttl.
```

# Set the TTL i.e. expiry time of the key 
```properties
this.redisTemplate.opsForValue().set(userId, userClass, 60, TimeUnit.SECONDS);
```


# Links for tutorials 
1. https://www.youtube.com/watch?v=oRGqCz8OLcM&ab_channel=JavaTechie
2. https://www.youtube.com/watch?v=vpe4aDu5ixI&ab_channel=JavaTechie
