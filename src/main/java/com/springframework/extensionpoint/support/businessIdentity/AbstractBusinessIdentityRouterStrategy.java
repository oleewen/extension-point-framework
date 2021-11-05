package com.springframework.extensionpoint.support.businessIdentity;

import com.springframework.extensionpoint.annotation.Extension;
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
public abstract class AbstractBusinessIdentityRouterStrategy implements RouterStrategy<IdentityParam, IExtensionPoint> {

    @Override
    public List<IExtensionPoint> execute(String code, IdentityParam param) {
        List<Extension> extensionPointImplList = ExtensionPointRegister.getExtensionPointImplList(code);
        if (CollectionUtils.isEmpty(extensionPointImplList)) {
            throw new RuntimeException("extension point impl not found");
        }
        // 匹配路由特征
        List<Extension> matchedExtensionPointImpls = matchExtension(extensionPointImplList, param.getActualIdentity(), supportDimensions());
        if (CollectionUtils.isEmpty(matchedExtensionPointImpls)) {
            throw new RuntimeException("extension point impl not matched");
        }
        // 转换成扩展点实例对象返回
        return matchedExtensionPointImpls.stream().map(ExtensionPointRegister::getExtensionPointInstance).collect(Collectors.toList());
    }

    /**
     * 路由特征抽象匹配方法
     *
     * @param candidateExtIdentities 匹配候选集
     * @param businessIdentity       当前业务身份
     * @param orderedDimensionList   需要匹配的身份要素，有序
     * @return 匹配完成后的
     */
    protected abstract List<Extension> matchExtension(List<Extension> candidateExtIdentities, Map<String, String> businessIdentity, List<String> orderedDimensionList);

    /**
     * 返回支持的业务身份要素
     */
    protected abstract List<String> supportDimensions();

}
