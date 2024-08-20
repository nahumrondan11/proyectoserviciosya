package pe.idat.proyectoserviciosya.core.dataclass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun generatePDF(
    context: Context,
    nombreUsuario: String,
    nombreServicio: String,
    fechaActual: String,
    costoServicio: String,
    opcionPagoSeleccionada: String
) {
    // Crear un nuevo documento PDF
    val pdfDocument = PdfDocument()

    // Configurar el estilo del texto
    val paint = Paint()
    paint.textSize = 12f
    paint.isFakeBoldText = true

    // Crear una nueva página
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    // Escribir los detalles en el PDF
    var yPosition = 50
    canvas.drawText("ServiciosYa", 100f, yPosition.toFloat(), paint)
    yPosition += 30
    paint.isFakeBoldText = false
    canvas.drawText("RUC: 12020726831", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("----------------------------", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("Nombre del Usuario: $nombreUsuario", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("Nombre del Servicio: $nombreServicio", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("Fecha: $fechaActual", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("Costo del Servicio: S/ $costoServicio", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("Opción de Pago: $opcionPagoSeleccionada", 50f, yPosition.toFloat(), paint)
    yPosition += 30
    canvas.drawText("----------------------------", 50f, yPosition.toFloat(), paint)

    // Finalizar la página
    pdfDocument.finishPage(page)

    // Guardar el archivo PDF en almacenamiento externo
    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val currentDateAndTime: String = sdf.format(Date())

    val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
    val file = File(directoryPath, "Comprobante_$currentDateAndTime.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        // Aquí puedes mostrar un mensaje de éxito, por ejemplo un Toast
        // Toast.makeText(context, "PDF guardado en $directoryPath", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        // Manejar el error
    }

    // Cerrar el documento PDF
    pdfDocument.close()
}
