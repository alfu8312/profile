package com.bank.profile.rest;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.IOException;

import com.bank.profile.service.impl.RestServiceImpl;

public class RestMain {

	public static void main(String[] args) throws IOException {

		RestServiceImpl restService = new RestServiceImpl();

		int port = 8080;
		port(port);
		get("/api/customer/:customerNumber", (req, res) -> {
			res.type("application/json");
			return restService.selectCustomerProfile(req.params(":customerNumber"));
		});

		get("/api/customer/:customerNumber/account/:accountNumber", (req, res) -> {
			res.type("application/json");
			return restService.selectAccountProfile(req.params(":customerNumber"), req.params(":accountNumber"));
		});

		get("/api/removeAll", (req, res) -> {
			return restService.removeAllCollections();
		});

		System.out.println("start server >> localhost:" + port);
	}

}
