package org.wso2.esb.sample.task;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.startup.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class DataPopulationTask implements Task, ManagedLifecycle {

	private Log log = LogFactory.getLog(DataPopulationTask.class);

	private String rmsEndpoint;
	private String storeFileLocation;
	private String name;

	public void execute() {
		log.debug("DataPopulationTask begin");

		if (StringUtils.isBlank(rmsEndpoint)) {
			log.error("RMS Endpoint is not set");
			return;
		}
		
		if (StringUtils.isBlank(storeFileLocation)) {
			log.error("Store file location is not set");
			return;
		}
		
		List<DataBean> dataList = getDataFromRMS();
		storeData(dataList);

		log.debug("DataPopulationTask end");
	}

	private void storeData(List<DataBean> dataList) {
		List<String> outputLines = new ArrayList<String>();
		
		for (DataBean data : dataList) {
			String line = data.getItemCode() +  "," + data.getItemQuantity();
			outputLines.add(line);
		}
		
		Path file = Paths.get(storeFileLocation);
		try {
			Files.write(file, outputLines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<DataBean> getDataFromRMS() {
		return getXMLDataFromRMS();
	}
	
	private List<DataBean> getXMLDataFromRMS() {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			URL url = new URL(rmsEndpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String line;
			
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String xml = stringBuilder.toString();
		log.info("Output from Server .... \n" + xml);
		return parseXML(xml);
	}

	private List<DataBean> parseXML(String xml) {
		List<DataBean> dataList = new ArrayList<DataBean>();
		Document dom = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			dom = builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (dom != null) {
			Element docElement = dom.getDocumentElement();
			NodeList nodeList = docElement.getElementsByTagName("items");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					DataBean dataBean = new DataBean();
					Element element = (Element) nodeList.item(i);
					dataBean.setItemCode(element.getElementsByTagName("itemCode").item(0).getTextContent());
					dataBean.setItemQuantity(element.getElementsByTagName("itemQuantity").item(0).getTextContent());
					dataList.add(dataBean);
				}
			}

		}

		return dataList;
	}

	private Map getJSONDataFromRMS(){
		Map jsonObj = null;
		try {
			URL url = new URL(rmsEndpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			log.debug("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {
				log.debug(output);
			}
			
			jsonObj = new Gson().fromJson(output, Map.class);
			// return (String)jsonJavaRootObject.get("access_token");
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	public String getRmsEndpoint() {
		return rmsEndpoint;
	}

	public void setRmsEndpoint(String rmsEndpoint) {
		this.rmsEndpoint = rmsEndpoint;
	}

	public String getStoreFileLocation() {
		return storeFileLocation;
	}

	public void setStoreFileLocation(String storeFileLocation) {
		this.storeFileLocation = storeFileLocation;
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(SynapseEnvironment arg0) {
		// TODO Auto-generated method stub
		
	}
}
