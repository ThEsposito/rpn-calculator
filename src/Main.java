/*
Alunos:
Theo Espósito Simões Resende  RA: 10721356
Kauê Lima Rodrigues Meneses RA: 10410594
*/
import data.structures.Fila;
import java.util.Scanner;

public class Main {
    public static Fila<String> recMode() {
        Fila<String> f = new Fila(10);
        Scanner sc = new Scanner(System.in);
        System.out.println("Iniciando gravação...");
        while(true){
            System.out.println("(REC: "+f.size()+"/10)");

            // Poderíamos tratar esse comando com a regex, tbem
            String comando = sc.nextLine().trim().toUpperCase();

            if(comando.equals("STOP")) break;

            if(comando.equals("REC") || comando.equals("PLAY") || comando.equals("ERASE") || comando.equals("EXIT")){
                System.out.println("Este comando é inválido em modo de gravação!");
                continue;
            }

            // Se puder usar a operação, guardar na fila (não precisa executar)
            f.enqueue(comando);
            if(f.isFull()){
                System.out.println("Fila de comandos cheia! Encerrando...");
                break;
            }
        }
        return f;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Inicializa nosso avaliador sem nenhuma variável definida

        // O avaliador vai usar o mesmo objeto para verificar as variáveis
        Variaveis vars = new Variaveis();
        Avaliador avaliador = new Avaliador(vars);

        Fila<String> gravacao = null;
        while(true){
            System.out.print("Digite sua instrução: ");
            String input = sc.nextLine().toUpperCase();
            input = input.replaceAll("\\s", ""); //Remove todos os espaços em branco

            if(input.equals("EXIT")) break;

            if(input.equals("VARS")){
                // Esse método já trata casos com nenhuma variavel definida
                System.out.println(vars.listar());

            } else if(input.equals("RESET")){
                vars.reset();
                System.out.println("Variáveis reiniciadas");

            } else if(input.equals("REC")){
                gravacao = recMode();

            } else if(input.equals("STOP")){
                System.out.println("Este comando só é válido em modo de gravação!");

            } else if(input.equals("PLAY")){
                if(gravacao == null || gravacao.size()==0){
                    System.out.println("Não há gravação para ser reproduzida.");
                    continue; // trocar return por continue para não encerrar o programa
                }
                int n = gravacao.size();
                for(int i=0; i<n; i++){
                    String operacaoRec = gravacao.dequeue();

                    // Executar a operação gravada
                    if(operacaoRec.contains("=")){ // Atribuição
                        String[] atribuicao = operacaoRec.split("=");
                        char variavel = atribuicao[0].charAt(0);
                        double valor;
                        try {
                            valor = Double.parseDouble(atribuicao[1]);
                            vars.atribuir(variavel, valor);
                            System.out.println("Variável " + variavel + " = " + valor);
                        } catch (Exception e) {
                            System.out.println("Erro ao executar operação gravada: " + operacaoRec);
                            System.out.println(e.getMessage());
                        }
                    } else { // Expressão
                        try {
                            String posfixa = avaliador.infixToPosfix(operacaoRec);
                            System.out.println("Operação posfixa: "+posfixa);
                            System.out.println("Resultado: " + avaliador.avaliarPosfix(posfixa));
                        } catch (Exception e){
                            System.out.println("Erro ao executar operação gravada: " + operacaoRec);
                            System.out.println(e.getMessage());
                        }
                    }

                    // Enfileira novamente para manter a gravação
                    gravacao.enqueue(operacaoRec);
                }
            }
            else if (input.equals("ERASE")){
                gravacao = null; // Remove da fila de comandos gravados
            } else if(input.contains("=")){ // Indica que é uma operação de atribuição
                String[] atribuicao = input.split("=");

                // Se houver mais de 1 '=' ou a variável possuir mais de um caractere:
                if(atribuicao.length > 2 || atribuicao[0].length() > 1){
                    System.out.println("Operação inválida!");
                    continue;
                }

                char variavel = atribuicao[0].charAt(0);
                if(! Character.isAlphabetic(variavel)){
                    System.out.println("Só são aceitas variáveis de A a Z");
                    continue;
                }

                double valor;
                try {
                    valor = Double.parseDouble(atribuicao[1]);
                    vars.atribuir(variavel, valor);
                } catch (NumberFormatException e) {
                    System.out.println("Número em formato inválido!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

                // Nesse caso, verificar se é uma expressão
            } else {
                try {
                    String posfixa = avaliador.infixToPosfix(input);
                    System.out.println("Operação posfixa: "+posfixa);
                    System.out.println("Resultado: " + avaliador.avaliarPosfix(posfixa));
                } catch (Exception e){
                    System.out.println("Operação inválida!");
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Programa encerrado!");
    }
}

/*
REFERÊNCIAS:
https://www.guj.com.br/t/como-obter-o-valor-de-um-caractere-ascii-no-java/134685
*/