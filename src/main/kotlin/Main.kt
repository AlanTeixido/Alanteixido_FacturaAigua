import org.example.*

/**
 * Aquesta funció principal es encarrega de calcular el cost de la factura de l'aigua en funció dels litres consumits i altres elements. El màxim de càlculs del cost és de 3.
 * @author AlanTeixido
 */
fun main() {
    for (i in 1..3) {
        println("\n$WHITE_BACKGROUND_BRIGHT$BLACK_BOLD FACTURA DE L'AIGUA ITB $RESET")

        val litresConsumits = introduirLitres()
        val preuXLitre = calcularTarifa(litresConsumits)

        val esFamiliaNombrosa = readBoolean("$BLUE És família nombrosa? (true/false): $RESET", "Format incorrecte.")
        val esFamiliaMono = if (esFamiliaNombrosa) false else readBoolean("$BLUE És família monomarental? (true/false): $RESET", "Format incorrecte.")

        val membresFamilia = if (esFamiliaMono) {
            readInt("$BLUE Introdueix el nombre de membres de la família monomarental: $RESET", "Introdueix un número!", "Valor fora del rang esperat!", 1, 10)
        } else {
            0
        }

        val teBoSocial = readBoolean("$BLUE Tens bo social? (true/false): $RESET", "Format incorrecte.")
        val coste = calcularCostAigua(litresConsumits, esFamiliaNombrosa, esFamiliaMono, teBoSocial, preuXLitre, membresFamilia)
    }
}

/**
 * Calcula el cost de la tarifa de l'aigua segons els litres consumits.
 * @param litresConsumits Litres d'aigua consumits.
 * @return Cost de la tarifa.
 */
fun calcularTarifa(litresConsumits: Int): Double {
    return when {
        litresConsumits > 200 -> 0.30
        litresConsumits in 50..200 -> 0.15
        else -> 0.0
    }
}

/**
 * Calcula el cost de la factura de l'aigua segons els paràmetres donats.
 * @param litresConsumits Litres d'aigua consumits.
 * @param esFamiliaNombrosa Indica si és família nombrosa.
 * @param esFamiliaMono Indica si és família monomarental.
 * @param teBoSocial Indica si té bo social.
 * @param preuXLitre Preu per litre.
 * @param membresFamilia Nombre de membres a la família monomarental.
 * @return Cost total de la factura.
 */
fun calcularCostAigua(litresConsumits: Int, esFamiliaNombrosa: Boolean, esFamiliaMono: Boolean, teBoSocial: Boolean, preuXLitre: Double, membresFamilia: Int): Double {
    var quotaFixa = 6.0
    var quotaVariable = litresConsumits * preuXLitre

    if (esFamiliaNombrosa) {
        quotaVariable *= 0.5
    } else if (esFamiliaMono && membresFamilia > 0) {
        val descompteMonomarental = 10 * membresFamilia * 0.5 * preuXLitre
        quotaVariable -= descompteMonomarental
    }

    if (teBoSocial) {
        quotaFixa = 3.0
        quotaVariable *= 0.2
    }

    val costTotal = quotaFixa + quotaVariable
    mostrarDesglossament(costTotal, quotaFixa, litresConsumits, quotaVariable)

    return costTotal
}

/**
 * Demana la introducció dels litres consumits pel client.
 * @return Litres consumits com a número enter.
 */
fun introduirLitres(): Int {
    return readInt("$BLUE Introdueix els litres gastats: $RESET", "Introdueix un número!", "Valor fora del rang esperat!", 1, 1000)
}

/**
 * Mostra el desglossament de la factura amb colors.
 * @param costTotal Import total de la factura d'aigua.
 * @param quotaFixa Quota fixa mensual.
 * @param litresConsumits Quantitat de litres d'aigua consumits.
 * @param quotaVariable Quota variable en funció del consum.
 */
fun mostrarDesglossament(costTotal: Double, quotaFixa: Double, litresConsumits: Int, quotaVariable: Double) {
    println("${WHITE_BACKGROUND_BRIGHT}${BLACK_BOLD}Desglossament de la factura d'aigua:${RESET}")
    println("${GREEN}Quota Fixa: ${RESET}$quotaFixa €")
    println("${GREEN}Consum (${litresConsumits} litres): ${RESET}$quotaVariable €")
    println("${YELLOW}------------------------------------------------------------- $RESET")
    println("${RED}Import Total: ${RESET}$costTotal €")
}
