package io.github.uncertifiedrobot.KristWallet;

import io.github.uncertifiedrobot.KristWallet.gui.LoginFrame;
import io.github.uncertifiedrobot.KristWallet.gui.WalletFrame;
import io.github.uncertifiedrobot.KristWallet.gui.WalletFrame.Views;
import io.github.uncertifiedrobot.KristWallet.gui.views.HistoryPanel;
import io.github.uncertifiedrobot.KristWallet.util.HTTP;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
/*
 * Author: UncertifiedRobot
 * Original Author: apemanzilla
 * 
 * License file is supplied with every copy.
 */
@SuppressWarnings("unused")
public class KWallet {
	
	public static LoginFrame loginWindow;
	public static WalletFrame walletWindow;
	public static KristAPI api;

	public static void main(String[] args) {
		System.out.println("Starting KWallet");
		
		// Set Nimbus L&F, if available
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, leave default L&F
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginWindow = new LoginFrame();
					loginWindow.pack();
					loginWindow.setLocationRelativeTo(null);
					loginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void login(String privateKey) {
		String apiLink = null;
		try {
			apiLink = HTTP.readURL(new URL("https://raw.githubusercontent.com/BTCTaras/kristwallet/master/staticapi/syncNode"));
			System.out.println("API link is " + apiLink);
		} catch (MalformedURLException e) {
			System.out.println("Bad URL");
			JOptionPane.showMessageDialog(null, "Bad URL","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Could not find node");
			JOptionPane.showMessageDialog(null, "Could not find node","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(2);
		}
		try {
			api = new KristAPI(new URL(apiLink), privateKey);
			System.out.println("Address (v2) is " + api.getAddress());
			loginWindow.dispose();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						walletWindow = new WalletFrame();
						walletWindow.pack();
						walletWindow.setLocationRelativeTo(null);
						walletWindow.setTitle("KWallet - " + api.getAddress());
						walletWindow.setVisible(true);
						walletWindow.setView(Views.OVERVIEW);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			System.out.println("Could not connect to node");
			JOptionPane.showMessageDialog(null, "Could not connect to node","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(3);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void logout(){
		walletWindow.dispose();
		try {
			loginWindow = new LoginFrame();
			loginWindow.pack();
			loginWindow.setLocationRelativeTo(null);
			loginWindow.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
