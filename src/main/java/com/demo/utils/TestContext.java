package com.demo.utils;

public class TestContext {

	private WebDriverManager webDriverManager;

	public TestContext(){
		webDriverManager = new WebDriverManager();
	}

	public WebDriverManager getWebDriverManager() {
		return webDriverManager;
	}

}
