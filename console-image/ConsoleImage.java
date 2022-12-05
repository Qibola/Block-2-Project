import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.io.BufferedOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
public class ConsoleImage {
	
	public static final String ANSI_CLEAR = "\u001B[H\u001B[2J";
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static String ANSI_INSERT = "\u001B[10@";
	
	public static void main(String[] args) throws InterruptedException, IOException {
		if (args.length > 0) 
		{
			switch (args[0]) 
			{
				case "rndcolor":
					colorAnim();
					break;
				default:
					loadimage(args[0]);
					break;
			}
		}	
	}

	public static void loadimage(String path) throws IOException {
		BufferedImage image = ImageIO.read(new File(path));
		int w = image.getWidth();
	   	int h = image.getHeight();
		String[][] colorarr;
		StringBuilder colorrow = new StringBuilder();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel = image.getRGB(j, i);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
			 	int blue = (pixel) & 0xff;
				colorrow.append("\u001B[38;2;" + red + ";" + green + ";" + blue + "m" + "██");
		    }
			colorrow.append("\n");
		}
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
		log.write(ANSI_INSERT + colorrow.toString());
	}

	static void zeugs() {
		StringBuilder colorrow = new StringBuilder();
		for (int i = 0; i <= 1000000; i++) {
			colorrow.append(i);
		}
		System.out.print(ANSI_INSERT + colorrow.toString());
	}

	public static void colorAnim() throws InterruptedException {
		int count = 0;
		String[] colors = {ANSI_RESET, ANSI_GREEN, ANSI_YELLOW, ANSI_RED, ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE};
		int[] zeugs = new int[86]; // 173
		count = 0;
		for (int j = 0; j <= 30; j++) {
			for (int i = 0; i <= 45; i++) {
				zeugs = new int[140]; //173
				for (int r = 0; r < zeugs.length; r++) {
					zeugs[r] = (int)(Math.random() * 7 + 1);
				}
				if (count > 3) {
					count = 0;
				}
				System.out.println(colorstrbuild(zeugs));
				count++;
				Thread.sleep(100);
			}	
		}
		System.out.print(ANSI_RESET);
	}

	public static String colorstrbuild(int[] pos) {
		String[] colors = {"", ANSI_RESET, ANSI_GREEN, ANSI_YELLOW, ANSI_RED, ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE};
		String temp = "";
		String point = "██"; //• , █
		for (int i = 0; i < pos.length; i++) {
			temp = temp + colors[pos[i]] + point;
		}
		return temp;
	}

}
