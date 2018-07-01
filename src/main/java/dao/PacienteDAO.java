package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionFactory;
import entity.PacienteEntity;

public class PacienteDAO {


    public void inserirPaciente(PacienteEntity pacienteEntity){

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionFactory.obtemConexao();
            String sql = "insert into paciente(nomePaciente, cpf, sexo) values(?,?,?);";
            ps = conn.prepareStatement (sql);
            ps.setString(1, pacienteEntity.getNome());
            ps.setString(2, pacienteEntity.getCPF());
            ps.setString(3, pacienteEntity.getSexo());
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
