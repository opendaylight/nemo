package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions;


/**
 * Created by hj on 10/29/15.
 */
public interface INemoParser {
    public void format(String content);

    public boolean findRest();

    public boolean findUser();

    public boolean send();
}
