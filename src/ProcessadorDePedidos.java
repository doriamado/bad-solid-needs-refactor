import java.util.ArrayList;
import java.util.List;

class ProcessadorDePedidos {

    private BancoDeDadosMySQL banco = new BancoDeDadosMySQL();
    private ServicoEmailSMTP email = new ServicoEmailSMTP();

    public void processar(Pedido pedido) {
        double valorTotal = pedido.calcularTotal();

        if (pedido.tipoCliente.equals("COMUM")) {
            valorTotal = valorTotal * 0.95;
        } else if (pedido.tipoCliente.equals("VIP")) {
            valorTotal = valorTotal * 0.80;
        } else if (pedido.tipoCliente.equals("FUNCIONARIO")) {
            valorTotal = valorTotal * 0.50;
        }

        pedido.valorFinal = valorTotal;

        banco.salvar(pedido);

        email.enviarConfirmacao(pedido.emailCliente, "Pedido confirmado no valor de R$ " + valorTotal);
    }
}

class Pedido {
    String tipoCliente;
    String emailCliente;
    List<Double> itens = new ArrayList<>();
    double valorFinal;

    public double calcularTotal() {
        double soma = 0;
        for (Double preco : itens) {
            soma += preco;
        }
        return soma + calcularFrete();
    }

    public double calcularFrete() {
        return 25.00;
    }
}

class PedidoDigital extends Pedido {
    @Override
    public double calcularFrete() {
        throw new UnsupportedOperationException("Produto digital não possui frete!");
    }
}

interface MeioDePagamento {
    void processarCartaoCredito(String numeroCartao, int parcelas);
    void gerarBoletoBancario(String cpf);
    void gerarQrCodePix();
}

class PagamentoPix implements MeioDePagamento {
    @Override
    public void gerarQrCodePix() {
        System.out.println("QR Code PIX gerado com sucesso.");
    }

    @Override
    public void processarCartaoCredito(String numeroCartao, int parcelas) {
        throw new UnsupportedOperationException("PIX não passa cartão!");
    }

    @Override
    public void gerarBoletoBancario(String cpf) {
        throw new UnsupportedOperationException("PIX não gera boleto!");
    }
}

class BancoDeDadosMySQL {
    public void salvar(Pedido p) {
        System.out.println("Salvando pedido no MySQL...");
    }
}

class ServicoEmailSMTP {
    public void enviarConfirmacao(String destinatario, String mensagem) {
        System.out.println("Enviando e-mail SMTP para " + destinatario + ": " + mensagem);
    }
}

