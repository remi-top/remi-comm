package ai.remi.comm.log.service;

import ai.remi.comm.log.domain.OperateLog;
import ai.remi.comm.util.auth.CompanyAuthInfo;
import ai.remi.comm.util.bean.BeanCopyUtils;
import ai.remi.sys.api.client.SysClientApi;
import ai.remi.sys.domain.dto.post.LogRecordPostDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 异步调用日志服务
 */
@Service
public class AsyncLogService {

    /**
     * 通过OpenFeign客户端注入
     */
    @Resource
    private SysClientApi sysClientApi;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveOperateLog(OperateLog operateLog) {
        CompanyAuthInfo companyAuthInfo = new CompanyAuthInfo();
        companyAuthInfo.setDeptTenantId(operateLog.getDeptTenantId());
        companyAuthInfo.setCompanyTenantId(operateLog.getCompanyTenantId());
        ai.remi.comm.util.auth.RequestHolder.add(companyAuthInfo);
        LogRecordPostDTO logRecordPostDTO = BeanCopyUtils.copyPropertiesThird(operateLog, LogRecordPostDTO.class);
        sysClientApi.addLogRecord(logRecordPostDTO);
    }
}
