/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.entity;
import org.opendaylight.nemo.renderer.openflow.entity.NodeBean;
import org.opendaylight.nemo.renderer.openflow.entity.LinkBean;
import org.opendaylight.nemo.renderer.openflow.entity.HostBean;

import java.util.List;

public class ResourceBean {
	public List<NodeBean> getNodelist() {
		return nodelist;
	}

	public void setNodelist(List<NodeBean> nodelist) {
		this.nodelist = nodelist;
	}

	private List<NodeBean> nodelist;

	public List<LinkBean> getLinklist() {
		return linklist;
	}

	public void setLinklist(List<LinkBean> linklist) {
		this.linklist = linklist;
	}

	private List<LinkBean> linklist;

	public List<HostBean> getHostlist() {
		return hostlist;
	}

	public void setHostlist(List<HostBean> hostlist) {
		this.hostlist = hostlist;
	}

	private List<HostBean> hostlist;
	
	//getter ,setter 
}

