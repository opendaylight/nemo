package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.impl.rev151010;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.nemo.user.UserManager;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.NemoIntentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NemoEngineModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.impl.rev151010.AbstractNemoEngineModule {
    private static final Logger LOG = LoggerFactory.getLogger(NemoEngineModule.class);

    public NemoEngineModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public NemoEngineModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, NemoEngineModule oldModule, AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public AutoCloseable createInstance() {
        final DataBroker dataBroker = getDataBrokerDependency();
        final RpcProviderRegistry rpcProviderRegistry = getRpcRegistryDependency();

        final class NemoEngine implements AutoCloseable {
            private final IntentResolver intentResolver = new IntentResolver(dataBroker);
            private final UserManager userManager = new UserManager(dataBroker, intentResolver);
            private final RpcRegistration<NemoIntentService> rpcRegistration;

            public NemoEngine() {
                rpcRegistration = rpcProviderRegistry.addRpcImplementation(NemoIntentService.class, userManager);
            }

            @Override
            public void close() throws Exception {
                if (null != rpcRegistration) {
                    rpcRegistration.close();
                }

                if ( null != intentResolver ) {
                    intentResolver.close();
                }
            }
        }

        LOG.info("Initialized the NEMO engine.");

        return new NemoEngine();
    }

}
