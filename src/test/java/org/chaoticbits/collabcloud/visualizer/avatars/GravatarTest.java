package org.chaoticbits.collabcloud.visualizer.avatars;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;

public class GravatarTest {

	@Test
	public void getMD5() throws Exception {
		assertEquals("da41bceff97b1cf96078ffb249b3d66e", Gravatar.instance().digest("andy"));
	}

	@Test
	public void getURL() throws Exception {
		URL url = Gravatar.instance().makeURL("andy.meneely@gmail.com");
		assertEquals("http://www.gravatar.com/avatar/c76f91c37fd0a6386de232f58f585ab0?s=80&r=g&d=wavatar", url.toString());
	}

	@Test
	public void downloads() throws Exception {
		String hash = "b76f91c37fd0a6386de232f58f585ab0";
		BufferedImage expected = ImageIO.read(getClass().getResourceAsStream(hash + ".png"));
		BufferedImage actual = Gravatar.instance().download(new URL("http://www.gravatar.com/avatar/" + hash + "?s=80&r=g&d=wavatar"));
		// ImageIO.write(actual, "PNG", new File("output/actualAvatar.png"));
		assertImageEquals("same wavatar image", expected, actual);
	}

	private void assertImageEquals(String message, BufferedImage expected, BufferedImage actual) {
		assertEquals(message + " (checking same width)", expected.getWidth(), actual.getWidth());
		assertEquals(message + " (checking same height)", expected.getHeight(), actual.getHeight());
		for (int x = 0; x < expected.getWidth(); x++)
			for (int y = 0; y < expected.getWidth(); y++)
				assertEquals(message + " (checking pixel" + x + "," + y + ")", expected.getRGB(x, y), actual.getRGB(x, y));
	}

}
