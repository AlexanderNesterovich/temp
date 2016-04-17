package by.training.image_downloader;
/**
 * Program for automated downloading from http://konachan.net/.
 */
public class App {
	/**
	 * @param args 3 arguments need 1. Output path 2. Search query for pictures 3. Number of pages to download. 
	 * Default number of pages = 1. Input for example: C:/images Evangelion 3
	 */
	public static void main(String[] args) {
		
		String message = "3 arguments required! 1. Output path 2. Search query for pictures 3. Number of pages to download."
				+ " For example: C:/images Evangelion 3";
		
		//check for good input
		if (args.length > 2) {
			int count = 0;
			if(!(args[2] == null)) {
				try {
					//check for int
					count = Integer.parseInt(args[2]);
					if (count < 0) {
						count = 0;
						System.out.println("Negative number of pages, using default 1");
					}
				} catch (NumberFormatException e) {
					System.out.println(message);
					System.exit(0);
				}
			}
			//things for actual download
			Downloader dl = new Downloader(args[0], args[1], count);
			dl.download();
		}else{
			System.out.println(message);
			System.exit(0);
		}

	}

}
