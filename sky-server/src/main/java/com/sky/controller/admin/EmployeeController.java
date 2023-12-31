package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.DishVO;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }
@PostMapping
    @ApiOperation("新增员工")

public Result save(@RequestBody EmployeeDTO employeeDTO)
{
log.info("新增员工: {}",employeeDTO);
employeeService.save(employeeDTO);
    return Result.success();
}
@GetMapping("/page")
@ApiOperation("员工分页查询")
public Result<PageResult> page( EmployeePageQueryDTO pageQueryDTO)
{
log.info("员工分页查询，参数为{}",pageQueryDTO);
PageResult pr=employeeService.pageQuery(pageQueryDTO);
return Result.success(pr);
}
@PostMapping("/status/{status}")
@ApiOperation("启用禁用员工账号")
public Result startOrStop(@PathVariable("status") Integer status,Long id)
{
    log.info("启用禁用{},{}",status,id);
    employeeService.startOrStop(status,id);
    return Result.success();
}
@GetMapping("/{id}")
@ApiOperation("根据id查询员工信息")
public Result<Employee> getById(@PathVariable Long id)
{
    String key="emp_"+ id;
    Employee employee =(Employee) redisTemplate.opsForValue().get(key);
    if (employee!=null)
    {
        return Result.success(employee);
    }
    employee =employeeService.getById(id);
    redisTemplate.opsForValue().set(key,employee);
    return Result.success(employee);
}
@PutMapping
    @ApiOperation("修改员工信息")
    public Result update( @RequestBody EmployeeDTO employeeDTO)
{
employeeService.update(employeeDTO);
return Result.success();
}
}
