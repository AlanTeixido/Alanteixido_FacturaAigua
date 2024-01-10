import org.example.*

/**
 * Este método se encarga de calcular el coste de la factura de l'aigua en función de los litros consumidos y otros elementos.
 * @author AlanTeixido
 */
fun main() {
    while (true) {
        println("\n$WHITE_BACKGROUND_BRIGHT$BLACK_BOLD FACTURA DE L'AIGUA ITB $RESET")

        val preus: DoubleArray = doubleArrayOf(0.15, 0.30)
        val litresConsumits = introduirLitres()
        val preuXLitre: Double = if (litresConsumits > 200) preus[1] else preus[0]

        val esFamiliaNombrosa = readBoolean("$BLUE És família nombrosa? (true/false): $RESET", "Format incorrecte.")

        // Pregunta adicional antes de preguntar por el número de miembros de la familia monomarental
        val esFamiliaMono = if (esFamiliaNombrosa) false else {
            readBoolean("$BLUE És família monomarental? (true/false): $RESET", "Format incorrecte.")
        }

        val membresFamilia = if (esFamiliaMono) {
            readInt("$BLUE Introdueix el nombre de membres de la família monomarental: $RESET", "Introdueix un número!", "Valor fora del rang esperat!", 1, 10)
        } else {
            // Si no es familia monomarental, el número de miembros es irrelevante
            0
        }

        val teBoSocial = readBoolean("$BLUE Tens bo social? (true/false): $RESET", "Format incorrecte.")

        calcularCostAigua(litresConsumits, esFamiliaNombrosa, esFamiliaMono, teBoSocial, preuXLitre, membresFamilia)
    }
}

/**
 * Calcula el coste de la factura de l'aigua según los parámetros dados.
 * @param litresConsumits Litros de agua consumidos.
 * @param esFamiliaNombrosa Indica si es familia numerosa.
 * @param esFamiliaMono Indica si es familia monomarental.
 * @param teBoSocial Indica si tiene bo social.
 * @param preuXLitre Precio por litro.
 * @param membresFamilia Número de miembros en la familia monomarental.
 */
fun calcularCostAigua(litresConsumits: Int, esFamiliaNombrosa: Boolean, esFamiliaMono: Boolean, teBoSocial: Boolean, preuXLitre: Double, membresFamilia: Int) {
    var quotaFixa = 6.0
    var quotaVariable: Double = when {
        litresConsumits < 50 -> 0.0
        litresConsumits in 50..200 -> litresConsumits * preuXLitre
        litresConsumits > 200 -> litresConsumits * preuXLitre
        else -> 0.0
    }

    if (esFamiliaNombrosa) {
        // Descuento del 50% para familia numerosa
        quotaVariable *= 0.5
    } else if (esFamiliaMono && membresFamilia > 0) {
        // Descuento 10x por cada miembro en familia monomarental
        val descompteMonomarental = 10 * membresFamilia * 0.5 * preuXLitre
        quotaVariable -= descompteMonomarental
    }

    if (teBoSocial) {
        quotaFixa = 3.0
        quotaVariable *= 0.2 // 80% de descuento
    }

    val costTotal = quotaFixa + quotaVariable
    mostrarDesglossament(costTotal, quotaFixa, litresConsumits, quotaVariable)
}

/**
 * Pide la introducción de los litros consumidos por el cliente.
 * @return Litros consumidos como número entero.
 */
fun introduirLitres(): Int {
    return readInt("$BLUE Introdueix els litres gastats: $RESET", "Introdueix un número!", "Valor fora del rang esperat!", 1, 1000)
}

/**
 * Muestra el desglose de la factura con colores.
 * @param costTotal Importe total de la factura de agua.
 * @param quotaFixa Cuota fija mensual.
 * @param litresConsumits Cantidad de litros de agua consumidos.
 * @param quotaVariable Cuota variable en función del consumo.
 */
fun mostrarDesglossament(costTotal: Double, quotaFixa: Double, litresConsumits: Int, quotaVariable: Double) {
    println("${WHITE_BACKGROUND_BRIGHT}${BLACK_BOLD}Desglossament de la factura d'aigua:${RESET}")
    println("${GREEN}Quota Fixa: ${RESET}$quotaFixa €")
    println("${GREEN}Consum (${litresConsumits} litres): ${RESET}$quotaVariable €")
    println("${YELLOW}------------------------------------------------------------- $RESET")
    println("${RED}Import Total: ${RESET}$costTotal €")
}
