package pe.edu.upc.bodeguin.data.network.model.request

class VoucherRequest (
    var paymentId: Int,
    var userId: Int,
    var detail: List<DetailRequest>
)