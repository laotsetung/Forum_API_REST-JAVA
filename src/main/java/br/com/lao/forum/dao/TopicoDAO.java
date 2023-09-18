package br.com.lao.forum.dao;

import br.com.lao.forum.conexao.ConnectionFactory;
import br.com.lao.forum.modelos.Topico;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TopicoDAO {

    private Connection con;
    private int id_criado;
    public TopicoDAO() throws SQLException {
        ConnectionFactory conFac = new ConnectionFactory();
        this.con = conFac.Conexao();
    }

    /*
        Verifica se existe um Tópico com o mesmo Titulo ou Mensagem
        Se existe retorna false, e termina o processo de incluir novo topico
        (na classe NovoTopicoController)
     */
    public boolean ValidaInserir(String titulo, String msg){
        String sql = "SELECT * FROM topico WHERE titulo = ? OR msg = ?";

        try (PreparedStatement pStm = con.prepareStatement(sql)) {
            pStm.setString(1, titulo);
            pStm.setString(2, msg);

            pStm.execute();
            try (ResultSet res = pStm.getResultSet()) {
                while (res.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /*
    Metodo que vai incluir o novo topico no banco de dados!
     */
    public int Inserir(Topico novoTopico) throws SQLException {
        String sql = "INSERT INTO topico (titulo, msg, data_criacao,estado,autor,curso) VALUES(?,?,?,?,?,?)";
        id_criado = -1;

        try (PreparedStatement pStm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pStm.setString(1, novoTopico.getTitulo());
            pStm.setString(2, novoTopico.getMsg());
            pStm.setDate(3, Date.valueOf(novoTopico.getData())); //data
            pStm.setInt(4,novoTopico.getEstado());
            pStm.setString(5,novoTopico.getAutor());
            pStm.setString(6,novoTopico.getCurso());

            pStm.executeUpdate();

            try (ResultSet rs = pStm.getGeneratedKeys()) {
                if (rs.next()) {
                    id_criado = rs.getInt(1);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return id_criado;
    }

    public List<Topico> SelectTopicos10(int page){
        List<Topico> topicos = new ArrayList<Topico>();

        if(page != 0){
            page *= 10;
        }

        String sql = "SELECT * FROM topico ORDER BY data_criacao ASC LIMIT 10 OFFSET " + page;

        try (PreparedStatement pStm = con.prepareStatement(sql)) {
            pStm.execute();

            try (ResultSet res = pStm.getResultSet()) {
                while (res.next()) {
                    Topico t = new Topico(res.getInt(1),res.getString(2),
                            res.getString(3), LocalDate.parse(res.getString(4)),
                            res.getInt(5), res.getString(6), res.getString(7));

                    topicos.add(t);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao selecionar registros de Hospedes!");
            throw new RuntimeException(ex);
        }

        return topicos;
    }

    public List<Topico> SelectUmTopico(int id){
        List<Topico> topicos = new ArrayList<Topico>();

        String sql = "SELECT * FROM topico WHERE id = ?";

        try (PreparedStatement pStm = con.prepareStatement(sql)) {
            pStm.setInt(1, id);
            pStm.execute();

            try (ResultSet res = pStm.getResultSet()) {
                while (res.next()) {
                    Topico t = new Topico(res.getInt(1),res.getString(2),
                            res.getString(3), LocalDate.parse(res.getString(4)),
                            res.getInt(5), res.getString(6), res.getString(7));

                    topicos.add(t);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao selecionar registros de Hospedes!");
            throw new RuntimeException(ex);
        }

        return topicos;
    }

    public int deleteTopico(int id){
        String sql = "DELETE FROM topico WHERE id = ?";

        try (PreparedStatement pStm = con.prepareStatement(sql)) {
            pStm.setInt(1, id);
            int linha = pStm.executeUpdate();

            return linha;
        } catch (SQLException ex) {
            System.out.println("Erro ao selecionar registros de Hospedes!");
            throw new RuntimeException(ex);
        }
    }

    public void fechaConexao() throws SQLException {
        this.con.close();
    }

    public int Atualiza(Topico atualizaTopico) {
        String sql = "UPDATE topico SET titulo = ?, msg = ?, autor = ?,curso = ? WHERE id = ?";
        int resultado = 99;

        try (PreparedStatement pStm = con.prepareStatement(sql)) {

            pStm.setString(1, atualizaTopico.getTitulo());
            pStm.setString(2, atualizaTopico.getMsg());
            pStm.setString(3,atualizaTopico.getAutor());
            pStm.setString(4,atualizaTopico.getCurso());
            pStm.setInt(5,atualizaTopico.getId());

            resultado = pStm.executeUpdate();
            return resultado;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
