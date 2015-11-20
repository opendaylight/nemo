package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.cli.renderer.impl.rev151119;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.renderer.cli.CliRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliRendererModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.cli.renderer.impl.rev151119.AbstractCliRendererModule {
    private static final Logger LOG = LoggerFactory.getLogger(CliRendererModule.class);

    public CliRendererModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public CliRendererModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, CliRendererModule oldModule, AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public AutoCloseable createInstance() {
        DataBroker dataBroker = getDataBrokerDependency();

        // TODO

        return new CliRenderer(dataBroker);
    }

}
