package summaryparagraphsapp;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JRootPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import java.awt.FlowLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import javax.swing.JButton;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo; 

public class ChartView extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * Author: Nguyen Viet Long
	 * Display charts
	 * @param parent
	 * @param style
	 */
	public ChartView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.center = true;
		rl_composite_1.justify = true;
		rl_composite_1.fill = true;
		composite_1.setLayout(rl_composite_1);
		
		// Go back to history view
		Button btnBack = new Button(composite_1, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dispose();
				try {
					SummaryParagraphsAppRun.createHistoryView();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBack.setLayoutData(new RowData(90, SWT.DEFAULT));
		btnBack.setText("Back");
		
		Label lblChart = new Label(composite_1, SWT.NONE);
		lblChart.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD | SWT.ITALIC));
		lblChart.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChart.setAlignment(SWT.CENTER);
		lblChart.setText("Statistics");
		
		Combo combo = new Combo(composite_1, SWT.NONE);
		combo.setLayoutData(new RowData(122, SWT.DEFAULT));
		String[] comboItems = {"Host - Keyword - Quantity", "Time - Keyword - Quantity"};
		combo.setItems(comboItems);
		
		text = new Text(composite_1, SWT.BORDER | SWT.CENTER);
		text.setLayoutData(new RowData(143, SWT.DEFAULT));
		
		Button btnStatistics = new Button(composite_1, SWT.NONE);
		
		btnStatistics.setLayoutData(new RowData(87, SWT.DEFAULT));
		btnStatistics.setText("View chart");
		
		Composite composite = new Composite(this, SWT.EMBEDDED);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
	      
		
		/*Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));*/
		
		/*JRootPane rootPane = new JRootPane();
		panel.add(rootPane);*/
		
		Frame frame = SWT_AWT.new_Frame(composite);
		// Display charts
		btnStatistics.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int comboSelection = combo.getSelectionIndex();
					if (comboSelection == -1 && text.getText() == "") {
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Please choose a type of chart to display !");
						box.open();
					}
					else if (comboSelection != -1 && text.getText() == "") {
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Please enter a keyword to create chart !");
						box.open();
					}
					else if (comboSelection != -1 && text.getText() != ""){
							Statistics stat = new Statistics();
							if (comboSelection == 0) {
								try {
									frame.removeAll();
									stat.getStat(text.getText());
									ArrayList<Host> hostStat = stat.getHosts();
									
									final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
								     for (Host host : hostStat) {
								    	 dataset.addValue(host.getCount(), host.getKeyword(), host.getName());
								     }
								     JFreeChart barChart = ChartFactory.createBarChart(
									         "Host - Keyword - Quantity", 
									         "Host", "Quantity", 
									         dataset,PlotOrientation.VERTICAL, 
									         true, true, false);
									      ChartPanel chartPanel = new ChartPanel( barChart );
								        
								      frame.add(chartPanel, BorderLayout.CENTER);
								      chartPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
								      frame.doLayout();
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else {
								try {
									frame.removeAll();
									stat.getStat(text.getText());
									ArrayList<Time> timeStat = stat.getTimes();
									
									final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
								     for (Time time : timeStat) {
								    	 dataset.addValue(time.getCount(), time.getKeyword(), time.getTime());
								     }
								     JFreeChart barChart = ChartFactory.createBarChart(
									         "Time - Keyword - Quantity", 
									         "Time", "Quantity", 
									         dataset,PlotOrientation.VERTICAL, 
									         true, true, false);
									      ChartPanel chartPanel = new ChartPanel( barChart );
								        
								      frame.add(chartPanel, BorderLayout.CENTER);
								      chartPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
								      frame.doLayout();
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
				}
			});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
