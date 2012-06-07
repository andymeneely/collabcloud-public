package org.chaoticbits.collabcloud.eclipse;

import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import org.chaoticbits.collabcloud.visualizer.command.Visualize;
import org.chaoticbits.collabcloud.visualizer.command.VisualizerConfigException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

public class RepoCloudPaintListener implements PaintListener {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RepoCloudPaintListener.class);

	private final Composite parent;

	public RepoCloudPaintListener(Composite parent) {
		this.parent = parent;
	}

	public void paintControl(PaintEvent event) {
		Visualize v;
		try {
			File srcTree;
			String srcTreeString = Activator.getDefault().getPluginPreferences().getString("srcTree");
			if (srcTreeString == null || srcTreeString.length() == 0) {
				srcTree = new File("C:/local/workspaces/workspace/CollabCloud/testgitrepo");
			} else
				srcTree = new File(srcTreeString);
			log.info("Loading source tree of: " + srcTree.toString());
			v = new Visualize(srcTree).useGit().since("bac7225dfb6ce2eb84c38f019defad21197514b6");
			BufferedImage bi = v.call();
			DirectColorModel colorModel = (DirectColorModel) bi.getColorModel();
			PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(),
					colorModel.getBlueMask());
			ImageData data = new ImageData(v.getWidth(), v.getHeight(), colorModel.getPixelSize(), palette);
			WritableRaster raster = bi.getRaster();
			int[] pixelArray = new int[4];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
					data.setPixel(x, y, pixel);
				}
			}
			Image image = new Image(parent.getDisplay(), data);
			event.gc.drawImage(image, 0, 0);
		} catch (VisualizerConfigException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
