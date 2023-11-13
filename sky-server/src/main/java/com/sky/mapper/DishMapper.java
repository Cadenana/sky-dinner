package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    @AutoFill(value = OperationType.INSERT)
@Insert("insert into dish (id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
        "values (#{id},#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser});")
    void save(Dish dish);
@Select("select * from dish where id=#{id}")
    Dish getById(Long id);
@Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    void update(Dish dish);

    List<Dish> list(Dish dish);
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long id);
}
