package by.training.linkchecker.model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.training.linkchecker.utils.GeneralUtils;


/**
 * Object representing page/file with methods to work with it.
 */
public class Page {

	private Document page = null;

    /**
     * Download file by url and check type of this file.
     * @param args String array with arguments for command.
     * @return Report with information about command execution.
     */
	public Report open(String... args) {

		try {
			return downloadFile(args);
		} catch (UnknownHostException s) {
			page = null; /*null for logic when check works only for last open and if last open is bad check fails*/
			return new Report(false, "(unknown host)");
		} catch (HttpStatusException e) {
			page = null;
			return new Report(true, "(HTTP status code:" + e.getStatusCode() + ")");
		} catch (IllegalArgumentException e) {
			page = null;
			return new Report(false, "(Malformed URL)");
		} catch (UnsupportedMimeTypeException e) {
			page = null;
			return new Report(true, "(file downloaded, file is not a page (" + e.getMimeType() + "))");
		} catch (IOException e) {
			page = null;
			return new Report(false, "(Can't read from the source)");

		} catch (Throwable e) {
			page = null;
			return new Report(false, e.getStackTrace().toString());
		}
	}

    /**
     * Utility method for proper downloading and timeout.
     * @param String array with arguments for command.
     * @return Report with information about command execution.
     */
	private Report downloadFile(String... args) throws Throwable {
		
		System.out.println("Opening file... " + args[0]);

		
		long timeout;

		/*Checking bad arguments input for this command and setting default if so*/
		if (args.length == 1) {
			timeout = 3000;
		} else if (GeneralUtils.validInteger(args[1])) {
			timeout = Integer.parseInt(args[1]);
		} else {
			timeout = 3000;
			System.out.println("(warning: incorrect timeout, using default: 3s)");
		}
		
		/*Using executor for proper working of timeout without stucking*/
		/*jsoup .timeout and urlconnection setreadtimeout doesnt work properly with one thread*/
		Future<Report> control = Executors.newSingleThreadExecutor().submit(new Callable<Report>() {
			
			@Override
			public Report call() throws IOException {
				
					String address = args[0];
					if (address.length() > 1 && address.charAt(address.length()-1) == '/') {
						address = address.substring(0, address.length()-1);
					}
					/*Pretending like good user for correct responses*/
					Response resp = Jsoup.connect(address).
							userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").
							header("Accept-Encoding","gzip,deflate,sdch").
							maxBodySize(0).
							ignoreContentType(true).
							execute();
					if (resp.contentType().contains("text/html") || resp.contentType().contains("text/xml")) {
						page = resp.parse();
					}else{
						throw new UnsupportedMimeTypeException("Isnt proper mime-type", resp.contentType(), args[0]);
					}
					return new Report(true, "");
			}
		});

		try {
			return control.get(timeout, TimeUnit.MILLISECONDS);
		} catch (TimeoutException ex) {
			control.cancel(true);
			page = null;
			return new Report(false, "(timeout)");
		} catch (InterruptedException ex) {
			page = null;
			return new Report(false, "(thread interrupted)");
		} catch (ExecutionException e) {
			/*Rethrow exception from callable, wrapping in Throwable is necessarily*/
			throw e.getCause();
		}
	}

    /**
     * Check if page contains link with href.
     * @param args String array with arguments for command.
     * @return Report with information about command execution.
     */
	public Report checkLinkByHref(String... args) {

		System.out.println("Checking link by href... " + args[0]);

		String firstArgument = args[0];

		if (pageIsNull()) {
			return new Report(false, "(the page is not available)");
		}

		Elements links = page.select("a[href]");
		Elements imports = page.select("link[href]");

		for (Element link : links) {

			if (link.attr("href").equals(firstArgument) || link.attr("abs:href").equals(firstArgument)) {
				return new Report(true, "");
			}

		}

		for (Element link : imports) {

			if (link.attr("href").equals(firstArgument) || link.attr("abs:href").equals(firstArgument)) {
				return new Report(true, "(imports)");
			}

		}

		return new Report(false, "(not found)");

	}

    /**
     * Check if page contains link with name.
     * @param args array with arguments for command.
     * @return Report with information about command execution.
     */
	public Report checkLinkbyName(String... args) {
		
		System.out.println("Checking link by name... " + args[0]);

		String firstArgument = args[0];

		if (pageIsNull()) {
			return new Report(false, "(the page is not available)");
		}

		Elements links = page.select("a[href]");
		Elements imports = page.select("link[href]");

		for (Element link : imports) {

			if (link.text().equals(firstArgument)) {
				return new Report(true, "");
			}

		}

		for (Element link : links) {

			if (link.text().equals(firstArgument)) {
				return new Report(true, "");
			}

		}

		return new Report(false, "(not found)");

	}

    /**
     * Check if page contains title with name.
     * @param args String array with arguments for command.
     * @return Report with information about command execution.
     */
	public Report checkPageTitle(String... args) {
		
		System.out.println("Checking page Title... " + args[0]);

		String firstArgument = args[0];

		if (pageIsNull()) {
			return new Report(false, "(the page is not available)");
		}
		
		String title = page.title();

		if (title.equals(firstArgument)) {
			return new Report(true, "");
		} else {
			return new Report(false, "(not found)");
		}
	}

    /**
     * Check if page contains text.
     * @param args String array with arguments for command.
     * @return Report with information about command execution.
     */
	public Report checkPageContains(String... args) {
		
		System.out.println("Checking page contains... " + args[0]);

		String firstArgument = args[0];

		if (pageIsNull()) {
			return new Report(false, "(the page is not available)");
		}

		String pageStr = page.html();

		if (pageStr.contains(firstArgument)) {
			return new Report(true, "");

		}

		return new Report(false, "(not found)");

	}

    /**
     * Utility method for checking null for skipping methods with failed open before them.
     * @param String array with arguments for command.
     * @return Report with information about command execution.
     */
	private boolean pageIsNull() {

		if (page == null) {
			return true;
		} else {
			return false;
		}
	}

}
