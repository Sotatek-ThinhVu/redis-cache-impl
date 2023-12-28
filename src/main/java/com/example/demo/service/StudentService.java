package com.example.demo.service;

import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
@Service
public class StudentService {
    private static final String HASH_KEY = "STUDENT";
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    private HashOperations<Object, String, Student> hashOperations;

    @Autowired
    public StudentService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(Student student) {
        hashOperations.put(HASH_KEY, student.getId(), student);
    }

    public void saveList(List<Student> students){
        redisTemplate.opsForList().rightPushAll(HASH_KEY,students);
    }
    public Map<String, Student> findAll() {
        return hashOperations.entries(HASH_KEY);
    }
    public Student findById(String id) {
        return hashOperations.get(HASH_KEY, id);
    }
    public void update(Student student) {
        save(student);
    }
    public void delete(String id) {
        hashOperations.delete(HASH_KEY, id);
    }
}
