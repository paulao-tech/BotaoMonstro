package br.com.starlog.amador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.com.starlog.controller.TransmissaoController;

/**
 * ESTAÇÃO STARLOG-ZERO
 *
 * Interface visual com as regras de negócio delegadas ao Model
 * e a orquestração delegada ao Controller.
 */
public class TelaCaoticaLegada extends JFrame {

    private JTextField txtRemetente;
    private JTextField txtMensagem;
    private JTextField txtChaveCifra;
    private JButton btnDespacharCifrado;
    private JTextArea areaLog;

    private final TransmissaoController controller = new TransmissaoController();

    public TelaCaoticaLegada() {
        super("SISTEMA LEGADO CORROMPIDO - STARLOG");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Remetente da Frota:"));
        txtRemetente = new JTextField(20);
        add(txtRemetente);

        add(new JLabel("Mensagem Secreta:"));
        txtMensagem = new JTextField(20);
        add(txtMensagem);

        add(new JLabel("Chave de Deslocamento (César 1 a 25):"));
        txtChaveCifra = new JTextField(5);
        add(txtChaveCifra);

        btnDespacharCifrado = new JButton("Cifrar e Transmitir");
        add(btnDespacharCifrado);

        areaLog = new JTextArea(10, 35);
        add(new JScrollPane(areaLog));

        // Evento do botão: coleta os dados e apresenta o resultado.
        btnDespacharCifrado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String remetente = txtRemetente.getText();
                String msg = txtMensagem.getText();
                String chaveStr = txtChaveCifra.getText();

                try {
                    String pacoteFinal = controller.processarTransmissao(
                        remetente, msg, chaveStr
                    );

                    areaLog.append(pacoteFinal + "\n");
                    JOptionPane.showMessageDialog(
                        null, "Transmissão efetuada com sucesso!"
                    );
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(
                        null, "ERRO: " + ex.getMessage()
                    );
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCaoticaLegada().setVisible(true));
    }
}
