package org.chaoticbits.collabcloud.visualizer.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Properties;

import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest(Visualize.class)
@RunWith(PowerMockRunner.class)
public class VisualizerTest {

	private final IMocksControl ctrl = EasyMock.createControl();

	private static final File TEST_BED = new File("testgitrepo");

	@Before
	public void init() {
		ctrl.reset();
	}

	@Test
	public void defaults() throws Exception {
		Visualize visualize = new Visualize(TEST_BED);
		visualize.since(GitLoaderTest.SECOND_COMMIT_ID).useGit();
		assertEquals(GitLoaderTest.SECOND_COMMIT_ID, visualize.getSince());
		assertEquals(800, visualize.getHeight());
		assertEquals(800, visualize.getWidth());
		assertEquals(100, visualize.getMaxTokens());
		assertEquals(1.0d, visualize.getLeafCutoff(), 0.01);
		assertEquals(500, visualize.getSpiralSteps());
		assertEquals(350d, visualize.getSpiralMaxRadius(), 0.01);
		assertEquals(1.0d, visualize.getSquashdown(), 0.01);
		assertEquals("Lucida Sans", visualize.getFont());
		assertEquals(50, visualize.getMaxFontSize());

	}

	@Test
	public void callRequiresRepo() throws Exception {
		Visualize visualize = new Visualize(TEST_BED);
		try {
			visualize.call();
			fail("exception should have been thrown");
		} catch (VisualizerConfigException e) {
			assertEquals("Missing the following properties: since revision, SVN or Git", e.getMessage());
		}
	}

	@Test
	public void canLoadfromProperties() throws Exception {
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("testprops.properties"));
		Visualize visualize = new Visualize(TEST_BED).load(props);
		assertEquals(127, visualize.getWidth());
		assertEquals(128, visualize.getHeight());
		assertEquals(101, visualize.getMaxTokens());
		assertEquals(1.1d, visualize.getLeafCutoff(), 0.01);
		assertEquals(501, visualize.getSpiralSteps());
		assertEquals(351d, visualize.getSpiralMaxRadius(), 0.01);
		assertEquals(1.1d, visualize.getSquashdown(), 0.01);
		assertEquals("Arial", visualize.getFont());
		assertEquals(51, visualize.getMaxFontSize());
	}

}
