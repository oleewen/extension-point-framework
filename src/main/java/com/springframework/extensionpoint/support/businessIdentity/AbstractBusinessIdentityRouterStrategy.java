package com.springframework.extensionpoint.support.businessIdentity;

import com.springframework.extensionpoint.model.RouterParam;
import com.springframework.extensionpoint.model.ExtensionObject;
import com.springframework.extensionpoint.model.IExtensionPoint;
import com.springframework.extensionpoint.model.RouterStrategy;
import com.springframework.extensionpoint.scan.ExtensionPointRegister;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务身份路由策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
public abstract class AbstractBusinessIdentityRouterStrategy implements RouterStrategy<IExtensionPoint> {

    @Override
    public List<IExtensionPoint> execute(RouterParam routerParam) {
        List<ExtensionObject> extensionPointImplList = ExtensionPointRegister.getExtensionPointImplList(routerParam.getExtensionPointCode());
        if (CollectionUtils.isEmpty(extensionPointImplList)) {
            throw new RuntimeException("extension point impl not found");
        }
        if (routerParam.getCustomParam() == null || !(routerParam.getCustomParam() instanceof IdentityParam)) {
            throw new RuntimeException("router param can not empty");
        }
        IdentityParam identityParam = (IdentityParam) routerParam.getCustomParam();
        // 匹配路由特征
        List<ExtensionObject> matchedExtensionPointImpls = matchExtension(extensionPointImplList, identityParam.getActualIdentity(), supportDimensions());
        if (CollectionUtils.isEmpty(matchedExtensionPointImpls)) {
            throw new RuntimeException("extension point impl not matched");
        }
        // 转换成扩展点实例对象返回
        return matchedExtensionPointImpls.stream().map(ExtensionObject::getExtensionInstance).collect(Collectors.toList());
    }

    /**
     * 路由特征抽象匹配方法
     *
     * @param candidateExtIdentities 匹配候选集
     * @param businessIdentity       当前业务身份
     * @param orderedDimensionList   需要匹配的身份要素，有序
     * @return 匹配完成后的
     */
    protected abstract List<ExtensionObject> matchExtension(List<ExtensionObject> candidateExtIdentities, Map<String, String> businessIdentity, List<String> orderedDimensionList);

    /**
     * 返回支持的业务身份要素
     *
     * @return 支持的业务身份要素
     */
    protected abstract List<String> supportDimensions();

    @Override
    public Object customGetParam(Object[] interfaceArgs) {
        return ThreadLocalContext.getContext().getParam("identity");
    }
}
