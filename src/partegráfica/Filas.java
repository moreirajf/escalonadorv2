package partegráfica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import escalonadorv2.Escalonador;
import escalonadorv2.Metodos.Estado;
import escalonadorv2.Processo;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Filas extends JFrame {

	private JPanel contentPane;
	private ArrayList<Processo> processos;
	private int x,y,quantum, tc;
	private Escalonador escalonador;
	private List listFinal = new List();
	private List listBloq = new List();
	private List listProntos = new List();
	private List list_7 = new List();
	private List list_6 = new List();
	private List list_5 = new List();
	private List list_4 = new List();
	private List list_3 = new List();
	private List list_2 = new List();
	private List list_1 = new List();
	private List list_0 = new List();
	private ArrayList<Processo>[] niveis;
	private ArrayList<Processo> finalizados;
	private ArrayList<Processo> bloqueados;
	/**
	 * Launch the application.
	 */
	
	

	/**
	 * Create the frame.
	 */
	public Filas(ArrayList<Processo> processos, int x, int y, int quantum, int tc) {
		
		this.x = x;
		this.y = y;
		this.quantum = quantum;
		this.tc = tc;
		this.processos = processos;
		escalonador = new Escalonador(this.quantum, this.tc, this.x, this.y);
		escalonador.setPronto(processos);
		escalonador.setMethod();
		finalizados = escalonador.getFinalizados();
		bloqueados = escalonador.getBloqueado();
		niveis = (ArrayList<Processo>[])escalonador.getFilas();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 949, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list_0.setBounds(137, 44, 121, 152);
		contentPane.add(list_0);
		
		list_1.setBounds(264, 44, 121, 152);
		contentPane.add(list_1);
		
		list_2.setBounds(391, 44, 121, 152);
		contentPane.add(list_2);
		
		list_3.setBounds(518, 44, 121, 152);
		contentPane.add(list_3);
		
		list_4.setBounds(57, 222, 121, 152);
		contentPane.add(list_4);
		
		list_5.setBounds(184, 222, 121, 152);
		contentPane.add(list_5);
		
		list_6.setBounds(311, 222, 121, 152);
		contentPane.add(list_6);
		
		list_7.setBounds(438, 222, 121, 152);
		contentPane.add(list_7);
		
		listProntos.setBounds(10, 44, 121, 152);
		contentPane.add(listProntos);
		
		listBloq.setBounds(645, 44, 121, 152);
		contentPane.add(listBloq);
		
		
		listFinal.setBounds(645, 222, 121, 152);
		contentPane.add(listFinal);
		
		
		
		
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddProcesso add = new AddProcesso();
				add.setVisible(true);
				dispose();
				
			}
		});
		btnVoltar.setBounds(834, 78, 89, 23);
		contentPane.add(btnVoltar);
		
		
		
		JLabel lblProcessos = new JLabel("Pronto");
		lblProcessos.setBounds(10, 24, 121, 14);
		contentPane.add(lblProcessos);
		
		JLabel lblFila = new JLabel("Fila 0");
		
		lblFila.setBounds(137, 24, 121, 14);
		contentPane.add(lblFila);
		
		JLabel lblFila_1 = new JLabel("Fila 1");
		lblFila_1.setBounds(264, 24, 121, 14);
		contentPane.add(lblFila_1);
		
		JLabel lblFila_2 = new JLabel("Fila 2");
		lblFila_2.setBounds(391, 24, 121, 14);
		contentPane.add(lblFila_2);
		
		JLabel lblFila_3 = new JLabel("Fila 3");
		lblFila_3.setBounds(522, 24, 117, 14);
		contentPane.add(lblFila_3);
		
		JLabel lblFila_4 = new JLabel("Fila 4");
		lblFila_4.setBounds(57, 202, 121, 14);
		contentPane.add(lblFila_4);
		
		JLabel lblBloqueados = new JLabel("Bloqueados");
		lblBloqueados.setBounds(645, 24, 121, 14);
		contentPane.add(lblBloqueados);
		
		JLabel lblFila_5 = new JLabel("Fila 5");
		lblFila_5.setBounds(184, 202, 121, 14);
		contentPane.add(lblFila_5);
		
		JLabel lblFila_6 = new JLabel("Fila 6");
		lblFila_6.setBounds(315, 202, 117, 14);
		contentPane.add(lblFila_6);
		
		JLabel lblFila_7 = new JLabel("Fila 7");
		lblFila_7.setBounds(438, 202, 121, 14);
		contentPane.add(lblFila_7);
		
		JLabel lblTempoTotal = new JLabel("Tempo Total: ");
		lblTempoTotal.setBounds(771, 332, 116, 14);
		contentPane.add(lblTempoTotal);
		
		JLabel lblTotal = new JLabel("0");
		lblTotal.setBounds(877, 332, 46, 14);
		contentPane.add(lblTotal);
		
		JLabel lblTrocas = new JLabel("Trocas:");
		lblTrocas.setBounds(771, 357, 116, 14);
		contentPane.add(lblTrocas);
		
		JLabel lblNtrocas = new JLabel("0");
		lblNtrocas.setBounds(877, 357, 46, 14);
		contentPane.add(lblNtrocas);
		
		JLabel lblUsoDeCpu = new JLabel("Uso de cpu");
		lblUsoDeCpu.setBounds(771, 382, 116, 14);
		contentPane.add(lblUsoDeCpu);
		
		JLabel lblUso = new JLabel("0");
		lblUso.setBounds(877, 382, 46, 14);
		contentPane.add(lblUso);
		
		
		
		Label lvlFinalizados = new Label("Finalizados");
		lvlFinalizados.setBounds(645, 194, 62, 22);
		contentPane.add(lvlFinalizados);
		
		JLabel lblExecutando = new JLabel("Executando:");
		lblExecutando.setBounds(10, 405, 107, 14);
		contentPane.add(lblExecutando);
		
		JLabel lblExec = new JLabel("0");
		lblExec.setBounds(132, 405, 511, 14);
		contentPane.add(lblExec);
		
		for(Processo p : processos) {
			listProntos.add(p.getLabel());
		}
		
		JButton btnPrximo = new JButton("Pr\u00F3ximo");
		btnPrximo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!escalonador.isEnd()) {
					atualiza();
					lblExec.setText(escalonador.executar().toString());
					lblTotal.setText(String.valueOf(escalonador.getNciclos()));
					lblNtrocas.setText(String.valueOf(escalonador.getNtrocacontexto()));
					lblUso.setText(String.format("%.2f", escalonador.getUsoDeCPU()) + "%");
				}else {
					btnPrximo.setEnabled(false);
				}
				
				
				
				
			}
		});
		btnPrximo.setBounds(834, 10, 89, 23);
		contentPane.add(btnPrximo);
		
		JButton btnAutomatico = new JButton("Autom\u00E1tico");
		btnAutomatico.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					while(!escalonador.isEnd()) {
						escalonador.executar();
					}
					lblExec.setText(Estado.finalizado.toString().toUpperCase());
					lblTotal.setText(String.valueOf(escalonador.getNciclos()));
					lblNtrocas.setText(String.valueOf(escalonador.getNtrocacontexto()));
					lblUso.setText(String.format("%.2f", escalonador.getUsoDeCPU()) + "%");
					atualiza();
								
			}
		});
		btnAutomatico.setBounds(834, 44, 89, 23);
		contentPane.add(btnAutomatico);
		
		
	}


	public void atualiza() {
		 list_0.removeAll();
		 list_1.removeAll();
		 list_2.removeAll();
		 list_3.removeAll();
		 list_4.removeAll();
		 list_5.removeAll();
		 list_6.removeAll();
		 list_7.removeAll();
		 listBloq.removeAll();
		 listFinal.removeAll();
		for(Processo p: niveis[0]){
		   list_0.add(p.getLabel() + " - " + p.getTempoRestante());
		}

		for(Processo p: niveis[1]){
		   list_1.add(p.getLabel() + " - " + p.getTempoRestante());
		}

		for(Processo p: niveis[2]){
		   list_2.add(p.getLabel() + " - " + p.getTempoRestante());
		}
		for(Processo p: niveis[3]){
		   list_3.add(p.getLabel() + " - " + p.getTempoRestante());
		}
		for(Processo p: niveis[4]){
		   list_4.add(p.getLabel() + " - " + p.getTempoRestante());
		}
		for(Processo p: niveis[5]){
		   list_5.add(p.getLabel() + " - " + p.getTempoRestante());
		}
		for(Processo p: niveis[6]){
			list_6.add(p.getLabel() + " - " + p.getTempoRestante());
		}
		for(Processo p: niveis[7]){
		   list_7.add(p.getLabel() + " - " + p.getTempoRestante());
		}
		for(Processo p: finalizados) {
			listFinal.add(p.getLabel());
		}
		bloqueados = escalonador.getBloqueado();
		for(Processo p: bloqueados) {
			listBloq.add(p.getLabel() + " - " + p.getTempoRestante() + " - " + p.getTempoBloqueio());
		}
		
		
	}

	public ArrayList<Processo> getProcessos() {
		return processos;
	}



	public void setProcessos(ArrayList<Processo> processos) {
		this.processos = processos;
	}
	
	
}
