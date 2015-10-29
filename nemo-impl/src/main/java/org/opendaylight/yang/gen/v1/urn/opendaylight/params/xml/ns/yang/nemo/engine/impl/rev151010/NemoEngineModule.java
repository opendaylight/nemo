package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.impl.rev151010;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.nemo.user.UserManager;
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
        DataBroker dataBroker = getDataBrokerDependency();
        RpcProviderRegistry rpcProviderRegistry = getRpcRegistryDependency();

        final IntentResolver intentResolver = new IntentResolver(dataBroker);
        final UserManager userManager = new UserManager(dataBroker, rpcProviderRegistry, intentResolver);

        final class NemoEngine implements AutoCloseable {
            @Override
            public void close() throws Exception {
                if ( null != intentResolver ) {
                    intentResolver.close();
                }

                if ( null != userManager ) {
                    userManager.close();
                }

                return;
            }
        }

        LOG.info("Initialized the NEMO engine.");

        return new NemoEngine();
    }

}
