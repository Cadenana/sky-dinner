package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    public void save(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);

    void startOrStop(Integer status, Long id);

    DishVO getById(Long id);

    void update(DishDTO dishDTO);

    List<Dish> list(Long categoryId);
}
