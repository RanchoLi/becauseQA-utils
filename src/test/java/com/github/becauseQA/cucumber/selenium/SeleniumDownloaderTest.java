package com.github.becauseQA.cucumber.selenium;

import org.junit.Test;

public class SeleniumDownloaderTest {

	@Test
	public void test() {
		//SeleniumDownloader downloader=new SeleniumDownloader();
		//String userdir=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources";
		//SeleniumDownloader.downloadSeleniumResources(userdir);
		//SeleniumDownloader.downloadfirefoxResources(userdir);
		
		SeleniumDownloader.getLatestSeleniumVersionNumber();
	}

}
