/*
 * Desenvolvido por: Grupo 4 "Os Grandes"
 * Versão: 1.0
 * Ultima Modificação: 24-02-2022 15:40
 * */
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

public class Main {
  public static void main(String[] args) {

    //Constantes
    final int TAMANHO = 50;

    //Variaveis
    int nElems = 0;
    int option;

    boolean exit = false;

    //Vetores
    int[] numeros = new int[TAMANHO];
    int[] algNotas = new int[TAMANHO];
    int[] javaNotas = new int[TAMANHO];
    int[] vbNotas = new int[TAMANHO];

    String[] turmas = new String[TAMANHO];
    String[] nomes = new String[TAMANHO];

    //Programa Principal

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("ToggleButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

      // ways to remove it from other controls...
      UIManager.put("CheckBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("RadioButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("Slider.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

      // figure out combobox
      UIManager.put("ComboBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

    } catch (Exception e) {
      e.printStackTrace();
    }

    do {

      option = menu();

      switch (option){
        case 1:
          if(nElems < TAMANHO){
            nElems = inserirAluno(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Numero máximo de alunos atingido!", "Falha ao inserir Aluno", JOptionPane.ERROR_MESSAGE);
          }
          break;

        case 2:
          if (nElems != 0){
            atualizarAluno(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else{
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case 4:
          if (nElems > 0){
            verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case 5:
          if(nElems < numeros.length){
            nElems = importarDados(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem dados");
          }
          break;

        case 6:
          exportarDados(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          break;

        default:
          exit = exitMenu();
      }
    } while (!exit);
  }

  /*Funcionalidades*/

  private static int inserirAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    //Variáveis
    String nome;
    String turma = "";
    String aux;

    int numero = 0;
    int algNota = 0;
    int javaNota = 0;
    int vbNota = 0;

    boolean isValid = false;
    boolean isCanceled = false;

    //Guarda o nome do aluno se não for cancelado(JOptionPane() quando cancelado retorna null).
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

    //Guarda a turma do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
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

    //Guarda o numero do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
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

    //Guarda a nota de algoritmia do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
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

    //Guarda a nota de java do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
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

    //Guarda a nota de visual basic do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
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

    //Se não tiver sido cancelado até agora insere o aluno no vetor e incremeta o nElems
    if (!isCanceled){
      //Inserir os alunos no vetor
      nomes[nElems] = nome;
      turmas[nElems] = turma;
      numeros[nElems] = numero;
      algNotas[nElems] = algNota;
      javaNotas[nElems] = javaNota;
      vbNotas[nElems] = vbNota;

      //Incrementa o nElems
      nElems++;

      //Mostra ao utilizador que o aluno foi inserido
      JOptionPane.showMessageDialog(null, "Aluno(a) inserido(a) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }

    //Retorna o nElems para poder ser atualizado no programa principal
    return nElems;

  }

  private static void atualizarAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    JOptionPane.showMessageDialog(null, "Atualizar Aluno", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

  }

  private static void verAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    //Variáveis
    int option;

    String[] options;

    //Os botões que são mostrados no fundo da tabela são inseridos num vetor de opções que será utilizado depois no JOptionPane da tabela
    options = new String[] {"Página Anterior", "Página Seguinte","Ordenar Alunos", "Voltar"};
    option = JOptionPane.showOptionDialog(null, tabelaAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina), "Ver Alunos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

    //As opções retornam uma Integer que corresponde ao indice do vetor das opções.
    //Caso sejam escolhidas as opções de "Página Anterior", "Página Seguinte" ou "Ordenar Alunos" chama a respetiva função.
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

    //Variáveis
    int auxNumero;
    int auxAlgNota;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    //Um conjunto de loops for é utilizado para comparar e trocar quando necessário as entradas dos u«alunos nos vetores
    for (int i = 0; i < nElems; i++) {
      for (int j = i + 1; j < nElems; j++) {
        //Ordena os alunos por ordem crescente de turma
        if(turmas[j].compareToIgnoreCase(turmas[i]) < 0){

          //Troca os nomes dos alunos
          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          //Troca as turmas dos alunos
          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          //Troca o numero dos alunos
          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          //Troca a nota de Algoritmia dos alunos
          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          //Troca a nota de Java dos alunos
          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          //Troca a nota de Visual Basic dos alunos
          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;
        }

        //Se as turmas forem iguais oredena por ordem crescente de numero
        if (numeros[i] > numeros[j] && turmas[j].compareToIgnoreCase(turmas[i]) == 0){

          //Troca os nomes dos alunos
          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          //Troca as turmas dos alunos
          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          //Troca o numero dos alunos
          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          //Troca a nota de Algoritmia dos alunos
          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          //Troca a nota de Java dos alunos
          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          //Troca a nota de Visual Basic dos alunos
          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;

        }
      }
    }

    //Chama a função verAlunos() para mostrar a tabela ordenada
    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);

  }

  private static int importarDados(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    int cop = nElems;
    int canceled;

    boolean isCanceled = false;

    JFileChooser file = new JFileChooser();

    try {

      do {
        file.setDialogTitle("Importar Alunos");
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

  private static void exportarDados(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems){

    int canceled;

    boolean isCanceled = false;
    boolean isSet = false;

    File file = null;

    JFileChooser fileChooser = new JFileChooser();

    do {
      fileChooser.setDialogTitle("Exportar Alunos");
      fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setPreferredSize(new Dimension(700, 500));
      fileChooser.setSelectedFile(new File("alunos.txt"));

      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Ficheiros de Texto (.txt)", "txt"));

      canceled = fileChooser.showSaveDialog(null);

      if (canceled == JFileChooser.CANCEL_OPTION){
        isCanceled = true;
      }

      if(checkValidFile(fileChooser.getSelectedFile().toString()) == 2 && fileChooser.getSelectedFile() != null){
        file = new File(fileChooser.getSelectedFile().toString() + ".txt");
        isSet = true;
      }

    }while (fileChooser.getSelectedFile() == null && !isCanceled && checkValidFile(fileChooser.getSelectedFile().toString()) == -1);

    if (!isSet && !isCanceled){
      file = fileChooser.getSelectedFile();
    }

    if(!isCanceled){
      try {
        Formatter fichAlunos = new Formatter(file);

        for (int x = 0; x < nElems; x++) {
          if(x == 0) {
            fichAlunos.format("%s:%d:%s:%d:%d:%d", turmas[x], numeros[x], nomes[x], algNotas[x], javaNotas[x], vbNotas[x]);
          }else{
            fichAlunos.format("\n%s:%d:%s:%d:%d:%d", turmas[x], numeros[x], nomes[x], algNotas[x], javaNotas[x], vbNotas[x]);
          }
        }

        fichAlunos.close();
        JOptionPane.showMessageDialog(null, nElems +  " Dado(s) exportado(s)");
      }catch (FileNotFoundException e){
        JOptionPane.showMessageDialog(null, "Não foi possivel criar o ficheiro!", "Ficheiro inválido!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /* Menus e outras necessidades*/
  public static int menu() {

    //Variáveis
    int selectedOption;

    //Vetores
    String[] options = {"Inserir Aluno", "Atualizar Informação", "Apagar Aluno", "Ver Alunos", "Carregar Alunos", "Exportar Alunos", "Sair"};


    JComboBox optionBox = new JComboBox(options);

    //Mostra o drop-down do menu com as opções inseridas no vetor acima. Retorna uma String com o valor da opção que foi escolhida
    JOptionPane.showMessageDialog(null, optionBox, "Menu", JOptionPane.PLAIN_MESSAGE);

    selectedOption = optionBox.getSelectedIndex();

    //Se o utilizador carregar no cancelar, a função Menu() envia a opção "Sair" para mostrar o menu de saída.
    if (selectedOption == options.length-1){
      return 0;
    }

    //Se o utilizador não tiver cancelado retorna a opção que foi selecionada.
    return selectedOption;


  }

  private static boolean exitMenu() {

    //Vetores
    String[] options = {"Sim", "Não"};

    //Componentes Java Swing
    JLabel lbl = new JLabel("Quer mesmo sair?");

    JPanel panel = new JPanel();
    panel.add(lbl);

    //Mostra um JOptionPane para confirmar que o utilizador quer mesmo sair que retorna o indice do vetor das opções ou -1 se for cancelado
    int selectedOption = JOptionPane.showOptionDialog(null, panel, "Sair", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[1]);

    //Se o utilizador tiver cancelado ou selecionado "Não" retorna false senão retorna true e sai do programa
    return selectedOption != 1 && selectedOption != -1;

  }

  private static String tabelaAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina){

    //Variáveis
    int numIn;
    int numFin;

    StringBuilder tabela;

    //Define primeiro indice da página
    numIn = (pagina * 10) - 10;

    //Define o ultimo indice da página
    numFin = Math.min(numIn + 10, nElems);

    //Adiciona o inicio de uma tabela HTML com estilização e cabeçalho
    tabela = new StringBuilder("<html> <head> <style> table { font-family: arial, sans-serif; border-collapse: collapse; width: 500px; margin-right: 0,} td, th { border: 2px solid #dddddd; text-align: left; margin-right: 0;} tr:nth-child(even) { background-color: #000000; font-weight: bold} </style> </head> <body> <table> <tr> <th>Turma</th> <th>Número</th> <th>Nome</th> <th>Alg</th> <th>Java</th> <th>VB</th> <th>Final</th> </tr>");

    //Adiciona á tabela uma nova fila com os dados do aluno corespondente ao indice do loop for
    for (int i = numIn; i < numFin; i++) {
      tabela.append("<tr> <td>").append(turmas[i].trim()).append("</td> <td>").append(numeros[i]).append("</td> <td>").append(nomes[i].trim()).append("</td> <td>").append(algNotas[i]).append("</td> <td>").append(javaNotas[i]).append("</td> <td>").append(vbNotas[i]).append("</td> <td>").append(notaFinal(algNotas[i], javaNotas[i], vbNotas[i])).append("</td> </tr>");
    }

    //Adiciona o fim de uma tabela HTML e o numero da página no centro
    tabela.append("</table> <br><p style='width:100%; text-align: center'> Página - ").append(pagina).append("</p><br> </body> </html>");

    //Retorna a tabela construida
    return tabela.toString();

  }

  private static int notaFinal(int algNota, int javaNota, int vbNota) {

    //Variáveis
    final double pAlg = 0.30;
    final double pJava = 0.40;
    final double pVb = 0.30;

    //Calcula a nota final do aluno e retorna-a
    return (int) ((algNota * pAlg) + (javaNota * pJava) + (vbNota * pVb));

  }

  private static void paginaAnterior(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    //Verifica se está na primeira página. Caso não esteja anda uma página para trás, senão continua na primeira. No final mostra a tabela na página escolhida.
    if (pagina != 1){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, (pagina - 1));
    }else{
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

  }

  private static void paginaSeguinte(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    //Verifica se está na ultima página. Caso não esteja anda uma página para a frente, senão continua na ultima. No final mostra a tabela na página escolhida.
    if(pagina != Math.ceil((double) nElems/10 )){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, (pagina + 1));
    }else {
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

  }

  private static int checkNumber(String input, String nome) {

    //Variáveis
    int output = -1;

    //Recebe uma string da inserção do numero. Se conseguir converter para Integer retorna o numero, senão pede o numero outra vez.
    while (output == -1){
      try {
        output = Integer.parseInt(input);
      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -2
        if (input == null){
          return -2;
        }

      }

      //Se o numero for negativo pede o numero outra vez
      if(output < -1){
        output = -1;

        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -2
        if (input == null){
          return -2;
        }
      }
    }

    //Quando o utilizador introduzir um numero válido retorna-o
    return output;

  }

  private static int checkNota(String input, String nome, String notaNome) {

    //Variáveis
    int output = -1;

    boolean isNumber = false;

    //Recebe uma string da inserção da nota. Se conseguir converter para Integer retorna a nota, senão pede a nota outra vez.
    while (output == -1){

      //Se o utilizador cancelar retorna -1
      if (input == null){
        return -1;
      }

      try {
        output = Integer.parseInt(input);

        isNumber = true;

      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -1
        if (input == null){
          return -1;
        }
      }

      //Se for um numero e não for uma nota válida (0-20) pede a nota outra vez
      if(isNumber && (output < 0 || output > 20)){
        output = -1;

        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);
        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -1
        if (input == null){
          return -1;
        }
      }

    }

    //Quando for introduzida uma nota válida retorna-a
    return output;

  }

  private static int checkDigits(int num){

    //Variáveis
    int count = 0;

    //Conta o numero de digitos de um numero
    while (num != 0) {
      num /= 10;
      count++;
    }

    //Retorna o numero de digitos contado
    return count;

  }

  private static int checkValidFile(String saveFilePath) {

    if (!saveFilePath.endsWith(".txt")){
      if (saveFilePath.endsWith("/")){
        return -1;
      }else {
        return 2;
      }
    }
    return 1;

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
