package by.training.image_downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Class for downloading pictures from konachan based on webdriver.
 */
public class Downloader {

	private String path;
	private int numOfPages;
	private String tag;

	/**
	 * @param path path where to save
	 * @param tag search query
	 * @param pages number of pages
	 */
	public Downloader(String path, String tag, int pages) {
		this.path = path;
		this.numOfPages = pages;
		this.tag = tag;
	}

	/**
	 * download method
	 */
	public void download() {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage mainPage;
		try {
			//open page
			mainPage = webClient.getPage("http://konachan.net/");
			HtmlForm form = mainPage.getFirstByXPath("//form[@action='/post']");
			HtmlTextInput textField = form.getInputByName("tags");
			HtmlSubmitInput button = form.getInputByValue("Search");
			//enter search query
			textField.setValueAttribute(tag);
			HtmlPage page = button.click();
			int count = 0;
			//downloading every page and stop when we dont have another page or when number of pages to download is enough.
			while (true) {
				cyclePage(page);
				count = count + 1;
				List<HtmlAnchor> nextButtonList = (List<HtmlAnchor>) page.getByXPath("//a[@class=\"next_page\"]");
				if (nextButtonList.size() > 0 && count < numOfPages) {
					page = nextButtonList.get(0).click();
				} else {
					break;
				}
			}
			webClient.close();
			//handling exceptions
		} catch (FailingHttpStatusCodeException e1) {
			System.out.println("Website doesnt work: " + e1.getStatusCode());
			System.exit(0);
		} catch (MalformedURLException e1) {
			System.out.println("Incorrect URL");
			System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * method for saving picture.
	 * @param imageUrl url of image
	 */
	private void saveImage(String imageUrl) {
		
		//simple java downloading and saving
		try {
			URL url = new URL(URLDecoder.decode(imageUrl, "UTF-8"));
			String fileName = url.getFile();
			File folder = new File(path);
			folder.mkdirs();
			File destName = new File(folder + fileName.substring(fileName.lastIndexOf("/")));
			if(!destName.exists()) {
				System.out.println("Downloading: " + url);
				InputStream is = url.openStream();
				OutputStream os;
				os = new FileOutputStream(destName);
				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
				is.close();
				os.close();
			}else{
				//check if we already have file with this name
				System.out.println("Already exists! " + fileName.substring(fileName.lastIndexOf("/")));
			}

		} catch (FileNotFoundException e) {
			System.out.println("Folder not found! Check output path! " + path);
			System.exit(0);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding: " + imageUrl);
		} catch (IOException e) {
			System.out.println("Problem with writing: " + imageUrl);
		}



	}

	/**
	 * Method for cycling every page and get all necessary pictures.
	 * @param page every page with pictures
	 * @throws IOException because of click method, handling it in higher method.
	 */
	private void cyclePage(HtmlPage page) throws IOException {
		int count = 0;
		System.out.println(page.getTitleText());
		List<HtmlAnchor> elements = (List<HtmlAnchor>) page.getByXPath("//a[@class=\"thumb\"]");

		//actual cycle for every image
		for (HtmlAnchor el : elements) {
			HtmlPage temp;
			temp = el.click();
			HtmlImage image = temp.getFirstByXPath("//*[@id=\"image\"]");
			saveImage(image.getSrcAttribute());
			count++;

		}
		if (count == 0) {
			System.out.println("Images not found");
		}
	}

}
