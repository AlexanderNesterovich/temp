package by.training.linkchecker.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import by.training.linkchecker.model.Report;
import by.training.linkchecker.utils.GeneralUtils;

public class PingCommand implements Command{
	
	private static long count;
	private static long summary;
    /**
     * Execute the command, and return the result.
     * @return report object with the result of execution.
     * @param args arguments for execution command.
     */
	@Override
	public Report executeCommand(String... args) {
		
			Long pingTime = 0L;
			Long pingCount = 0L;
			String address = args[0];
			String quantity = "3"; /* default value */
			String interval = "1000"; /* default value */
			Process proc;
			URL url = null;
			
			String urlString = args[0];
			
			/*checking if string is url, cmd cant ping url only adresses or domain names*/
			if (address.matches("^(https?|ftp)://.*$")) {
				try {
					url = new URL(urlString);
					address = url.getHost();
				} catch (MalformedURLException e1) {
					return new Report(false, "(Malformed URL)");
				}
			}
			
			
			
			/*checking for proper arguments for command*/
			if (args.length > 1 && GeneralUtils.validInteger(args[1])) {
				quantity = args[1];
			}
			if (args.length > 2 && GeneralUtils.validInteger(args[2])) {
				interval = args[2];
			}
			
			try {
				String command;
				/*selecting windows or unix/mac for proper ping commands*/
				if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
					command = "ping -n 1 -w 1000 " + address;
				} else {
					command = "ping -c 1 -w 1000 " + address;
				}
				
				long elapsedTime = 0L;
				long smallCount = 0L;
				
				/*cycle for numbers of pings*/
				
				for(int k = Integer.parseInt(quantity); k > 0; k--) {
					long start = System.nanoTime();
					proc = Runtime.getRuntime().exec(command);
					BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
					String line;
					while ((line = in.readLine()) != null) {
						if (line.contains("TTL=")) {
							elapsedTime = elapsedTime + System.nanoTime() - start;
							smallCount = smallCount + 1;
						}
					}
					
					/*sleep for interval*/
					try {
					    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(interval));
					} catch (InterruptedException e) {
					    return new Report(false, "(thread interrupted)");
					}
					
				}
				
				/*escaping divide by zero if we did not get results*/
				if (smallCount == 0) {
					return new Report(false, "(not found)");
				}else{
					long averageTime = elapsedTime/smallCount;
					pingTime = pingTime + averageTime;
					pingCount = pingCount + 1;
					count = count + 1;
					summary = summary + averageTime;
					return new Report(true, "(" + GeneralUtils.getThreeDigitsTimeInSeconds(averageTime) + "s)");
					
				}
			} catch (IOException e) {
				return new Report(false, "(command line access denied)");
			}


		}
	
	public static long getAveragePing() {
		if(count >= 1) {
			return summary/count;
		}else{
			return 0L;
		}
		
	}
}
