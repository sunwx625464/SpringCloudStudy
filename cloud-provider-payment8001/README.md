### 创建cloud-provider-payment8001微服务提供者支付module模块
#### 创建model，修改pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloudStudy</artifactId>
        <groupId>com.swx</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.swx</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>

</project>

```

#### 创建application.yml配置文件

```yml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
    driver-class-name: com.mysql.jdbc.Driver      # mysql驱动包 com.mysql.jdbc.Driver
    url: jdbc:mysql://123.57.72.33:3306/stydudb?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 625464


mybatis:
  mapperLocations: classpath:mapper/*.xml
  type-aliases-package: com.swx.entity    # 所有Entity别名类所在包

```

#### 创建实体类

- 数据库脚本在resources目录db文件下

```java
package com.swx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    private Long id;

    private String serial;

}
```

#### 创建Dao层mapper类

```java
package com.swx.mapper;

import com.swx.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@Mapper
public interface PaymentMapper {
    /**
     * 添加
     * @param payment
     * @return
     */
    public int add(Payment payment);
    /**
     * 通过id查询
     */
    public Payment getPaymentById(@Param("id") Long id);
}

```

#### 在resources目录下创建mapper的映射文件

- 目录mapper/.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.swx.mapper.PaymentMapper">

    <insert id="add" parameterType="com.swx.entity.Payment" useGeneratedKeys="true" keyProperty="id" >
        INSERT INTO payment(SERIAL) VALUES(#{serial});
    </insert>
    <select id="getPaymentById" resultType="com.swx.entity.Payment">
        select * from payment where id = #{id};
    </select>
</mapper>

```

#### 编写service层代码

- 创建service接口

```java
package com.swx.service;

import com.swx.entity.Payment;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
public interface PaymentService {

    public int save(Payment payment);

    public Payment getPaymentById(Long id);
}

```

- 创建service实现类

```java
package com.swx.service.impl;

import com.swx.entity.Payment;
import com.swx.mapper.PaymentMapper;
import com.swx.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public int save(Payment payment) {
        return paymentMapper.add(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentMapper.getPaymentById(id);
    }
}

```

#### 创建json封装体,新建util包

```java
package com.swx.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result(Integer code,String message){
        this(code,message,null);
    }
}

```

#### 编写controller层代码

```java
package com.swx.controller;

import com.swx.entity.Payment;
import com.swx.service.PaymentService;
import com.swx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/payment/save")
    public Result create(@RequestBody Payment payment)
    {
        int result = paymentService.save(payment);
        log.info("*****插入操作返回结果:" + result);

        if(result > 0)
        {
            return new Result(200,"插入数据库成功",result);
        }else{
            return new Result(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public Result<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果:{}",payment);
        if (payment != null) {
            return new Result(200,"查询成功",payment);
        }else{
            return new Result(444,"没有对应记录,查询ID: "+id,null);
        }
    }

}

```

#### 测试

http://localhost:8001/payment/get/1
