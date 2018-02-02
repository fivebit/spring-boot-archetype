package com.fivebit.tools;

import com.fivebit.tools.config.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

//@SpringBootApplication 等价于：ComponentScan(bean 扫描)，EnableAutoConfiguration(自动配置)，Configuration(表示该为配置)
@SpringBootApplication
@MapperScan("com.fivebit.tools.dao")
@EnableConfigurationProperties
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
public class ArcheTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArcheTypeApplication.class, args);
	}
}
