package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.RecordControlHandler;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ProviderModel;
import org.apache.dubbo.rpc.model.ServiceRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RecordControlHandlerImpl implements RecordControlHandler {

    private static final String SERVICE_NAME = RecordService.class.getCanonicalName();
    private static final RegistryFactory REGISTER_FACTORY = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
    private static final ServiceRepository SERVICE_REPOSITORY = ApplicationModel.getServiceRepository();

    @Override
    public void online() throws HandlerException {
        try {
            Collection<ProviderModel> providerModelList = SERVICE_REPOSITORY.getExportedServices();
            ProviderModel providerModel = providerModelList.stream()
                    .filter(p -> p.getServiceMetadata().getDisplayServiceKey().startsWith(SERVICE_NAME))
                    .findAny().orElseThrow(() -> new HandlerException("找不到 dubbo 提供者 " + SERVICE_NAME));
            List<ProviderModel.RegisterStatedURL> statedUrls = providerModel.getStatedUrl();
            for (ProviderModel.RegisterStatedURL statedURL : statedUrls) {
                if (!statedURL.isRegistered()) {
                    Registry registry = REGISTER_FACTORY.getRegistry(statedURL.getRegistryUrl());
                    registry.register(statedURL.getProviderUrl());
                    statedURL.setRegistered(true);
                }
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void offline() throws HandlerException {
        try {
            Collection<ProviderModel> providerModelList = SERVICE_REPOSITORY.getExportedServices();
            ProviderModel providerModel = providerModelList.stream()
                    .filter(p -> p.getServiceMetadata().getDisplayServiceKey().startsWith(SERVICE_NAME))
                    .findAny().orElseThrow(() -> new HandlerException("找不到 dubbo 提供者 " + SERVICE_NAME));
            List<ProviderModel.RegisterStatedURL> statedUrls = providerModel.getStatedUrl();
            for (ProviderModel.RegisterStatedURL statedURL : statedUrls) {
                if (statedURL.isRegistered()) {
                    Registry registry = REGISTER_FACTORY.getRegistry(statedURL.getRegistryUrl());
                    registry.unregister(statedURL.getProviderUrl());
                    statedURL.setRegistered(false);
                }
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
