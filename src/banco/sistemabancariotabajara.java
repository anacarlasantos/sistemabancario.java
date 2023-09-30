package banco;
import conexao.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class sistemabancariotabajara {

        public static void main(String[] args) {
            try {
                Connection conn = conexao.getConnection(); // Obter a conexão com o banco de dados
    
                scanner = new Scanner(System.in);
                int escolha;
    
                do {
                    System.out.println("Sistema Bancário Tabajara");
                    System.out.println("1 - Cadastrar conta");
                    System.out.println("2 - Consultar conta");
                    System.out.println("3 - Alterar conta");
                    System.out.println("4 - Remover conta");
                    System.out.println("5 - Exibir todas as contas");
                    System.out.println("6 - Sair do sistema");
                    System.out.print("Escolha uma opção: ");
                    escolha = scanner.nextInt();
    
                    switch (escolha) {
                        case 1:
                            cadastrarConta(conn);
                            break;
                        case 2:
                            consultarConta(conn);
                            break;
                        case 3:
                            alterarConta(conn);
                            break;
                        case 4:
                            removerConta(conn);
                            break;
                        case 5:
                            exibirTodasContas(conn);
                            break;
                        case 6:
                            System.out.println("Saindo do sistema.");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                } while (escolha != 6);
    
                conexao.fechar(conn); // Feche a conexão com o banco de dados
                scanner.close(); // Feche o scanner corretamente
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        private static Scanner scanner;
        private static String nome;
        public static String getNome() {
            return nome;
        }

        public static void setNome(String nome) {
            sistemabancariotabajara.nome = nome;
        }

        private static String codigo;
    
        public static String getCodigo() {
            return codigo;
        }

        public static void setCodigo(String codigo) {
            sistemabancariotabajara.codigo = codigo;
        }

        private static void cadastrarConta(Connection conn) throws SQLException {
                System.out.print("Digite o número da conta: ");
                int numeroConta = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Digite o nome do titular da conta: ");
                String nomeTitular = scanner.nextLine();
                System.out.print("Digite o saldo inicial: ");
                double saldoInicial = scanner.nextDouble();
            
                setCodigo(Integer.toString(numeroConta)); // Converte o número da conta para String e define como código
                setNome(nomeTitular); // Define o nome do titular
            
                String sql = "INSERT INTO conta (codigo, nome, saldo) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, getCodigo()); // Obtém o código usando o método get
                    pstmt.setString(2, getNome()); // Obtém o nome usando o método get
                    pstmt.setDouble(3, saldoInicial);
                    pstmt.executeUpdate();
                    System.out.println("Conta cadastrada com sucesso.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Erro ao cadastrar a conta.");
                }
            }
    private static void consultarConta(Connection conn) throws SQLException {
            System.out.print("Digite o número da conta a ser consultada: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine(); // Consuma a quebra de linha
        
            String sql = "SELECT * FROM conta WHERE codigo = ?"; // Use "codigo" em vez de "numero"
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, numeroConta);
                ResultSet resultSet = pstmt.executeQuery();
        
                if (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String titular = resultSet.getString("nome"); // Use "nome" em vez de "titular"
                    double saldo = resultSet.getDouble("saldo");
                    System.out.println("Número da conta: " + codigo); // Exibe o código da conta
                    System.out.println("Titular: " + titular);
                    System.out.println("Saldo: " + saldo);
                } else {
                    System.out.println("Conta não encontrada.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro ao consultar a conta.");
            }
        }
    private static void alterarConta(Connection conn) throws SQLException {
            System.out.print("Digite o número da conta a ser alterada: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine(); // Consuma a quebra de linha
            System.out.print("Digite o novo saldo: ");
            double novoSaldo = scanner.nextDouble();
        
            String sql = "UPDATE conta SET saldo = ? WHERE codigo = ?"; // Use "codigo" em vez de "numero"
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, novoSaldo);
                pstmt.setInt(2, numeroConta);
                int rowsAffected = pstmt.executeUpdate();
        
                if (rowsAffected > 0) {
                    System.out.println("Saldo da conta atualizado com sucesso.");
                } else {
                    System.out.println("Conta não encontrada.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro ao alterar a conta.");
            }
           
    }
    private static void removerConta(Connection conn) throws SQLException {
        System.out.print("Digite o número da conta a ser removida: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consuma a quebra de linha
    
        String sql = "DELETE FROM conta WHERE codigo = ?"; // Use "codigo" em vez de "numero"
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroConta);
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Conta removida com sucesso.");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover a conta.");
        }
    }
    
    private static void exibirTodasContas(Connection conn) throws SQLException {
            String sql = "SELECT * FROM conta";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet resultSet = pstmt.executeQuery();
        
                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String nome = resultSet.getString("nome");
                    double saldo = resultSet.getDouble("saldo");
                    System.out.println("Número da conta: " + codigo);
                    System.out.println("Titular: " + nome);
                    System.out.println("Saldo: " + saldo);
                    System.out.println("--------------------------");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro ao exibir as contas.");
            }
        }
}
