import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        final int TAMANHO = 50;
        String[] nomes = new String[TAMANHO];
        String[] Turmas = new String[TAMANHO];
        int[] numeros = new int[TAMANHO];
        

        int nElems = 0, op;
        do {
            op = menu();
            switch (op) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Fim...");
                    break;
                case 1:
                    if (nElems < nomes.length /*TAMANHO*/) {
                        inserir(nomes, vencimentos, nElems);
                        nElems++;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Atingida a capacidade máxima do vetor");
                    }
                    break;
                case 2:
                    if (nElems > 0) {
                        listar(nomes, vencimentos, nElems);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Não existem funcionários");
                    }
                    break;
                case 3:
                    if (nElems > 0) {
                        atualizarVencimento(nomes, vencimentos, nElems);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Não existem funcionários");
                    }
                    break;
                case 4:
                    if (nElems > 0) {
                        if (eliminar(nomes, vencimentos, nElems) == true) {
                            JOptionPane.showMessageDialog(null,
                                    "Funcionário eliminado");
                            nElems--;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não há qualquer funcionário com o nome introduzido ");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Não existem funcionários");
                    }
                    break;
                case 5:
                    if (nElems > 0) {
                        ordenar(nomes, vencimentos, nElems);
                        listar(nomes, vencimentos, nElems);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Não existem funcionários");
                    }
                    break;
                case 6:
                    if(nElems < nomes.length){
                        nElems = carregarDados(nomes, vencimentos, nElems);
                    }else {
                        JOptionPane.showMessageDialog(null,
                                "Não existem funcionários");
                    }
                    break;
                case 7:
                    gravarDados(nomes, vencimentos, nElems);
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                            "“Opção inválida");
            }
        } while (op != 0);

    }
    private static int menu() {
        int op;
        op = Integer.parseInt(JOptionPane.showInputDialog(
                "0 – Sair\n1 – Ler notas\n2 – Ver notas\n" +
                        "3 – Inserir info aluno\n" +
                        "4 – Atualizar info aluno\n" +
                        "5 – Apagar info aluno\n" +
                        "6 - Ver melhores alunos\n" +
                        "7 - Gravar dados"));
        return op;
    }
}