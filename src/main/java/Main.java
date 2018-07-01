import javax.swing.*;

import dao.ConsultaDAO;
import dao.ExameDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import entity.ConsultaEntity;
import entity.ExameEntity;
import entity.MedicoEntity;
import entity.PacienteEntity;

import java.awt.*;

public class Main {
    public static void main(String Args[]){


        int op;
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("1-Cadastrar Paciente\n2-Cadastrar Médico\n3-Agendar Consulta\n4-Realizar Pagamento Consulta Particular\n5-Cadastrar Exame\n0-Sair"));
            switch (op) {
                case 1:
                    PacienteEntity paciente = new PacienteEntity();

                    paciente.setNome(JOptionPane.showInputDialog("Digite o nome").toUpperCase().toString());
                    paciente.setSexo(JOptionPane.showInputDialog("Digite o sexo M ou F").toUpperCase().toString());
                    paciente.setCPF(JOptionPane.showInputDialog("Digite o CPF, apenas números"));

                    PacienteDAO pacienteDAO = new PacienteDAO();

                    pacienteDAO.inserirPaciente(paciente);
                    msgPadrao();
                    break;
                    case 2:
                        MedicoEntity medico = new MedicoEntity();
                        medico.setNome(JOptionPane.showInputDialog("Digite o nome").toUpperCase().toString());
                        medico.setCRM(JOptionPane.showInputDialog("Digite o CRM, apenas números").toUpperCase().toString());
                        Integer espec = Integer.parseInt(JOptionPane.showInputDialog("Digite a especialidade: 1-Neuro\n2-Cardio\n3-Plastico\n4-Geral\n5-Pediatra"));
                        switch (espec){
                            case 1 :
                                medico.setEspecialidade("NEURO");
                                break;
                            case 2 :
                                medico.setEspecialidade("CARDIO");
                                break;
                            case 3:
                                medico.setEspecialidade("PLASTICO");
                                break;
                            case 4:
                                medico.setEspecialidade("GERAL");
                                break;
                            case 5:
                                medico.setEspecialidade("PEDIATRA");
                        }
                        MedicoDAO medicoDAO = new MedicoDAO();
                        medicoDAO.inserirMedico(medico);
                        msgPadrao();
                    break;
                case 3:
                    ConsultaEntity consulta = new ConsultaEntity();
                    ConsultaDAO consultaDAO = new ConsultaDAO();
                    consulta.setData(JOptionPane.showInputDialog("Digite a data da Consulta: ").toString());
                    consulta.setCrm(JOptionPane.showInputDialog("Digite o CRM do médico desejado: ").toString());

                    String consultas  = consultaDAO.listarConsultas(consulta);
                    JOptionPane.showMessageDialog(null, consultas);

                    String verifica = null;
                    verifica = JOptionPane.showInputDialog("Agendar nova? S / N").toUpperCase().toString();
                    if (verifica.substring(0,1).equals("S")){
                        consulta.setCpf(JOptionPane.showInputDialog("Digite o CPF do paciente:").toString());
                        String hora = JOptionPane.showInputDialog("Horários: 9:00 ~ 11:30 & 13:00 ~ 19:30\n"+consultas+"\nDigite a hora: ");
                        while (Integer.parseInt(hora.substring(0,2)) < 9 || Integer.parseInt(hora.substring(0,2)) == 12  || Integer.parseInt(hora.substring(0,2)) > 19){
                            JOptionPane.showMessageDialog(null,"Entre com um horário válido!");
                            hora = JOptionPane.showInputDialog("Horários: 9:00 ~ 11:30 & 13:00 ~ 19:30\n"+consultas+"\nDigite a hora: ");
                        }
                        consulta.setHora(hora);
                        Integer ehconvenio = Integer.parseInt(JOptionPane.showInputDialog("Qual o tipo de consulta?\n1-Convenio\n2-Particular"));
                        if (ehconvenio == 1){
                            consulta.setTipoConsulta("CONVENIO");
                            consulta.setStatus("PAGA CONVENIO");
                        }else{
                            consulta.setTipoConsulta("PARTICULAR");
                            consulta.setStatus("NAO PAGA");
                        }
                        consultaDAO.inserirConsulta(consulta);
                    }else{
                        break;
                    }
                    break;
                case 4:
                    consultaDAO = new ConsultaDAO();
                    String cpf = JOptionPane.showInputDialog("Digite o CPF: ");
                    String consultasAbertas = consultaDAO.listarConsultasByCPF(cpf);
                    Integer codConsulta = Integer.parseInt(JOptionPane.showInputDialog(consultasAbertas + "\n Digite o código da consulta que deseja fechar: \n( 0 para cancelar)"));
                    if(codConsulta == 0 ){
                        break;
                    }else{
                        consultaDAO.pagarConsulta(codConsulta);
                        msgPadrao();
                    }
                    break;
                case 5:

                    ExameDAO exameDAO = new ExameDAO();

                    ExameEntity exameEntity = new ExameEntity();
                    exameEntity.setCpf(JOptionPane.showInputDialog("Digite o CPF, apenas números: "));

                    String exames = exameDAO.listarTipoExame();
                    exameEntity.setIdTipoExame( Integer.parseInt(JOptionPane.showInputDialog(exames)));

                    String ehPossivel = exameDAO.ehPossivelRealizar(exameEntity.getIdTipoExame());
                        if(ehPossivel.equals("SIM")){
                            exameDAO.inserirExame(exameEntity);
                            msgPadrao();
                        }else{
                            JOptionPane.showMessageDialog(null, "Não é possível realizar o exame por falta de estoque.");
                            break;
                        }
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida");
            }
        } while (op != 0);
    }


    public static void msgPadrao(){
        JOptionPane.showMessageDialog(null, "Operação Realizada");
    }

}
