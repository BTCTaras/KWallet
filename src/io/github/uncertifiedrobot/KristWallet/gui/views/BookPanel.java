package io.github.uncertifiedrobot.KristWallet.gui.views;

import io.github.uncertifiedrobot.KristWallet.KWallet;
import io.github.uncertifiedrobot.KristWallet.gui.AddressTableModel;
import io.github.uncertifiedrobot.KristWallet.types.Address;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BookPanel extends JPanel {
	private static final long serialVersionUID = 8580657934407941138L;
	private JPanel richlistPanel;

	public BookPanel() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		richlistPanel = new JPanel();
		richlistPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		richlistPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Address Book", null, richlistPanel, null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_4, null);
		
		new LoadThread().start();

	}

	class LoadThread extends Thread {
		public void run() {
			JSONParser parser = new JSONParser();
			 
	        try {
	 
	            Object obj = parser.parse(new FileReader(
	                    "kbook.json"));
	 
	            JSONObject jsonObject = (JSONObject) obj;
	            
	            JSONArray data = (JSONArray) jsonObject.get("data");
	            
	            
	            
	            DefaultTableModel model = new DefaultTableModel();
	            final JTable tbl = new JTable(model);
	            model.addColumn("Name");
	            model.addColumn("Address");
				
				tbl.getColumnModel().getColumn(1).setMinWidth(100);
				tbl.getColumnModel().getColumn(1).setMaxWidth(100);
				
				JSONObject addrInfo = (JSONObject) data.get(0);
	            for(int i = 1; i <= addrInfo.size(); i++){
	            	JSONObject curInfo = (JSONObject) addrInfo.get(Integer.toString(i));
	            	
	            	String name = (String) curInfo.get("name");
	            	String addr = (String) curInfo.get("addr");
	            	
	            			
	            	model.addRow(new Object[]{name,addr});
	            	
	            	
	            }
				JScrollPane sp =  new JScrollPane(tbl);
				sp.setPreferredSize(new Dimension(tbl.getWidth() + 2,tbl.getHeight() + 2));
				richlistPanel.add(sp, BorderLayout.CENTER);
				invalidate();
				validate();
				repaint();
				
				tbl.getModel().addTableModelListener(new TableModelListener() {
					   @Override
					   public void tableChanged(TableModelEvent e) {
						   try {
							   
							   JSONObject main = new JSONObject();
							   JSONArray data = new JSONArray();
							   JSONObject datasub = new JSONObject();
							   
							   for(int j = 0; j < tbl.getRowCount();j++){
								   
								   JSONObject obj = new JSONObject();
								   System.out.println(j);
								   obj.put("name", tbl.getValueAt(j, 0));
								   obj.put("addr", tbl.getValueAt(j, 1));
								   datasub.put(Integer.toString(j+1), obj);
							   }
							   data.add(datasub);
							   main.put("data",data);
							   System.out.println(main);
							   
							   
							   FileWriter file = new FileWriter("kbook.json");
							   file.write(main.toJSONString());
							   file.flush();
							   file.close();
						   } catch (IOException e1) {
							   System.out.println(e1);
						   }
					       //System.out.println(tbl.getValueAt(tbl.getEditingRow(), tbl.getEditingColumn()));
					   }
					});
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
		}
	}
}
