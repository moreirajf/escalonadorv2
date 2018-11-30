package escalonadorv2;

import java.util.ArrayList;
import java.util.Scanner;

public class MultiplasFilas implements Metodos{
	private Escalonador escalonador;
	private ArrayList<Processo>[] nivel;
	private ArrayList<Processo> bloqueio;
	private ArrayList<Processo> finalizados;
	private Processo executando;
	private int tempoEx = 0, tempoOperacao = 0;
	private boolean tc, ocioso,exec;
	private int nivelMaximo=0;

	private Estado estadoAnterior = Estado.nada;
	private Estado operacao;

	private Processo proximo;

	private int tempoAnterior;


	
	
	
	public MultiplasFilas(Escalonador escalonador, int nivelMaximo) {
		super();
		this.escalonador = escalonador;
		this.nivelMaximo=nivelMaximo;
		init();
	}


	public Processo executar() {

		
		if (exec) {
			executando.decremTempo();			
			if(executando.isFinished() && tempoEx<tempoOperacao) {
				this.ocioso = true;
				this.exec = false;
				finalizados.add(executando);
				System.out.println(executando);
				escalonador.notificarOcioso(tempoOperacao-tempoEx);
				executando = null;
			}else if(tempoEx == tempoOperacao) {
				if(executando.isIObound() && !executando.isFinished()) {
					bloqueio.add(executando);
					executando.setTempoBloqueio(escalonador.getX());
					executando=null;
				}else if (executando.isFinished()) {
					System.out.println(executando);
					finalizados.add(executando);
					executando = null;
				}
				if(executando!=null) {
					System.out.println(executando);
					if(executando.getPrioridade()==nivelMaximo-1)executando.resetNivel();
					else executando.incremNivel();
					putBack(executando);
				}
				this.exec = false;
				this.tc = true;
				tempoEx = 0;
				tempoOperacao = escalonador.getTc();
				escalonador.relatarTroca();
			}
		}else if(ocioso) {
			System.out.println("ocioso");
			if (tempoEx == tempoOperacao) {
				this.tc = true;
				this.ocioso = false;
				tempoEx = 0;
				tempoOperacao = escalonador.getTc();
				escalonador.relatarTroca();
			}
		}else if(tc) {
			System.out.println("tc");
			if (tempoEx == tempoOperacao) {
				this.tc = false;
				this.exec = true;
				executando = getNextProcess();
				if(executando==null&&bloqueio.size()==0) {
					escalonador.notifyEnd();
					this.printList();	
				}
				else if(executando==null&&bloqueio.size()>0) {
					tempoEx=0;
					tempoOperacao=timeUntilDesbloqueio();
					System.out.println("Tempo pré desbloqueio: "+tempoOperacao);
					if(tempoOperacao==0) {
						removeBloqueio();
						//escalonador.notificarOcioso(1);
						executando=getNextProcess();
						tempoOperacao=escalonador.getY();
						tc=false;
						ocioso=false;
						this.exec = true;
						return null;
					}
					tc=false;
					ocioso=true;
					this.exec = false;
					
				}
				else {
					System.out.println(executando);
					tempoEx = 0;
					if(executando.isIObound()) {
						tempoOperacao = escalonador.getY();
					}else {
						tempoOperacao = (int)Math.pow(2, (executando.getPrioridade()))*escalonador.getQuantum();
					}
				}
			}
		}
		else {
			executando=getNextProcess();
			tempoEx = 0;
			if(executando.isIObound()) {
				tempoOperacao = escalonador.getY();
			}else {
				tempoOperacao = (int)Math.pow(2, (executando.getPrioridade()))*escalonador.getQuantum();
			}
			exec=true;
			System.out.println(executando);
		}
		tempoEx++;
		removeBloqueio();
		return null;
	}
	
	private Processo getNextProcess() {
		for(int i = 0; i <nivel.length; i++) {
			if(nivel[i].size() > 0) {
				return nivel[i].remove(0);
			}
		}
		return null;
	}
	
	
	
	private void putBack(Processo px) {
		if(px.getPrioridade()<nivelMaximo-1)px.incremNivel();
		nivel[px.getPrioridade()].add(px);
		System.out.println("putback: "+px.toString());

	}
	
	private void init() {
		exec=false;
		tc=false;
		ocioso=false;
		estadoAnterior=Estado.nada;
		bloqueio=new ArrayList<>();
		nivel = new ArrayList[this.nivelMaximo];
		finalizados = new ArrayList<>();
		for (int i = 0; i < nivel.length; i++) {
			nivel[i] = new ArrayList<>();
		}
		
		ArrayList<Processo> processos = escalonador.getPronto();
		for (int i = 0; i < processos.size(); i++) {
			nivel[processos.get(i).getPrioridade()].add(processos.get(i));
		}
	}
	
