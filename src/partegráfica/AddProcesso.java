package partegráfica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import escalonadorv2.Processo;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButton;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.List;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddProcesso extends JFrame {

	private JPanel contentPane;
	private JTextField textTempo;
	private boolean ioBounds = false;
	private String processo;
	private int tempo;
	private char lblProcesso = 'A';
	private ArrayList<Processo> processos =  new ArrayList<>();
	private int prioridade = 0;
	private JTextField textQtun;
	private JTextField textTempTC;
	private JTextField textX;
	private JTextField textY;
	private int quantun, tc, x = 1 ,y = 1;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProcesso frame = new AddProcesso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddProcesso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ButtonGroup grupo = new ButtonGroup();
		
		JRadioButton rdbtnCpuBounds = new JRadioButton("CPU Bounds");
		rdbtnCpuBounds.setSelected(true);
		rdbtnCpuBounds.setBounds(6, 7, 109, 23);
		contentPane.add(rdbtnCpuBounds);
		
		JRadioButton rdbtnIoBounds = new JRadioButton("I/O Bounds");
		rdbtnIoBounds.setBounds(6, 33, 109, 23);
		contentPane.add(rdbtnIoBounds);
		grupo.add(rdbtnIoBounds);
		grupo.add(rdbtnCpuBounds);
		
		
		
		
		
		
		
		Panel panel = new Panel();
		panel.setBounds(6, 119, 418, 189);
		contentPane.add(panel);
		panel.setLayout(null);
		
		List list = new List();
		list.setBounds(0, 0, 418, 189);

		panel.add(list);
		
		textTempo = new JTextField();
		textTempo.setBounds(236, 8, 86, 20);
		contentPane.add(textTempo);
		textTempo.setColumns(10);
		
		JLabel lblTempo = new JLabel("Tempo:");
		lblTempo.setBounds(137, 11, 89, 14);
		contentPane.add(lblTempo);
		
		JLabel lblQuantum = new JLabel("Quantum:");
		lblQuantum.setBounds(6, 63, 99, 14);
		contentPane.add(lblQuantum);
		
		textQtun = new JTextField();
		textQtun.setBounds(75, 63, 28, 20);
		contentPane.add(textQtun);
		textQtun.setColumns(10);
		
		textTempTC = new JTextField();
		textTempTC.setBounds(75, 88, 28, 20);
		contentPane.add(textTempTC);
		textTempTC.setColumns(10);
		
		JLabel lblTempoTc = new JLabel("Tempo TC:");
		lblTempoTc.setBounds(6, 91, 99, 14);
		contentPane.add(lblTempoTc);
		
		JLabel lblTempoDeUma = new JLabel("Tempo de uma opera\u00E7\u00E3o ES:");
		lblTempoDeUma.setBounds(115, 63, 243, 14);
		contentPane.add(lblTempoDeUma);
		
		JLabel lblTempoDeProcessamento = new JLabel("Tempo de processamento ES:");
		lblTempoDeProcessamento.setBounds(113, 91, 245, 14);
		contentPane.add(lblTempoDeProcessamento);
		
		textX = new JTextField();
		textX.setBounds(325, 60, 45, 20);
		contentPane.add(textX);
		textX.setColumns(10);
		
		textY = new JTextField();
		textY.setBounds(325, 88, 45, 20);
		contentPane.add(textY);
		textY.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCpuBounds.isSelected()) {
					ioBounds = false;
				}else {
					ioBounds = true;
				}
				
				tempo = Integer.valueOf(textTempo.getText());
				processo = "Processo " + lblProcesso;
				
				
				
				list.add(processo);
				
				System.out.println("tempo de exec: " + tempo + " iobounds: " + ioBounds);
				
				processos.add(new Processo(ioBounds, tempo, prioridade, String.valueOf(processo)));
				
				lblProcesso++;
			}
		});
		btnAdd.setBounds(137, 33, 89, 23);
		contentPane.add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!processos.isEmpty()) {
					list.remove(processos.size() - 1);
					processos.remove(processos.size() - 1);
					lblProcesso--;
				}
			}
		});
		btnRemove.setBounds(236, 33, 89, 23);
		contentPane.add(btnRemove);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				quantun = Integer.valueOf(textQtun.getText());
				tc = Integer.valueOf(textTempTC.getText());
				x = Integer.valueOf(textX.getText());
				y = Integer.valueOf(textY.getText());
				
				Filas filas = new Filas(processos,x,y,quantun,tc);
				filas.setVisible(true);
				dispose();
			
				
			}
		});
		btnRun.setBounds(335, 314, 89, 23);
		contentPane.add(btnRun);
		
		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				processos.add(new Processo(false,10,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(true,5,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(false,20,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(true,3,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(false,30,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(false,50,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(false,40,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				processos.add(new Processo(false,100,0, "Processo " + lblProcesso));
				list.add( "Processo " + lblProcesso);
				lblProcesso++;
				
				
	


				
			}
		});
		btnRandom.setBounds(335, 33, 89, 23);
		contentPane.add(btnRandom);
		
		
	}
}
