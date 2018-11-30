package escalonadorv2;

import java.util.ArrayList;

public class Escalonador {
	private int quantum,tc,x,y;
	private Processo executando;
	private ArrayList<Processo> pronto,bloqueado;
	private Metodos metodo;
	private int nciclos=0;
	private int ntrocacontexto=0;
	private int ciclostrocacontexto=0;
	private int cicloocioso=0;



	private boolean end=false;

	private ArrayList<Step> passos=new ArrayList<>();


	public int getNciclos() {
		return nciclos;
	}



	public double getUsoDeCPU(){
		return (nciclos-ciclostrocacontexto-cicloocioso)*100/nciclos;
	}


	public void setNciclos(int nciclos) {
		this.nciclos = nciclos;
	}

	public int getNtrocacontexto() {
		return ntrocacontexto;
	}

	public void setNtrocacontexto(int ntrocacontexto) {
		this.ntrocacontexto = ntrocacontexto;
	}

	public int getCiclostrocacontexto() {
		return ciclostrocacontexto;
	}

	public void setCiclostrocacontexto(int ciclostrocacontexto) {
		this.ciclostrocacontexto = ciclostrocacontexto;
	}

	public Escalonador(int quantum, int tc, int x, int y) {
		super();
		this.quantum = quantum;
		this.tc = tc;
		this.x = x;
		this.y = y;
		pronto = new ArrayList<>();
		bloqueado = new ArrayList<>();
	}

	public ArrayList<Processo> getPronto() {
		return pronto;
	}


	public void relatarTroca() {
		ntrocacontexto++;
		ciclostrocacontexto+=tc;
	}


	public void setPronto(ArrayList<Processo> pronto) {
		this.pronto = pronto;
	}


	public ArrayList<Processo> getFinalizados() {
		return metodo.getProntos();
	}

	public ArrayList<Processo> getBloqueado() {
		return metodo.getBloqueados();
	}

	public Object getFilas(){
		return metodo.getFilas();
	}




	public int getQuantum() {
		return quantum;
	}

	public boolean isEnd() {
		return end;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public int getTc() {
		return tc;
	}

	public void setTc(int tc) {
		this.tc = tc;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setBloqueado(ArrayList<Processo> bloqueado) {
		this.bloqueado = bloqueado;
	}


	public void setMethod() {
		metodo=new MultiplasFilas(this,8);
	}
	

	public String relatorio() {
		System.out.println(nciclos);

		System.out.println(ciclostrocacontexto);

		System.out.println(ntrocacontexto);
		return "Uso de CPU:"+(((double)(nciclos-ciclostrocacontexto)/nciclos)*100)+"%"+"   Trocas de contexto:"+(ntrocacontexto+1)+"    tempo de execucao:"+ (nciclos-1);
	}
	
	
	
	public Step executar() {
		Step st=metodo.execucao();
		if(st.getState().equals(Metodos.Estado.trocaDeContexto)){
			ntrocacontexto++;
			ciclostrocacontexto+=st.getDuracao();
		}
		if(st.getState().equals(Metodos.Estado.oscioso)) cicloocioso+=st.getDuracao();

		nciclos+=st.getDuracao();
		if(st.getPx()!=null)System.out.println(st.getState().name()+" - "+st.getDuracao()+" - "+ st.getPx().toString());
		else System.out.println(st.getState().name()+" - "+st.getDuracao());
		if(st.getState().equals(Metodos.Estado.finalizado))end=true;
		return st;
	}
	public void notifyEnd() {
		this.end=true;
		
	}
	public void notificarOcioso(int quanto) {
		nciclos-=quanto;
	}
}
