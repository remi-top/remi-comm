package ai.remi.comm.jdbc.config;

import ai.remi.comm.jdbc.handler.CompanyTenantHandler;
import ai.remi.comm.jdbc.handler.DeptTenantHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc 自动配置类
 */
@Configuration
@Import({CompanyTenantHandler.class, DeptTenantHandler.class,TenantConfig.class})
public class AutoConfig {



}
