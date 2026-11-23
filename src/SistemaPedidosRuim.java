public class SistemaPedidosRuim {
    public static void main(String[] args) {
        ProcessadorDePedidos processador = new ProcessadorDePedidos();

        Pedido pedido1 = new Pedido();
        pedido1.tipoCliente = "VIP";
        pedido1.emailCliente = "ana@email.com";
        pedido1.itens.add(100.0);
        processador.processar(pedido1);

        Pedido pedido2 = new PedidoDigital();
        pedido2.tipoCliente = "COMUM";
        pedido2.emailCliente = "carlos@email.com";
        pedido2.itens.add(50.0);
        processador.processar(pedido2);
    }
}