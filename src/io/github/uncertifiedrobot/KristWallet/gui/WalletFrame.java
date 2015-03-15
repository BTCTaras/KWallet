package io.github.uncertifiedrobot.KristWallet.gui;

import io.github.uncertifiedrobot.KristWallet.KWallet;
import io.github.uncertifiedrobot.KristWallet.gui.views.BookPanel;
import io.github.uncertifiedrobot.KristWallet.gui.views.EconomiconPanel;
import io.github.uncertifiedrobot.KristWallet.gui.views.HistoryPanel;
import io.github.uncertifiedrobot.KristWallet.gui.views.OverviewPanel;
import io.github.uncertifiedrobot.KristWallet.gui.views.TransferPanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

public class WalletFrame extends JFrame {

	private static final long serialVersionUID = -4298637799286781394L;
	private WalletFrame self;
	private JPanel contentPane;
	private JPanel viewContainer;
	private JPanel currentView;
	private JButton btnOverview;
	private JButton btnTransactions;
	private JButton btnSendKrist;
	private JButton btnEconomicon;
	private JButton btnLogout;
	private JButton btnBook;
	/**
	 * Create the frame.
	 */
	public WalletFrame() {
		
		
		File f = new File("kbook.json");
		if(!f.exists() && !f.isDirectory()) { 
			try {
				f.createNewFile();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null,"Could not create empty address book. This feature will not work correctly.","An error has occured!",JOptionPane.ERROR_MESSAGE);
			}
		}
		setMinimumSize(new Dimension(600, 325));
		setTitle("KristWallet");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		splitPane.setLeftComponent(buttonPanel);
		buttonPanel.setLayout(new GridLayout(7, 1, 0, 1));
		
		btnOverview = new JButton("Overview");
		btnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setView(Views.OVERVIEW);
			}
		});
		btnOverview.setFocusPainted(false);
		btnOverview.setMaximumSize(new Dimension(0, 25));
		btnOverview.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnOverview);
		
		btnTransactions = new JButton("Transactions");
		btnTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				self.setView(Views.HISTORY);
			}
		});
		btnTransactions.setFocusPainted(false);
		btnTransactions.setMaximumSize(new Dimension(0, 25));
		btnTransactions.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnTransactions);
		
		btnSendKrist = new JButton("Transfer");
		btnSendKrist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setView(Views.TRANSFER);
			}
		});
		btnSendKrist.setFocusPainted(false);
		btnSendKrist.setMaximumSize(new Dimension(0, 25));
		btnSendKrist.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnSendKrist);
		
		btnBook = new JButton("Address Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setView(Views.BOOK);
			}
		});
		btnBook.setFocusPainted(false);
		btnBook.setMaximumSize(new Dimension(0, 25));
		btnBook.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnBook);
		
		
		btnEconomicon = new JButton("Economicon");
		btnEconomicon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setView(Views.ECONOMICON);
			}
		});
		btnEconomicon.setFocusPainted(false);
		btnEconomicon.setMaximumSize(new Dimension(0, 25));
		btnEconomicon.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnEconomicon);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KWallet.logout();
			}
		});
		btnLogout.setFocusPainted(false);
		btnLogout.setMaximumSize(new Dimension(0, 25));
		btnLogout.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnLogout);
		
		
		Component verticalGlue = Box.createVerticalGlue();
		buttonPanel.add(verticalGlue);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFocusPainted(false);
		btnExit.setMaximumSize(new Dimension(0, 25));
		btnExit.setPreferredSize(new Dimension(0, 25));
		buttonPanel.add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		splitPane.setRightComponent(scrollPane);
		
		viewContainer = new JPanel();
		scrollPane.setViewportView(viewContainer);
		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.X_AXIS));
		self = this;
	}
	
	public enum Views {
		OVERVIEW,
		HISTORY,
		TRANSFER,
		ECONOMICON,
		BOOK
	}
	
	public void setView(Views view) {
		switch(view) {
		 case OVERVIEW: {
			try {
				viewContainer.removeAll();
				currentView = new OverviewPanel(KWallet.api.getAddress());
				viewContainer.add(currentView);
				
				btnOverview.setEnabled(false);
				btnTransactions.setEnabled(true);
				btnSendKrist.setEnabled(true);
				btnEconomicon.setEnabled(true);
				btnBook.setEnabled(true);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			invalidate();
			validate();
			repaint();
			break;
		 }
		 case HISTORY: {
			 	try {
			 		viewContainer.removeAll();
					currentView = new HistoryPanel(KWallet.api.getAddress());
				 	viewContainer.add(currentView);
					 
					btnOverview.setEnabled(true);
					btnTransactions.setEnabled(false);
					btnSendKrist.setEnabled(true);
					btnEconomicon.setEnabled(true);
					btnBook.setEnabled(true);
				} catch (MalformedURLException e) {
					JOptionPane.showMessageDialog(null, "Could not get transactions","Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Could not get transactions","Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			 	invalidate();
			 	validate();
			 	repaint();
				break;
		 }
		 case TRANSFER: {
				viewContainer.removeAll();
				currentView = new TransferPanel();
				viewContainer.add(currentView);
				
				btnOverview.setEnabled(true);
				btnTransactions.setEnabled(true);
				btnSendKrist.setEnabled(false);
				btnEconomicon.setEnabled(true);
				btnBook.setEnabled(true);
				invalidate();
				validate();
				repaint();
				break;
		 }
		 case ECONOMICON: {
			 	viewContainer.removeAll();
			 	currentView = new EconomiconPanel();
			 	viewContainer.add(currentView);
			 	
				btnOverview.setEnabled(true);
				btnTransactions.setEnabled(true);
				btnSendKrist.setEnabled(true);
				btnEconomicon.setEnabled(false);
				btnBook.setEnabled(true);
				invalidate();
				validate();
				repaint();
				break;
		 }
		 case BOOK: {
			 	viewContainer.removeAll();
			 	currentView = new BookPanel();
			 	viewContainer.add(currentView);
			 	
				btnOverview.setEnabled(true);
				btnTransactions.setEnabled(true);
				btnSendKrist.setEnabled(true);
				btnEconomicon.setEnabled(true);
				btnBook.setEnabled(false);
				
				invalidate();
				validate();
				repaint();
				break;
		 }
		 
		}
		
	}

}
