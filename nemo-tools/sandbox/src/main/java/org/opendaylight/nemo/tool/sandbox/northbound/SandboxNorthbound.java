/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.northbound;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;
import org.opendaylight.nemo.tool.sandbox.SandBoxManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hj on 12/12/15.
 */
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/nemo/sandbox")
public class SandboxNorthbound {
    @Path("/create")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    @StatusCodes
            ({
                    @ResponseCode(code = 201, condition = "???"),
                    @ResponseCode(code = 409, condition = "")
            })
    public String create(
            @Context UriInfo uriInfo,
            String request) {
        List<String> formattedCommands = format(request);
        return "" + SandBoxManager.getInstance().createNetwork(formattedCommands);
    }

    private List<String> format(String request) {
        String[] arrays = request.split("\n");
        List<String> list = new ArrayList<String>();
        for (String command : arrays) {
            if (!command.trim().equals("")) {
                list.add(command);
            }
        }
        return list;
    }

//    @Path("/create")
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({MediaType.TEXT_PLAIN})
//    @StatusCodes
//            ({
//                    @ResponseCode(code = 201, condition = "???"),
//                    @ResponseCode(code = 409, condition = "")
//            })
//    public Response create(
//            @Context UriInfo uriInfo,
//            @TypeHint(CreateRequest.class) CreateRequest request) {
//        //TODO:Generate List<String> by the request.
//
//        return Response.created(uriInfo.getRequestUri()).status(409).build();
//    }

    @Path("/execute")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    @StatusCodes
            ({
                    @ResponseCode(code = 201, condition = "???"),
                    @ResponseCode(code = 409, condition = "")
            })
    public String create(
            @Context UriInfo uriInfo,
            @TypeHint(ExecuteRequest.class) ExecuteRequest executeRequest) {
        String result = SandBoxManager.getInstance().execute(executeRequest.getHostName(), executeRequest.getCommand());

        return result;
    }

    @Path("/destroy")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    @StatusCodes
            ({
                    @ResponseCode(code = 201, condition = "???"),
                    @ResponseCode(code = 409, condition = "")
            })
    public String destroy(@Context UriInfo uriInfo) {
        //TODO:Generate List<String> by the request.
        return SandBoxManager.getInstance().destroyNetwork() + "";
    }

    @Path("/crossdomain.xml")
    @GET
    @Produces({MediaType.TEXT_XML})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "Operation successful")})
    public String getXml() {
        System.out.println("getHosts running");
        return "<?xml version=\"1.0\"?> <cross-domain-policy><allow-access-from domain=\"*\"/>" +
                "<allow-http-request-headers-from domain=\"*\" headers=\"*\"/></cross-domain-policy>";
    }

    @Path("/hosts")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "Operation successful")})
    public String getHosts() {
        return SandBoxManager.getInstance().getHosts();
    }

    @Path("/switches")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "Operation successful")})
    public String getSwitches() {
        return SandBoxManager.getInstance().getSwitches();
    }

    @Path("/links")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "Operation successful")})
    public String getLinks() {
        return SandBoxManager.getInstance().getLinks();
    }

    @Path("/externals")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "Operation successful")})
    public String getExternals() {
        return SandBoxManager.getInstance().getExternals();
    }
}
