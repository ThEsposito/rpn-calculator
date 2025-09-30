/*
Um algoritmo para conversão de uma expressão infixa qualquer para posfixa seria:
• Inicie com uma pilha vazia;
• Realize uma varredura na expressão infixa, copiando todos os identificadores encontrados
diretamente para a expressão de saída.
a) Ao encontrar um operador:
1. Enquanto a pilha não estiver vazia e houver no seu topo um operador com prioridade maior
ou igual ao encontrado, desempilhe o operador e copie-o na saída;
2. Empilhe o operador encontrado;
b) Ao encontrar um parêntese de abertura, empilhe-o;
c) Ao encontrar um parêntese de fechamento, remova um símbolo da pilha e copie-o na saída,
até que seja desempilhado o parêntese de abertura correspondente.
• Ao final da varredura, esvazie a pilha, movendo os símbolos desempilhados para a saída.
*/

import data.structures.Pilha;
import exceptions.OperacaoInvalidaException;
import exceptions.VariavelNaoDefinidaException;

public class Avaliador {
    private final Variaveis vars;

    public Avaliador() {
        this.vars = new Variaveis();
    }

    public Avaliador(Variaveis vars) {
        this.vars = vars;
    }

    public String infixToPosfix(String infix) throws IllegalArgumentException {
        String result = "";
        Pilha<Character> p1 = new Pilha<>(infix.length());

        for (int i = 0; i < infix.length(); i++) {
            char atual = Character.toUpperCase(infix.charAt(i));

            if (Character.isWhitespace(atual)) continue; // ignora os espaços

            if (Character.isAlphabetic(atual)) {
                result += atual; // vai direto para saída
            }
            else if (isOperador(atual)){
                while (!p1.isEmpty() && isOperador(p1.top()) &&
                        getPrioridadeDoOperador(p1.top()) >= getPrioridadeDoOperador(atual)) {
                    result += p1.pop();
                }
                p1.push(atual);
            }
            else if (atual == '('){
                p1.push(atual);
            }
            else if (atual == ')'){
                while (!p1.isEmpty() && p1.top() != '(') {
                    result += p1.pop();
                }
                if (!p1.isEmpty() && p1.top() == '(') {
                    p1.pop(); // descarta o '(' que sobrou
                } else {
                    throw new IllegalArgumentException("Parêntese desbalanceado!");
                }
            }
            else {
                throw new IllegalArgumentException("Caractere inválido: " + atual);
            }
        }

    // Desempilha o que restou
    while (!p1.isEmpty()){
        if (p1.top() == '(') throw new IllegalArgumentException("Parêntese desbalanceado!");
        result += p1.pop();
    }
    return result;
}


    public Double avaliarPosfix(String posfix) throws VariavelNaoDefinidaException, OperacaoInvalidaException {
        Pilha<Double> p = new Pilha<>(posfix.length());

        for (int i = 0; i < posfix.length(); i++) {
            Character c = posfix.charAt(i);

            // Ignorar espaços em branco (importante se a expressão vier separada por espaços)
            if (Character.isWhitespace(c)) continue;

            if (Character.isAlphabetic(c)) {
                // Empilha o valor da variável correspondente
                p.push(vars.getValor(c));

            } else if (isOperador(c)) {
                // Atenção: no pós-fixo o primeiro pop é o operando da direita!
                double right = p.pop();
                double left  = p.pop();

                double result;
                // Dá pra reduzir com lambda expressions, mas n lembro a sintaxe
                switch (c) {
                    case '+':
                        result = left + right;
                        break;
                    case '-':
                        result = left - right;
                        break;
                    case '*':
                        result = left * right;
                        break;
                    case '/':
                        if (right == 0) throw new OperacaoInvalidaException("Não é possível dividir por zero!");
                        result = left / right;
                        break;
                    case '^':
                        result = Math.pow(left, right);
                        break;
                    default:
                        throw new OperacaoInvalidaException("Não é possível realizar a operação '" + c + "'!");
                }

                p.push(result);
            } else {
                // Caso seja um caractere inesperado
                throw new OperacaoInvalidaException("Caractere inválido encontrado: '" + c + "'");
            }
        }

        // No final, deve restar apenas UM elemento na pilha, que é o resultado
        if (p.size() == 1) return p.pop();
        else throw new RuntimeException(
                "Erro inesperado!! Era pro último elemento da pilha ser o resultado! \nA operação pode ser inválida!: " + posfix
        );
    }


    public static boolean isBalanceado(String expressao) {
        Pilha<Character> p = new Pilha<>(expressao.length());

        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            if (c == '(') p.push(c);
            else if (c == ')') {
                if (p.isEmpty()) return false;
                p.pop();
            }
            // outros caracteres são ignorados
        }
        return p.isEmpty();
    }


    private boolean isOperador(char c){
        char[] operadores = {'+','-', '*','/', '^'};

        for(char operador : operadores) {
            if(c == operador) return true;
        }
        return false;
    }

    private int getPrioridadeDoOperador(char operador) throws IllegalArgumentException {
        if(!isOperador(operador)) throw new IllegalArgumentException("O parâmetro deve ser um operador!");
        switch(operador){
            case '+', '-':
                return 1;

            case '/', '*':
                return 2;

            case '^':
                return 3;
        }
        throw new IllegalArgumentException("Operador inválido!");
    }
}
