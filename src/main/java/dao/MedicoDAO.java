package dao;

import database.ConnectionFactory;
import entity.MedicoEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicoDAO {
    MedicoEntity medico = new MedicoEntity();

    public void inserirMedico (MedicoEntity medico){
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "insert into medico(CRM, especialidade, nomeMedico) values(?,?,?);";
            ps = conn.prepareStatement (sql);
            ps.setString(1, medico.getCRM());
            ps.setString(2, medico.getEspecialidade());
            ps.setString(3, medico.getNome());
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
