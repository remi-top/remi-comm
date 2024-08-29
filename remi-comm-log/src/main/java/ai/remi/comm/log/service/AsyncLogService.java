package ai.remi.comm.log.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 */
@Service
public class AsyncLogService {

    /**
     * 通过OpenFeign客户端注入
     */
    //@Resource
    //private IamClientApi iamClientApi;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveOperateLog(ai.remi.comm.log.domain.OperateLog operateLog) {
        //ai.remi.comm.util.auth.CompanyAuthInfo companyAuthInfo = new ai.remi.comm.util.auth.CompanyAuthInfo();
        //companyAuthInfo.setDeptTenantId(operateLog.getDeptTenantId());
        //companyAuthInfo.setCompanyTenantId(operateLog.getCompanyTenantId());
        //ai.remi.comm.util.auth.RequestHolder.add(companyAuthInfo);
        //LogRecordPostDTO logRecordPostDTO = ai.remi.comm.util.bean.BeanCopyUtils.copyPropertiesThird(operateLog, LogRecordPostDTO.class);
        //iamClientApi.addLogRecord(logRecordPostDTO);
    }
}
