package com.miproyecto.Proyecto_Integrador.controller;


import com.miproyecto.Proyecto_Integrador.model.Pago;
import com.miproyecto.Proyecto_Integrador.model.Pedido;
import com.miproyecto.Proyecto_Integrador.repository.PagoRepo;
import com.miproyecto.Proyecto_Integrador.repository.PedidoRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoRepo pagoRepo;
    private final PedidoRepo pedidoRepo;

    @Value("${SUPABASE_URL}")
    private String SUPABASE_URL;

    @Value("${SUPABASE_SERVICE_KEY}")
    private String SUPABASE_SERVICE_KEY;

    public PagoController(PagoRepo pagoRepo, PedidoRepo pedidoRepo) {
        this.pagoRepo = pagoRepo;
        this.pedidoRepo = pedidoRepo;
    }

    // ========== POST: crear pago + subir comprobante (Supabase público) ==========
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> crear(
            @RequestParam Long idPedido,
            @RequestParam Double monto,
            @RequestParam String metodo,
            @RequestParam String estadoPago,
            @RequestParam(value = "comprobante", required = false) MultipartFile file
    ) {
        Pedido pedido = pedidoRepo.findById(idPedido).orElseThrow();

        Pago p = new Pago();
        p.setPedido(pedido);
        p.setMonto(BigDecimal.valueOf(monto));
        p.setMetodo(metodo);
        p.setEstadoPago(estadoPago);
        p.setFechaRegistro(Instant.now());

        if (file != null && !file.isEmpty()) {
            try {
                String ext = Optional.ofNullable(file.getOriginalFilename())
                        .filter(n -> n.contains("."))
                        .map(n -> n.substring(n.lastIndexOf(".")))
                        .orElse(".jpg");

                String objectPath = "pagos/" + idPedido + "/" + UUID.randomUUID() + ext;
                String publicUrl = subirASupabasePublic("comprobantes", objectPath, file);

                // Guardar datos del comprobante en la entidad (columnas mapeadas en Pago.java)
                p.setComprobanteUrl(publicUrl);
                p.setComprobanteStoragePath(objectPath);
                p.setComprobanteMime(Optional.ofNullable(file.getContentType()).orElse("application/octet-stream"));
                p.setComprobanteSize((int) file.getSize());

            } catch (Exception ex) {
                System.err.println("❌ No se pudo subir a Supabase: " + ex.getMessage());
                // continúa: se registra el pago sin URL
            }
        }

        pagoRepo.save(p);

        // Map.of no acepta null -> usar LinkedHashMap para evitar NPE si la URL es null
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("ok", true);
        resp.put("ruta", p.getComprobanteUrl() != null ? p.getComprobanteUrl() : "");
        return resp;
    }

    // ===== helper: subir a Supabase Storage (bucket público) =====
    private String subirASupabasePublic(String bucket, String objectPath, MultipartFile file)
            throws IOException, InterruptedException {

        URI url = URI.create(SUPABASE_URL + "/storage/v1/object/" + bucket + "/" + objectPath);
        String contentType = Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");

        HttpRequest req = HttpRequest.newBuilder(url)
                .header("Authorization", "Bearer " + SUPABASE_SERVICE_KEY) // Service Role Key
                .header("apikey", SUPABASE_SERVICE_KEY)                     // importante para RLS
                .header("Content-Type", contentType)
                .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
            return SUPABASE_URL + "/storage/v1/object/public/" + bucket + "/" + objectPath;
        }
        throw new IOException("Supabase upload FAILED: " + resp.statusCode() + " - " + resp.body());
    }

    // ========== GET: listar pagos por pedido ==========
    @GetMapping
    public List<Map<String, Object>> listarPorPedido(@RequestParam("pedidoId") Long pedidoId) {
        if (pedidoId == null) return List.of();
        return pagoRepo.findByPedido_IdPedido(pedidoId)
                .stream()
                .map(pg -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("idPago", pg.getIdPago());
                    m.put("monto", pg.getMonto());
                    m.put("metodo", pg.getMetodo());
                    m.put("estado", pg.getEstadoPago());
                    m.put("ruta", pg.getComprobanteUrl()); // el front usa "ruta"
                    return m;
                })
                .toList();
    }
}
