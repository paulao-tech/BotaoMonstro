package br.com.starlog.controller;

import br.com.starlog.model.CriptoMensagem;
import java.util.ArrayList;
import java.util.List;

/**
 * CAMADA CONTROLLER (O Maestro da Transmissão)
 *
 * Orquestra as requisições da View, convertendo os tipos brutos
 * e alimentando o Model.
 *
 * O Controller manipula lógica e dados, não componentes visuais.
 */
public class TransmissaoController {

    private final List<String> bufferTransmissoes = new ArrayList<>();

    /**
     * Orquestra a transmissão da mensagem.
     *
     * @param remetenteRaw Remetente vindo do campo de texto da tela
     * @param msgRaw       Mensagem vinda do campo de texto da tela
     * @param chaveStr     Chave digitada como texto na tela
     * @return Pacote formatado no padrão: [REMETENTE] #CHECKSUM -> TEXTO_CIFRADO
     */
    public String processarTransmissao(String remetenteRaw, String msgRaw, String chaveStr) {
        int deslocamento;

        try {
            deslocamento = Integer.parseInt(chaveStr.trim());
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Chave deve ser um número inteiro válido.");
        }

        CriptoMensagem cripto = new CriptoMensagem(
            remetenteRaw, msgRaw, deslocamento
        );

        String textoCifrado = cripto.cifrar();
        int checksum = cripto.calcularChecksum();

        String pacoteFinal = String.format(
            "[%s] #%d -> %s",
            cripto.getRemetente(),
            checksum,
            textoCifrado
        );

        bufferTransmissoes.add(pacoteFinal);

        return pacoteFinal;
    }

    public int getTotalTransmissoes() {
        return bufferTransmissoes.size();
    }
}
