/*
* Desenvolvido por: Grupo 4 "Os Grandes"
* Versão: 1.0
* Ultima Modificação: 24-02-2022 15:40
* */
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
  public static void main(String[] args) throws FileNotFoundException {

    //Constantes
    final int TAMANHO = 50;

    //Variaveis
    int nElems = 0;

    String option;

    boolean exit = false;

    //Vetores
    int[] numeros = new int[TAMANHO];
    int[] algNotas = new int[TAMANHO];
    int[] javaNotas = new int[TAMANHO];
    int[] vbNotas = new int[TAMANHO];

    String[] turmas = new String[TAMANHO];
    String[] nomes = new String[TAMANHO];

    do {

      option = menu();

      switch (option){
        case "Inserir Aluno":
          if(nElems < TAMANHO){
            nElems = inserirAluno(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Numero máximo de alunos atingido!", "Falha ao inserir Aluno", JOptionPane.ERROR_MESSAGE);
          }
          break;

        case "Atualizar Informação":
          System.out.println("ATUALIZAR");
          //TODO: atualizarAluno()
          break;

        case "Ver Alunos":
          if (nElems > 0){
            verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case "Carregar Alunos":
          if(nElems < numeros.length){
            nElems = importarDados(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem dados");
          }
            break;

        case "Exportar Alunos":
          exportarDados(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          break;

        default:
          exit = exitMenu();
      }
    } while (!exit);
  }

  /*Funcionalidades*/

  private static int inserirAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    String nome;
    String turma = "";
    String aux;

    int numero = 0;
    int algNota = 0;
    int javaNota = 0;
    int vbNota = 0;

    boolean isValid = false;
    boolean isCanceled = false;

    do {
      nome = JOptionPane.showInputDialog(null, "Insira o nome do(a) aluno(a):", "Inserir Aluno",  JOptionPane.PLAIN_MESSAGE);

      if (nome == null){
        isCanceled = true;
      }else if (nome.trim().equals("")){
        JOptionPane.showMessageDialog(null, "Por favor preencha o campo do nome!", "Nome inválido!", JOptionPane.WARNING_MESSAGE);
      }else{
        nome = nome.trim();
      }

    }while (!isCanceled && nome.equals(""));

    do {

      if(!isCanceled){
        turma = JOptionPane.showInputDialog(null, "Insira a turma do aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

        if (turma == null) {
          isCanceled = true;
        } else if (turma.trim().equals("")) {
          JOptionPane.showMessageDialog(null, "Por favor preencha o campo da turma!", "Turma inválida!", JOptionPane.WARNING_MESSAGE);
        } else {
          turma = turma.toUpperCase();
        }
      }

    } while (!isCanceled && turma.equals(""));

    do {

      if (!isCanceled){
        aux = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

        if (aux == null){
          isCanceled = true;
        }else {
          numero = checkNumber(aux, nome);

          if (numero != -2){
            if(pesquisar(numeros, nElems, numero) != -1){
              JOptionPane.showMessageDialog(null, "O aluno que está a tentar inserir já se econtra guardado!", "Numero inválido!", JOptionPane.WARNING_MESSAGE);
            }else {
              isValid = true;
            }

            if (checkDigits(numero) != 7){
              JOptionPane.showMessageDialog(null, "Por favor introduza um numero válido!", "Numero inválido!", JOptionPane.WARNING_MESSAGE);
            }
          }else {
            isCanceled = true;
          }
        }
      }

    }while (!isCanceled && (checkDigits(numero) != 7 || !isValid));

    if (!isCanceled){

      aux = JOptionPane.showInputDialog(null, "Insira a nota de Algoritmia do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

      if (aux == null){
        isCanceled = true;
      }else {
        algNota = checkNota(aux, nome, "Algoritmia");

        if (algNota == -1){
          isCanceled = true;
        }
      }
    }

    if (!isCanceled){

      aux = JOptionPane.showInputDialog(null, "Insira a nota de Java do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

      if (aux == null){
        isCanceled = true;
      }else {
        javaNota = checkNota(aux, nome, "Java");

        if (javaNota == -1){
          isCanceled = true;
        }
      }
    }

    if (!isCanceled){

      aux = JOptionPane.showInputDialog(null, "Insira a nota de Visual Basic do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

      if (aux == null){
        isCanceled = true;
      }else {
        vbNota = checkNota(aux, nome, "Visual Basic");

        if (vbNota == -1){
          isCanceled = true;
        }
      }
    }

    if (!isCanceled){
      nomes[nElems] = nome;
      turmas[nElems] = turma;
      numeros[nElems] = numero;
      algNotas[nElems] = algNota;
      javaNotas[nElems] = javaNota;
      vbNotas[nElems] = vbNota;

      nElems++;

      JOptionPane.showMessageDialog(null, "Aluno(a) inserido(a) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }

    return nElems;

  }

  private static void verAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    int option;

    String[] options;

    options = new String[] {"Página Anterior", "Página Seguinte","Ordenar Alunos", "Voltar"};
    option = JOptionPane.showOptionDialog(null, tabelaAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina), "Ver Alunos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

    if (option == 0){
      paginaAnterior(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

    if (option == 1){
      paginaSeguinte(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

    if(option == 2){
      ordenarClassificacao(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }

  }

  private static void ordenarClassificacao(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    int auxNumero;
    int auxAlgNota;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    for (int i = 0; i < nElems; i++) {
      for (int j = i + 1; j < nElems; j++) {
        if(turmas[j].compareToIgnoreCase(turmas[i]) < 0){

          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;
        }

        if (numeros[i] > numeros[j] && turmas[j].compareToIgnoreCase(turmas[i]) == 0){

          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;

        }
      }
    }

    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);

  }

  private static int importarDados(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    int cop = nElems;
    int canceled;

    boolean isCanceled = false;

    JFileChooser file = new JFileChooser();

    try {

      do {
        file.setDialogTitle("Ficheiro com alunos");
        file.setDialogType(JFileChooser.OPEN_DIALOG);
        file.setAcceptAllFileFilterUsed(false);
        file.setPreferredSize(new Dimension(700, 500));

        file.addChoosableFileFilter(new FileNameExtensionFilter("Ficheiros de Texto (.txt)", "txt"));

        canceled = file.showOpenDialog(null);

        if (canceled == JFileChooser.CANCEL_OPTION){
          isCanceled = true;
        }

      }while (file.getSelectedFile() == null && !isCanceled);

      if (!isCanceled){
        Scanner fichFunc = new Scanner(file.getSelectedFile());

        while(fichFunc.hasNextLine() && nElems < numeros.length){

          String linha = fichFunc.nextLine();
          String[] vetLinha = linha.split(":");

          if(pesquisar(numeros, nElems, Integer.parseInt(vetLinha[1])) == -1){

            turmas[nElems] = vetLinha[0];
            numeros[nElems] = Integer.parseInt(vetLinha[1].trim());
            nomes[nElems] = vetLinha[2];

            algNotas[nElems] = Integer.parseInt(vetLinha[3].trim());
            javaNotas[nElems] = Integer.parseInt(vetLinha[4].trim());
            vbNotas[nElems] = Integer.parseInt(vetLinha[5].trim());

            nElems++;

          }
        }
        fichFunc.close();

        if (nElems - cop != 0){
          JOptionPane.showMessageDialog(null, (nElems-cop) +  " Dado(s) carregados(s)");
        }else {
          JOptionPane.showMessageDialog(null, "Não foram carregados dados!");
        }
      }
    }catch (FileNotFoundException e){
      JOptionPane.showMessageDialog(null, "Ficheiro não encontrado!", "Ficheiro inválido!", JOptionPane.ERROR_MESSAGE);
    }

    return nElems;

  }

  private static void exportarDados(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) throws FileNotFoundException {
    Formatter fichFunc = new Formatter("DadosAlunos.txt");
    for (int x = 0; x < nElems; x++) {
      if(x == 0) {
        fichFunc.format("%s:%d:%s:%d:%d:%d", turmas[x], numeros[x], nomes[x], algNotas[x], javaNotas[x], vbNotas[x]);
      }else{
        fichFunc.format("\n%s:%d:%s:%d:%d:%d", turmas[x], numeros[x], nomes[x], algNotas[x], javaNotas[x], vbNotas[x]);
      }
    }
    fichFunc.close();
    JOptionPane.showMessageDialog(null, nElems +  " Dado(s) exportado(s)");
  }

  /* Menus e outras necessidades*/
  public static String menu() {

    String[] options = {"Inserir Aluno", "Atualizar Informação", "Ver Alunos", "Carregar Alunos", "Exportar Alunos", "Sair"};

    String selectedOption;

    selectedOption = (String) JOptionPane.showInputDialog(null, "Selecione o que pretende fazer:\n\n", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

    if (selectedOption == null){
      return "Sair";
    }

    return selectedOption;


  }

  private static boolean exitMenu() {

    String[] options = {"Sim", "Não"};

    JLabel lbl = new JLabel("Quer mesmo sair?");

    JPanel panel = new JPanel();
    panel.add(lbl);

    int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[1]);

    return selectedOption != 1 && selectedOption != -1;

  }

  private static String tabelaAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina){

    int numIn;
    int numFin;

    StringBuilder tabela;

    numIn = (pagina * 10) - 10;

    numFin = Math.min(numIn + 10, nElems);

    tabela = new StringBuilder("<html> <head> <style> table { font-family: arial, sans-serif; border-collapse: collapse; width: 500px; margin-right: 0,} td, th { border: 2px solid #dddddd; text-align: left; margin-right: 0;} tr:nth-child(even) { background-color: #000000; font-weight: bold} </style> </head> <body> <table> <tr> <th>Turma</th> <th>Número</th> <th>Nome</th> <th>Alg</th> <th>Java</th> <th>VB</th> <th>Final</th> </tr>");

    for (int i = numIn; i < numFin; i++) {
      tabela.append("<tr> <td>").append(turmas[i].trim()).append("</td> <td>").append(numeros[i]).append("</td> <td>").append(nomes[i].trim()).append("</td> <td>").append(algNotas[i]).append("</td> <td>").append(javaNotas[i]).append("</td> <td>").append(vbNotas[i]).append("</td> <td>").append(notaFinal(algNotas[i], javaNotas[i], vbNotas[i])).append("</td> </tr>");
    }

    tabela.append("</table> <br><p style='width:100%; text-align: center'> Página - ").append(pagina).append("</p><br> </body> </html>");

    return tabela.toString();

  }

  private static int notaFinal(int algNota, int javaNota, int vbNota) {

    final double pAlg = 0.30;
    final double pJava = 0.40;
    final double pVb = 0.30;

    return (int) ((algNota * pAlg) + (javaNota * pJava) + (vbNota * pVb));

  }

  private static void paginaAnterior(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {
    if (pagina != 1){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, (pagina - 1));
    }else{
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }
  }

  private static void paginaSeguinte(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {
    if(pagina != Math.ceil((double) nElems/10 )){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, (pagina + 1));
    }else {
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }
  }

  private static int checkNumber(String input, String nome) {

    int output = -1;

    while (output == -1){
      try {
        output = Integer.parseInt(input);
      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        if (input == null){
          return -2;
        }

      }

      if(output < -1){
        output = -1;

        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        if (input == null){
          return -2;
        }
      }
    }

    return output;

  }

  private static int checkNota(String input, String nome, String notaNome) {

    int output = -1;

    boolean isNumber = false;

    while (output == -1){

      if (input == null){
        return -1;
      }

      try {
        output = Integer.parseInt(input);

        isNumber = true;

      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        if (input == null){
          return -1;
        }
      }

      if(isNumber && (output < 0 || output > 20)){
        output = -1;

        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);
        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        if (input == null){
          return -1;
        }
      }

    }

    return output;

  }

  private static int checkDigits(int num){
    int count = 0;

    while (num != 0) {
      num /= 10;
      count++;
    }

    return count;

  }

  private static int pesquisar(int[] numeros, int nElems, int numero) {
    int pos = 0;
    while (pos < nElems && numero != numeros[pos]) {
      pos++;
    }
    if (pos < nElems) {
      return pos;
    } else {
      return -1;
    }
  }
}
