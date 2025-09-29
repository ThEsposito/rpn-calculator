import data.structures.Fila;

import java.util.Scanner;

public class Main {
    public static Fila<String> recMode() {
        Fila<String> f = new Fila(10);
        Scanner sc = new Scanner(System.in);
        System.out.print("Iniciando gravação... (REC: 0/10)");
        while(true){
            System.out.print("(REC: "+f.size()+"/10)");

            // Poderíamos tratar esse comando com a regex, tbem
            String comando = sc.nextLine().trim();

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
        // Inicializa nosso avaliador sem nenhuma variável definida
        Scanner sc = new Scanner(System.in);

        Variaveis vars = new Variaveis();
        Avaliador avaliador = new Avaliador(vars);

        Fila<String> gravacao = null;
        while(true){
            String input = sc.nextLine().toUpperCase(); // Ainda precisa do trim() ?
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
                if(gravacao == null){
                    System.out.println("Não há nenhum comando registrado em gravação!");
                    continue;
                }

                for(int i=0; i<gravacao.size(); i++){

                }
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
                    continue;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace(); // TODO: comentar ou apagar depois do debug
                }

            // Nesse caso, verificar se é uma expressão
            } else {
            // *verificar se é uma expressão*
            // Se sim valida,


            }
        }

        System.out.println("Programa encerrado!");
    }
}

/*
REFERÊNCIAS:
https://www.guj.com.br/t/como-obter-o-valor-de-um-caractere-ascii-no-java/134685

 */