package cz.GravelCZLP.BlockGame.Client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;

import cz.GravelCZLP.BlockGame.Common.Network;

public class AuthWindow extends JFrame {

	private static final long serialVersionUID = 771822481556979845L;

	public AuthWindow(final ClientMain main) {
		final Client c = new Client();
		c.start();
		Network.injectPackets(c);
		setTitle("Pls set server and name");
		setSize(420, 360);
		setLayout(new FlowLayout());
		final JTextField name = new JTextField("My Name",20);
		JButton lan = new JButton("Lan Discovey");
		lan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 100; i < 25565; i++) {
					InetAddress address = c.discoverHost(i, 500);
						try {
							if (address == null) {
								continue;
							}
							c.connect(500, address, i);
							if (name.getText() == "" || name.getText() == null || name.getText() == "My Name") {
								JOptionPane.showMessageDialog(null, "Prosím nastav si jméno :)", "Není Zadané jmého", JOptionPane.ERROR_MESSAGE);
								return;
							}
							main.gameWindow.initGame(name.getText(), c);
							dispose();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
				}
			}
		});
		JButton connect = new JButton("Connect!");
		final JTextField ip = new JTextField("localhost", 20);
		final JTextField port = new JTextField("25565", 20);
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					c.connect(5000, ip.getText(), Integer.parseInt(port.getText()));
					if (name.getText() == "" || name.getText() == null) {
						JOptionPane.showMessageDialog(null, "Prosím nastav si jméno :)", "Není Zadané jmého", JOptionPane.ERROR_MESSAGE);
						return;
					}
					main.gameWindow.initGame(name.getText(), c);
					dispose();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		add(name);
		add(ip);
		add(port);
		add(lan);
		add(connect);
		setVisible(true);
		pack();
	}
}
