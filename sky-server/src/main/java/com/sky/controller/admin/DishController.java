package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api("菜品相关接口")
@Slf4j
public class DishController {
@Autowired
    private DishService dishService;
@PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO)
{
    log.info("新增菜品{}",dishDTO);
    dishService.save(dishDTO);
    return Result.success();
}
@GetMapping("/page")
@ApiOperation("菜品分页查询")
public  Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO)
{
PageResult pageResult= dishService.pageQuery(dishPageQueryDTO);
return Result.success(pageResult);
}
@DeleteMapping
@ApiOperation("菜品批量删除")
public Result delete(@RequestParam List<Long> ids)
{
    log.info("菜品批量删除{}",ids);
    dishService.deleteBatch(ids);
    return Result.success();
}
@PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result<String> startOrStop(@PathVariable Integer status,Long id)
{
    dishService.startOrStop(status,id);
    return Result.success();
}
@GetMapping("/{id}")
@ApiOperation("根据id查询菜品")
public Result<DishVO> getById(@PathVariable Long id)
{
  DishVO dish=dishService.getById(id);
    return Result.success(dish);
}
@PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO)
{
dishService.update(dishDTO);
    return Result.success();
}
@GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId)
{
    List<Dish> list=dishService.list(categoryId);
    return Result.success(list);
}
}
