package org.chaoticbits.collabcloud;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaSummaryToken;
import org.chaoticbits.collabcloud.codeprocessor.java.JavaTokenType;
import org.chaoticbits.collabcloud.vc.git.GitLoader;
import org.chaoticbits.collabcloud.vc.git.GitLoaderTest;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.samples.ShowLayouts;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;

/**
 * Based on {@link ShowLayouts} example - just a test driver to visualize the layout of the a given token
 * network.
 * @author andy
 * 
 */
public class NetworkVisualize extends JApplet {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(NetworkVisualize.class);
	private static final long serialVersionUID = 1L;
	protected static Graph<ISummaryToken, Long> graph;

	private static final class LayoutChooser implements ActionListener {
		private final JComboBox jcb;
		private final VisualizationViewer<ISummaryToken, Long> vv;

		private LayoutChooser(JComboBox jcb, VisualizationViewer<ISummaryToken, Long> vv) {
			this.jcb = jcb;
			this.vv = vv;
		}

		public void actionPerformed(ActionEvent arg0) {
			Object[] constructorArgs = { graph };

			Class<? extends Layout<ISummaryToken, Long>> layoutC = (Class<? extends Layout<ISummaryToken, Long>>) jcb.getSelectedItem();
			// Class lay = layoutC;
			try {
				Constructor<? extends Layout<ISummaryToken, Long>> constructor = layoutC.getConstructor(new Class[] { Graph.class });
				Object o = constructor.newInstance(constructorArgs);
				Layout<ISummaryToken, Long> l = (Layout<ISummaryToken, Long>) o;
				l.setInitializer(vv.getGraphLayout());
				l.setSize(vv.getSize());

				LayoutTransition<ISummaryToken, Long> lt = new LayoutTransition<ISummaryToken, Long>(vv, vv.getGraphLayout(), l);
				Animator animator = new Animator(lt);
				animator.start();
				vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
				vv.repaint();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static JPanel getGraphPanel() {

		graph = createGraph();

		final VisualizationViewer<ISummaryToken, Long> vv = new VisualizationViewer<ISummaryToken, Long>(
				new FRLayout<ISummaryToken, Long>(graph));

		vv.getRenderContext().setVertexFillPaintTransformer(
				new PickableVertexPaintTransformer<ISummaryToken>(vv.getPickedVertexState(), Color.red, Color.yellow));

		vv.getRenderContext().setVertexLabelTransformer(new Transformer<ISummaryToken, String>() {
			public String transform(ISummaryToken input) {
				return input.getToken();
			}
		});

		final DefaultModalGraphMouse<ISummaryToken, Long> graphMouse = new DefaultModalGraphMouse<ISummaryToken, Long>();
		vv.setGraphMouse(graphMouse);

		final ScalingControl scaler = new CrossoverScalingControl();

		JButton plus = new JButton("+");
		plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaler.scale(vv, 1.1f, vv.getCenter());
			}
		});
		JButton minus = new JButton("-");
		minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaler.scale(vv, 1 / 1.1f, vv.getCenter());
			}
		});
		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Layout<ISummaryToken, Long> layout = vv.getGraphLayout();
				layout.initialize();
				Relaxer relaxer = vv.getModel().getRelaxer();
				if (relaxer != null) {
					// if(layout instanceof IterativeContext) {
					relaxer.stop();
					relaxer.prerelax();
					relaxer.relax();
				}
			}
		});

		JComboBox modeBox = graphMouse.getModeComboBox();
		modeBox.addItemListener(((DefaultModalGraphMouse<ISummaryToken, Long>) vv.getGraphMouse()).getModeListener());

		JPanel jp = new JPanel();
		jp.setBackground(Color.WHITE);
		jp.setLayout(new BorderLayout());
		jp.add(vv, BorderLayout.CENTER);
		Class<? extends Layout>[] combos = getCombos();
		final JComboBox jcb = new JComboBox(combos);
		// use a renderer to shorten the layout name presentation
		jcb.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				String valueString = value.toString();
				valueString = valueString.substring(valueString.lastIndexOf('.') + 1);
				return super.getListCellRendererComponent(list, valueString, index, isSelected, cellHasFocus);
			}
		});
		jcb.addActionListener(new LayoutChooser(jcb, vv));
		jcb.setSelectedItem(FRLayout.class);

		JPanel control_panel = new JPanel(new GridLayout(2, 1));
		JPanel topControls = new JPanel();
		JPanel bottomControls = new JPanel();
		control_panel.add(topControls);
		control_panel.add(bottomControls);
		jp.add(control_panel, BorderLayout.NORTH);

		topControls.add(jcb);
		bottomControls.add(plus);
		bottomControls.add(minus);
		bottomControls.add(modeBox);
		bottomControls.add(reset);
		return jp;
	}

	private static Graph<ISummaryToken, Long> createGraph() {
		GitLoader loader;
		try {
			log.info("Loading contribution network...");
			loader = new GitLoader(new File("testgitrepo/.git"), GitLoaderTest.SECOND_COMMIT_ID);
			Graph<ISummaryToken, Long> cn = loader.contributionNetwork();
			return cn;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void start() {
		this.getContentPane().add(getGraphPanel());
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Class<? extends Layout>[] getCombos() {
		List<Class<? extends Layout>> layouts = new ArrayList<Class<? extends Layout>>();
		layouts.add(KKLayout.class);
		layouts.add(FRLayout.class);
		layouts.add(CircleLayout.class);
		layouts.add(SpringLayout.class);
		layouts.add(SpringLayout2.class);
		layouts.add(ISOMLayout.class);
		return layouts.toArray(new Class[0]);
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		JPanel jp = getGraphPanel();

		JFrame jf = new JFrame();
		jf.getContentPane().add(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
	}
}
