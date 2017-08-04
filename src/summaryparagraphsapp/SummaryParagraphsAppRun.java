package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Main view
 */

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.jfree.ui.RefineryUtilities;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ScrolledComposite;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import java.awt.Canvas;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager; 

public class SummaryParagraphsAppRun {

    protected static Shell shell;
	private  Text text;
	private ProgressBar progressBar;
	private Button btnNewButton_1;
	private Composite composite_1;
	private Text text_1;
	private Label lblNewLabel;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			shell = new Shell();
			SummaryParagraphsAppRun window = new SummaryParagraphsAppRun();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		/*shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				MessageBox box = new MessageBox(shell);
				box.setText("Thank you");
				box.setMessage("Thank you for using our app !");
				box.open();
			}
		});*/
		shell.setSize(450, 300);
		shell.setText("Summary Paragraph App");
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		createContents();
		shell.setMaximized(true);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD | SWT.ITALIC));
		lblNewLabel.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_lblNewLabel.heightHint = 45;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Single paragraph summarizing");
		
		text = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		GridData gd_text = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1);
		gd_text.widthHint = 359;
		text.setLayoutData(gd_text);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton.heightHint = 26;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Summarize");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.update();
				String inputURL = text.getText();
				Document doc;
				if (inputURL == "") {
					// Show message box if user haven't enter URL
					MessageBox box = new MessageBox(shell, SWT.OK);
					box.setText("Message");
					box.setMessage("Please enter a vaid URL !");
					box.open();
					
				}
				
				else {
					try {
						// Crawl data from website
						String paragraph = "";
			    	   
						// Get the instruction to crawl data from that URL
						Connection connection = ConnectionUtils.getMyConnection();
						int id_or_class = 0;
						String id_class_name = "";
						String sql = "SELECT * FROM url_process";
					      PreparedStatement statement = connection.prepareStatement(sql);
					      ResultSet rs = statement.executeQuery();
					      boolean notInDatabase = true;
					      String host = "";
					      while (rs.next()) {
					    	  if (inputURL.contains(rs.getString("page_name"))){
					    		  id_or_class = rs.getInt("content_id_or_class");
					    		  id_class_name = rs.getString("content_id_class_name");
					    		  host = rs.getString("page_name");
					    		  notInDatabase = false;
					    	  }
					      }
					      if (notInDatabase == true) {
					    	  // Show message box if app haven't support that website
					    	  MessageBox box = new MessageBox(shell, SWT.OK);
								box.setText("Message");
								box.setMessage("So sorry, our app haven't supported that Website yet !"
										+ "\nTry another Website please !");
								box.open();
					      }
					      else {
					    	  doc = Jsoup.connect(inputURL).get();
					      // The <div> contains the content can only get by ID
							if (id_or_class == 0) {
								Element content = doc.getElementById(id_class_name);
								Elements data = content.getElementsByTag("p");
								for (Element eachData : data) {
						    		   paragraph += eachData.text() + "\n";
						    	   }
							}
							else { // The <div> contains the content can only get by class
								Elements contents = doc.getElementsByClass(id_class_name);
								for (Element element : contents) {
									Elements data = element.getElementsByTag("p");
							    	   for (Element eachData : data) {
							    		   paragraph += eachData.text() + "\n";
							    	   }
								}
							}
							
							
							// Prepare UTF-8 file to tokenize
					    	   prepareUTF8File(paragraph);
					    	   
					    	   // Use vnTokenizer
					    	   useVNTokenizer();
					    	   
					    	   // Progress bar
					   		int progressBarValue = 0;
					   		progressBar.setSelection(progressBarValue);
					    	   while (progressBarValue < 2000) {
					               progressBar.setSelection(progressBarValue);
					               try {
										Thread.sleep(1000);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
					                progressBarValue += 333;
					           }
					    	   /*try {
								Thread.sleep(3333);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}*/
					    	   
					    	   // Get all sentences (tokenized)
					    	   ArrayList<Sentence> sentences = new ArrayList<Sentence>();
					    	   getAllTokenizedSentences(sentences);
					    	   
			                   
			                   // Get all words and set sentencePos for each word (tokenized)
			                   getAllWords(sentences);
			                   
			                   // Count each word's appearance in each sentence
			                   countEachWordAppearanceInEachSentence(sentences);
			                   
			                  // Calculate each word's importance in each sentence
			                  calculateEachWordImportanceInEachSentence(sentences);
			                  
			                  // Calculate each sentence's importance
			                  calculateEachSentenceImportance(sentences);
			                                                  	
			                  // Display summary paragraph (just 5 sentences)
			                  String summaryParagraph = summarizeParagraph(paragraph,sentences);
			                  text_1.setText(summaryParagraph);
			                  
			                  CrawledWebsite website = new CrawledWebsite(host,inputURL,summaryParagraph);
			                  for (String keyword : website.getKeywords()) {
			                	  System.out.print(keyword + " ");
			                  }
			                  System.out.println("");
			                  	                  
			               // Test
			                  for (Sentence sentence : sentences) {
			               	   System.out.println(sentence.getSentenceText() + " " + sentence.getSentencePos()
			               	   + " " + sentence.getSentenceImportance());
			               	   //sentence.showWordsInSentence();
			                  }  
					      }
				    	   
						
					      }
		                   
					 catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} 
			       
			});
		
		progressBar = new ProgressBar(composite, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_progressBar.heightHint = 19;
		progressBar.setLayoutData(gd_progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(2000);
		
		btnNewButton_1 = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton_1.widthHint = 77;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("History");
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Go to History view
				composite.dispose();
				composite_1.dispose();
				/*try {
					HistoryView historyView = new HistoryView(shell, SWT.NONE);
									      
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				shell.open();
				shell.layout();*/
				try {
					createHistoryView();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_1 = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		
	}
	
	// Prepare UTF-8 file to tokenize
	public void prepareUTF8File(String paragraph) throws IOException{
		File inputFileDir = new File("D:\\EclipseWorkplace\\SummaryParagraphsApp\\input.txt");
 	   Writer writeInputFile = new BufferedWriter(new OutputStreamWriter(
 	   new FileOutputStream(inputFileDir), "UTF8")); 
 	   writeInputFile.append(paragraph).append("\r\n");
 	   writeInputFile.close();
	}
	
	// Use vnTokenizer
	public void useVNTokenizer() throws IOException {
		String runVNTokenizer = "cmd /c D:\\EclipseWorkplace\\SummaryParagraphsApp\\vnTokenizer.bat" +
		    	   " vnTokenizer -i input.txt -o output.txt -sd" ;
		    	   Runtime.getRuntime().exec(runVNTokenizer);
	}
	
	// Get all sentences (tokenized)
	public void getAllTokenizedSentences(ArrayList<Sentence> sentences) throws IOException {
		File outputFileDir = new File("D:\\EclipseWorkplace\\SummaryParagraphsApp\\output.txt");
 	   BufferedReader readInputFile = new BufferedReader(
		   		   new InputStreamReader(
		                         new FileInputStream(outputFileDir), "UTF8"));
			String readData;
			int sentencePos = 0;
	   		while ((readData = readInputFile.readLine()) != null) {
	   		    sentences.add(new Sentence(readData,sentencePos));
	   		    sentencePos++;
	   		}
        readInputFile.close();
	}
	
	// Get all words and set sentencePos for each word (tokenized)
	public void getAllWords(ArrayList<Sentence> sentences) {
		int sentencePos = 0;
        for (Sentence sentence : sentences) {
     	   for (String eachWord : sentence.getSentenceText().split(" ")) {
     		   sentence.addWordInSentence(new Word(eachWord,sentencePos));
     	   }
     	   sentencePos++;
        }
	}
	
	// Count each word's appearance in each sentence
	public void countEachWordAppearanceInEachSentence(ArrayList<Sentence> sentences) {
		int count = 0;
        for (Sentence sentence : sentences) {
     	   for (Word word : sentence.getWordsInSentence()) {
     		   for (Sentence sentence1 : sentences) {
     			   for (Word word1 : sentence1.getWordsInSentence()) {
     				   if (word1.getWordText().equals(word.getWordText())) {
     					   count++;
     				   }
     			   }
     			   word.setAppearInEachSentence(count);
     			   count = 0;
     		   }
     	   }
        }
	}
	
	// Calculate each word's importance in each sentence
	public void calculateEachWordImportanceInEachSentence(ArrayList<Sentence> sentences) {
		double denominator = 0.0;
        for (Sentence sentence : sentences) {
      	  for (Word word : sentence.getWordsInSentence()) {
      		  for (int appearInEachSentence : word.getAppearInEachSentence()) {
      			  denominator += Math.pow(appearInEachSentence, 2);
      		  }
      		  denominator = Math.sqrt(denominator);
      		  for (int appearInEachSentence : word.getAppearInEachSentence()) {
      			  double importance =  appearInEachSentence / denominator;
      			  word.setWordImportanceInEachSentence(importance);
      		  }
      		  denominator = 0.0;
      	  }
        }
	}
	
	// Calculate each sentence's importance
	public void calculateEachSentenceImportance(ArrayList<Sentence> sentences) {
		double sentenceImportance = 0.0;
        for (Sentence sentence : sentences) {
      	  for (Word word : sentence.getWordsInSentence()) {
      		  sentenceImportance += word.getWordImportanceInEachSentence()
      				  .get(sentence.getSentencePos());
      	  }
      	  sentence.setSentenceImportance(sentenceImportance);
      	  sentenceImportance = 0.0;
        }
	}
	
	// Summarize paragraph (50% total sentences)
	public String summarizeParagraph(String paragraph, ArrayList<Sentence> sentences) {
		if (sentences.size() < 5) {
      	  return paragraph;
        }
        else {
      	  ArrayList<Sentence> tempSentences = new ArrayList<Sentence>();
      	  for (int countSentence = 0; countSentence < sentences.size(); countSentence++) {
      		  tempSentences.add(sentences.get(countSentence));
      	  }
      	  // Sort sentences according to sentenceImportance
      	  for (int pos1 = 0; pos1 < tempSentences.size() - 1; pos1++) {
      		  for (int pos2 = pos1 + 1; pos2 < tempSentences.size(); pos2++) {
      			  if (tempSentences.get(pos1).getSentenceImportance() < 
      				  tempSentences.get(pos2).getSentenceImportance()) {
      				  swap(tempSentences.get(pos1), tempSentences.get(pos2));	                				  
      			  }
      		  }
      	  }
      	  // Sort 5 most importance sentences according to sentencePos
      	  for (int pos1 = 0; pos1 <= (tempSentences.size() / 2) - 1; pos1++) {
      		  for (int pos2 = pos1 + 1; pos2 <= tempSentences.size() / 2; pos2++) {
      			  if (tempSentences.get(pos1).getSentencePos() >
      					  tempSentences.get(pos2).getSentencePos()) {
      			  swap(tempSentences.get(pos1), tempSentences.get(pos2));
      			  }
      		  }
      	  }
      	  // Get summary paragraph
      	  String summaryResult = "";
      	  for (int pos = 0; pos <= tempSentences.size() / 2; pos++) {
      		  summaryResult += tempSentences.get(pos).getSentenceText() + "\n";
      	  }
      	  return summaryResult.replace("_", " ");
        }
	}
	
	// Swap necessary data of 2 sentences
	// Note: this function doesn't swap wordsInSentence
	public void swap(Sentence sentence1, Sentence sentence2) {
		String tempSentenceText = sentence1.getSentenceText();
		sentence1.setSentenceText(sentence2.getSentenceText());
		sentence2.setSentenceText(tempSentenceText);
		int tempSentencePos = sentence1.getSentencePos();
		sentence1.setSentencePos(sentence2.getSentencePos());
		sentence2.setSentencePos(tempSentencePos);
		double tempSentenceImportance = sentence1.getSentenceImportance();
		sentence1.setSentenceImportance(sentence2.getSentenceImportance());
		sentence2.setSentenceImportance(tempSentenceImportance);
	}
	
	
	// Create history view
	public static void createHistoryView() throws ClassNotFoundException, SQLException {
		HistoryView historyView = new HistoryView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
	// Create chart view
	public static void createChartView() {
		ChartView barChart = new ChartView (shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
}
