public class Main {
    public static void main(String[] args) {
        SistemaVotacao sistemaVotacao = new SistemaVotacao();

        Pessoa pessoa = new Pessoa(sistemaVotacao, 0);
        Pessoa pessoa2 = new Pessoa(sistemaVotacao, 1);
        Pessoa pessoa3 = new Pessoa(sistemaVotacao, 0);
        Pessoa pessoa4 = new Pessoa(sistemaVotacao, 1);
        Pessoa pessoa5 = new Pessoa(sistemaVotacao, 0);
        Pessoa pessoa6 = new Pessoa(sistemaVotacao, 0);

        pessoa.setName("Thread 1");
        pessoa2.setName("Thread 2");
        pessoa3.setName("Thread 3");
        pessoa4.setName("Thread 4");
        pessoa5.setName("Thread 5");
        pessoa6.setName("Thread 6");

        pessoa.start();
        pessoa2.start();
        pessoa3.start();
        pessoa4.start();
        pessoa5.start();
        pessoa6.start();
    }
}

class Pessoa extends Thread {
    SistemaVotacao sistemaVotacao = null;
    int candidato;

    @Override
    public void run() {
        this.votar();
    }

    public Pessoa(SistemaVotacao sistemaVotacao, int candidato) {
        this.sistemaVotacao = sistemaVotacao;
        this.candidato = candidato;
    }

    public void votar() {
        sistemaVotacao.votar(candidato);
    }
}

class SistemaVotacao implements Runnable {
    private int votoCandidato1 = 0;
    private int votoCandidato2 = 0;

    private int totalVotos = 0;

    private final Object lockC1 = new Object();
    private final Object lockC2 = new Object();

    @Override
    public void run() {}

    public void votar(int candidato) {
        if(candidato == 0) {
            synchronized (lockC1) {
                this.setVotoCandidato1(this.getVotoCandidato1() + 1);
                System.out.println("Voto candidato 1: " + this.getVotoCandidato1() + " " + Thread.currentThread().getName());
            }
        } else if(candidato == 1) {
            synchronized (lockC2) {
                this.setVotoCandidato2(this.getVotoCandidato2() + 1);
                System.out.println("Voto candidato 2: " + this.getVotoCandidato2() + " " + Thread.currentThread().getName());
            }
        }

        synchronized (this) {
            this.setTotalVotos(this.getTotalVotos() + 1);
            System.out.println("Total votos: " + this.getTotalVotos() + " " + Thread.currentThread().getName());
        }
    }

    public int getVotoCandidato1() {
        return votoCandidato1;
    }

    public void setVotoCandidato1(int votoCandidato1) {
        this.votoCandidato1 = votoCandidato1;
    }

    public int getVotoCandidato2() {
        return votoCandidato2;
    }

    public void setVotoCandidato2(int votoCandidato2) {
        this.votoCandidato2 = votoCandidato2;
    }

    public int getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(int totalVotos) {
        this.totalVotos = totalVotos;
    }
}