	private int timeUntilDesbloqueio() {
		if(bloqueio.isEmpty())return Integer.MAX_VALUE;
		int menor=bloqueio.get(0).getTempoBloqueio();
		for(int i=1;i<bloqueio.size();i++) {
			if(menor>bloqueio.get(i).getTempoBloqueio()) {
				menor=bloqueio.get(i).getTempoBloqueio();
			}
		}
		return Math.max(menor,0);
	}
	
	
	private void removeBloqueio() {
		ArrayList<Processo> bloqueio2=new ArrayList<>();
		for(Processo p:bloqueio) {
			if(p.getTempoBloqueio()==0) {
				if(p.getPrioridade()==nivelMaximo-1)p.resetNivel();
				else p.incremNivel();
				nivel[p.getPrioridade()].add(p);
			}
			else {
				p.setTempoBloqueio(p.getTempoBloqueio()-1);
				bloqueio2.add(p);
			}
			
		}
		bloqueio.clear();
        bloqueio.addAll(bloqueio2);
	}
	
	public void printList() {
		for(Processo p: finalizados) {
			System.out.println(p);
		}
	}

	public Step execucao(){//retorna proximo passo
		//remover do bloqueio
		//tentar carregar
		//executar
		//testar final
		removeBloqueio(tempoAnterior);
		switch (estadoAnterior){
			case nada:{//inicio da fila - proximo passo é troca de contexto
				estadoAnterior=Estado.trocaDeContexto;
				tempoAnterior=escalonador.getTc();
				return new Step(Estado.trocaDeContexto,escalonador.getTc(),null);
			  }
			case executando:{//pós execução - proximo passo é troca de contexto ou fim
				if(proximo==null){
					estadoAnterior=Estado.finalizado;
					tempoAnterior=0;
					return new Step(Estado.finalizado,0,null);
				}
				else {
					estadoAnterior=Estado.trocaDeContexto;
					tempoAnterior=escalonador.getTc();
					return new Step(Estado.trocaDeContexto,escalonador.getTc(),null);
				}
			}
			case oscioso:{// oscioso esperando processo bloqueado, proximo passo é troca de contexto
				estadoAnterior=Estado.trocaDeContexto;
				return new Step(Estado.trocaDeContexto,escalonador.getTc(),null);
			}
			case trocaDeContexto:{//troca de contexto entre processos, proximo passo é execucao ou oscioso
				if(hasProcessReady()){//se tem processo pronto pra executar
					int time1=0;
					proximo=getNextProcess(); //pega o processo e determina o tempo que o mesmo vai ser executado (até completar, até chamada IO, até fim do tempo) Escolher o que for menor.
					if(proximo.isIObound()){
						time1=escalonador.getY();//até chamada IO
					}
					else time1=(int)Math.pow(2,proximo.getPrioridade()); //tempo calculado por prioridade
					int time2=proximo.getTempoRestante();//tempo restante
					estadoAnterior=Estado.executando;
					tempoAnterior=Math.min(time1,time2);//menor entre os tempos
					proximo.decremTempo(Math.min(time1,time2));//decrementa tempo de execucao
					System.out.println("Esse é o estado que eu vou mexer: "+proximo.toString());
					if(proximo.isFinished()){//se processo acabou adiciona nos finalizados
						finalizados.add(proximo);
						System.out.println("Finalizou o processo: "+proximo.getLabel());
					}
					else if(proximo.isIObound()){//se processo não acabou e é IOBound bloqueia
						bloquear(proximo);
						System.out.println("Bloqueou o processo: "+proximo.getLabel());
					}
					else putBack(proximo);//se processo não acabou e não é IObound volta pra lista de prontos

					return new Step(Estado.executando,Math.min(time1,time2),proximo);
				}
				else{
					if(hasProcess()){
						estadoAnterior=Estado.oscioso;
						tempoAnterior=timeUntilDesbloqueio();
						return new Step(Estado.oscioso,timeUntilDesbloqueio(),null);
					}
					else {
						estadoAnterior=Estado.finalizado;
						tempoAnterior=0;
						return new Step(Estado.finalizado,0,null);
					}
				}
			}
			}

		return null;

	}

	@Override
	public ArrayList<Processo> getProntos() {
		return finalizados;
	}

	@Override
	public ArrayList<Processo> getBloqueados() {
		return bloqueio;
	}

	@Override
	public Object getFilas() {
		return nivel;
	}

	public boolean hasProcess(){
		for(int i=0;i<nivel.length;i++) {
			if(!nivel[i].isEmpty())return true;
		}
		if(!bloqueio.isEmpty())return true;
		return false;
	}

	public boolean hasProcessReady(){
		for(int i=0;i<nivel.length;i++) {
			if(!nivel[i].isEmpty())return true;
		}
		return false;
	}


	private void removeBloqueio(int num) {
		ArrayList<Processo> bloqueio2=new ArrayList<>();
		for(Processo p:bloqueio) {
			p.setTempoBloqueio(p.getTempoBloqueio()-num);
			if(p.getTempoBloqueio()<=0) {
				System.out.println("desbloqueou: "+p.getLabel());
				putBack(p);
			}
			else {
				bloqueio2.add(p);
			}

		}
		bloqueio=bloqueio2;
	}

	private void bloquear(Processo processo){
		processo.setTempoBloqueio(escalonador.getX());
		bloqueio.add(processo);
	}

}
