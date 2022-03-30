package com.sdd.LibRestAPI.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdd.LibRestAPI.pojo.RespAll;
import com.sdd.LibRestAPI.pojo.RespAll2;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RespHandler {
	public static RespAll getObject(String url) {
		RespAll rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			WebResource webResource = client.resource(url.trim());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			String output = response.getEntity(String.class);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			rsp = mapper.readValue(output, RespAll.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static RespAll2 postObject(String url, Object objmhsw) throws Exception {
		RespAll2 rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			WebResource webResource = client.resource(url.trim());
			System.out.println("Req : " + mapper.writeValueAsString(objmhsw));

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,
					mapper.writeValueAsString(objmhsw));
			String output = response.getEntity(String.class);
			System.out.println(output);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			mapper.setDateFormat(df);
			rsp = mapper.readValue(output, RespAll2.class);
			System.out.println(rsp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static RespAll2 putObject(String url, Object req) throws Exception {
		RespAll2 rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			WebResource webResource = client.resource(url.trim());
			System.out.println("Req : " + mapper.writeValueAsString(req));
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class,
					mapper.writeValueAsString(req));
			String output = response.getEntity(String.class);
			System.out.println(output);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			mapper.setDateFormat(df);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, RespAll2.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static RespAll delObject(String url, Object objmhsw) {
		RespAll rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			System.out.println("Req : " + mapper.writeValueAsString(objmhsw));
			WebResource webResource = client.resource(url.trim());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class,
					mapper.writeValueAsString(objmhsw));
			String output = response.getEntity(String.class);
			System.out.println(output);

			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, RespAll.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}
}
