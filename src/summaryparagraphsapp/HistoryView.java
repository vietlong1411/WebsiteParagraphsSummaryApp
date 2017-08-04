package summaryparagraphsapp;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * Author: Nguyen Viet Long
 * Display history view
 */
public class HistoryView extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public HistoryView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblHistory = new Label(composite, SWT.NONE);
		lblHistory.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD | SWT.ITALIC));
		lblHistory.setAlignment(SWT.CENTER);
		GridData gd_lblHistory = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_lblHistory.heightHint = 44;
		lblHistory.setLayoutData(gd_lblHistory);
		lblHistory.setText("History");
		
		// Go back to main view
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dispose();
				SummaryParagraphsAppRun window = new SummaryParagraphsAppRun();
				window.open();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton.widthHint = 89;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Back");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		
		GridData gd_btnNewButton_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton_1.widthHint = 96;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("Statistics");
		
		Combo combo = new Combo(composite, SWT.NONE);
		GridData gd_combo = new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1);
		gd_combo.heightHint = 119;
		gd_combo.widthHint = 321;
		combo.setLayoutData(gd_combo);
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		
		// Add history URL to combo
		Connection connection = ConnectionUtils.getMyConnection();
		getHistoryToCombo(connection,combo);
		
		// Choose history to view
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String historyURL = combo.getItem(combo.getSelectionIndex());
				try {
					// Show summary paragraph
					showSummary(connection,historyURL);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Go to charts view
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dispose();
				SummaryParagraphsAppRun.createChartView();
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	// Add history URL to combo
	public void getHistoryToCombo(Connection connection, Combo combo) throws SQLException {
		String sql = "SELECT DISTINCT url FROM history_website";
		PreparedStatement statement = connection.prepareStatement(sql);
	      ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  combo.add(rs.getString("url"));
	      }
	}
	
	// Show summary paragraph
	public void showSummary(Connection connection, String historyURL) throws SQLException {
		String sql = "SELECT summary FROM history_website WHERE url LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, historyURL);
		ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  text.setText(rs.getString("summary"));
	      }
	}
}
