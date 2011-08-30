package org.chaoticbits.collabcloud.codeprocessor.java;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;

import java.io.File;

import org.chaoticbits.collabcloud.codeprocessor.CloudWeights;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ JavaParser.class, CompilationUnit.class })
public class JavaProjectSummarizerTest {

	@Test
	public void testProject() throws Exception {
		JavaProjectSummarizer summarizer = new JavaProjectSummarizer();
		mockStatic(JavaParser.class);
		CompilationUnit unit = PowerMock.createMock(CompilationUnit.class);
		File mockDir = PowerMock.createMock(File.class);
		File mockFile = PowerMock.createMock(File.class);
		CloudWeights weights = new CloudWeights();

		expect(mockDir.isDirectory()).andReturn(true).anyTimes();
		expect(mockDir.listFiles()).andReturn(new File[] { mockFile }).once();
		expect(mockFile.isDirectory()).andReturn(false).anyTimes();
		expect(mockFile.getName()).andReturn("a.java").anyTimes();
		expect(JavaParser.parse(mockFile)).andReturn(unit);
		expect(unit.accept((JavaSummarizeVisitor) EasyMock.anyObject(), (CloudWeights) EasyMock.anyObject())).andReturn(weights).once();
		PowerMock.replay();

		summarizer.summarize(mockDir);

		EasyMock.verify();
	}
}
