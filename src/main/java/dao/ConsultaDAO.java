package dao;

import database.ConnectionFactory;
import entity.ConsultaEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaDAO {

    public String listarConsultas(ConsultaEntity consultaEntity){
        String consultas = "Consultas agendadas: ";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "select * from consulta WHERE DATA = ? and idMedico = (select idMedico from medico where crm = ?) order by hora asc";
            ps = conn.prepareStatement (sql);
            ps.setString(1,consultaEntity.getData());
            ps.setString(2, consultaEntity.getCrm());
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                consultas = consultas +  rs.getString(3) + " | ";
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return consultas;
    }

    public void inserirConsulta(ConsultaEntity consultaEntity){

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "insert into consulta(data, hora, tipoConsulta, status, idPaciente, idMedico) values(?,?,?,?,(select idPaciente from paciente where cpf = ?), (select idMedico from medico where CRM = ?));";
            ps = conn.prepareStatement (sql);
            ps.setString(1, consultaEntity.getData());
            ps.setString(2, consultaEntity.getHora());
            ps.setString(3, consultaEntity.getTipoConsulta());
            ps.setString(4, consultaEntity.getStatus());
            ps.setString(5, consultaEntity.getCpf());
            ps.setString(6, consultaEntity.getCrm());
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


    public String listarConsultasByCPF(String cpf){
        String consultas = "Consultas não pagas:\n";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "select * from consulta WHERE idPaciente = (select idPaciente from paciente where cpf = ?) and tipoConsulta = 'PARTICULAR' and status = 'NAO PAGA'";
            ps = conn.prepareStatement (sql);
            ps.setString(1,cpf);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                consultas = consultas + "codigo: "+rs.getString(1) + " data: " + rs.getString(2)+" hora: " +rs.getString(3) + "\n";
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return consultas;
    }

    public void pagarConsulta(Integer codConsulta){

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "update consulta set status = 'PAGA' where idConsulta = ?";
            ps = conn.prepareStatement (sql);
            ps.setInt(1, codConsulta);
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
