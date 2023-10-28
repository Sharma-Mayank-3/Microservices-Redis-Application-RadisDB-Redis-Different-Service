package com.userredis.serviceImpl;

import com.userredis.dto.UserClassDto;
import com.userredis.entity.UserClass;
import com.userredis.exception.ResourceNotFoundException;
import com.userredis.repository.UserClassRepository;
import com.userredis.service.UserClassService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserClassServiceImpl implements UserClassService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserClassRepository userClassRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserClassDto createUser(UserClassDto userClassDto) {

        UserClass map = this.modelMapper.map(userClassDto, UserClass.class);
        UserClass save = this.userClassRepository.save(map);
        return this.modelMapper.map(save, UserClassDto.class);

    }

    @Override
    // either use @cachable or use redistemplate.optforhash.set
    // since both will store individually.
    // also redistemplate will give more power to user to set the ttl.
//    @Cacheable(key = "#userId", value = "UserClass")
    public UserClassDto getUserById(int userId) {

        // get from redis if present.
        Object o = this.redisTemplate.opsForValue().get(userId);
        if(!Objects.isNull(o)){
            return this.modelMapper.map(o, UserClassDto.class);
        }

        log.info("db call...");
        UserClass userClass = this.userClassRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
//        this.redisTemplate.opsForValue().set(userId, userClass);
        // ttl or set the expire time of key as 60 sec.
        this.redisTemplate.opsForValue().set(userId, userClass, 60, TimeUnit.SECONDS);
        return this.modelMapper.map(userClass, UserClassDto.class);
    }

    @Override
    public List<UserClassDto> getAllUser() {
        List<UserClass> all = this.userClassRepository.findAll();
       return all.stream().map(x -> this.modelMapper.map(x, UserClassDto.class)).collect(Collectors.toList());
    }

    @Override
    @CachePut(key = "#userId", value = "UserClass")
    public UserClassDto updateUser(int userId, UserClassDto userClassDto) {
        UserClass userClass = this.userClassRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        userClass.setUserName(userClassDto.getUserName());
        userClass.setUserAge(userClassDto.getUserAge());
        UserClass save = this.userClassRepository.save(userClass);

//         update redis.
        Object o = this.redisTemplate.opsForValue().get(userId);
        if(!Objects.isNull(o)){
            UserClass map = this.modelMapper.map(o, UserClass.class);
            map.setUserName(userClassDto.getUserName());
            map.setUserAge(userClassDto.getUserAge());
            this.redisTemplate.opsForValue().set(userId, map);
        }

        return this.modelMapper.map(save, UserClassDto.class);
    }

    @Override
    @CacheEvict(key = "#userId", value = "UserClass")
    public String deleteUser(int userId) {
        UserClass userClass = this.userClassRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        this.userClassRepository.delete(userClass);

        // update redis.
        Object o = this.redisTemplate.opsForValue().get(userId);
        if(!Objects.isNull(o)){
            this.redisTemplate.opsForValue().getAndDelete(userId);
        }

        return "user-deleted";
    }
}
