package org.chaoticbits.collabcloud.visualizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;
import org.chaoticbits.collabcloud.visualizer.command.Visualize;
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
		visualize.since(GitLoaderTest.SECOND_COMMIT_ID).useGit().call();
		assertEquals(GitLoaderTest.SECOND_COMMIT_ID, visualize.getSince());
		assertEquals(800, visualize.getHeight());
		assertEquals(800, visualize.getWidth());
		assertEquals(100, visualize.getMaxTokens());
		assertEquals(1.0d, visualize.getLeafCutoff(), 0.01);
		assertEquals(500, visualize.getSpiralSteps());
		assertEquals(350d, visualize.getSpiralMaxRadius(), 0.01);
		assertEquals(1.0d, visualize.getSquashdown(), 0.01);
	}

	@Test
	public void callRequiresRepo() throws Exception {
		Visualize visualize = new Visualize(TEST_BED);
		try {
			visualize.call();
			fail("exception should have been thrown");
		} catch (VisualizerConfigException e) {
			assertEquals(
					"Missing the following properties: since revision, SVN or Git",
					e.getMessage());
		}
	}

}
