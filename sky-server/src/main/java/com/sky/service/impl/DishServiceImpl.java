package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void save(DishDTO dishDTO) {
        Dish  dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
dishMapper.save(dish);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
         Dish dish =  dishMapper.getById(id);
         if (dish.getStatus()== StatusConstant.ENABLE)
         {
             throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
         }
        }
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds!=null && setmealIds.size()>0)
        {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        for (Long id : ids) {
            dishMapper.deleteById(id);
        }
    }
@Transactional
    @Override
    public void startOrStop(Integer status, Long id) {
       Dish dish= Dish.builder().id(id).status(status).build();
       dishMapper.update(dish);
    }

    @Override
    public DishVO getById(Long id) {
        Dish byId = dishMapper.getById(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(byId,dishVO);
        return dishVO;
    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
    }

    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish=Dish.builder().categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }
}
