package dao;

import database.ConnectionFactory;
import entity.ExameEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExameDAO {

    public String listarTipoExame(){
        String exames = "Exames Possíveis:\n";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "select * from tipoExame";
            ps = conn.prepareStatement (sql);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                exames = exames +  rs.getInt(1) + " " + rs.getString(2) + "\n";
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return exames;
    }
    public String ehPossivelRealizar(Integer idTipoExame){
        Integer quantidadeRequerida = 0, quantidadeDisponivel = 0;
        String retorno = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "select count(*) from material where idTipoExame = ?;";
            ps = conn.prepareStatement (sql);
            ps.setInt(1, idTipoExame);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            if(rs.next()){
                quantidadeRequerida = rs.getInt(1);
            }

            sql = "select count(*) from material where idTipoExame = ? and quantidade >=1;;";
            ps = conn.prepareStatement (sql);
            ps.setInt(1, idTipoExame);
            ps.execute();

            rs = ps.getResultSet();

            if(rs.next()){
                quantidadeDisponivel = rs.getInt(1);
            }

            if (quantidadeDisponivel >= quantidadeRequerida){
                retorno = "SIM";
            }else{
                retorno = "NAO";
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
        return retorno;
    }

    public void inserirExame(ExameEntity exameEntity){

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "insert into exame(idTipoExame, idPaciente) values(?,(select idPaciente from paciente where cpf = ?));";
            ps = conn.prepareStatement (sql);
            ps.setInt(1,exameEntity.getIdTipoExame());
            ps.setString(2, exameEntity.getCpf());
            ps.execute();



            sql = "update material set quantidade = quantidade - 1 where idTipoExame = ?;";
            ps = conn.prepareStatement (sql);
            ps.setInt(1,exameEntity.getIdTipoExame());
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally{
            try{
                ps.close();
                conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
