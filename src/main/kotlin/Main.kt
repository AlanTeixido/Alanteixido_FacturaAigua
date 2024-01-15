import org.example.*

/**
 * Aquesta funció es encarrega de calcular el cost de la factura de l'aigua en funció dels litres consumits i altres elements.
 * @author AlanTeixido
 */
fun main() {
    while (true) {
        println("\n$WHITE_BACKGROUND_BRIGHT$BLACK_BOLD FACTURA DE L'AIGUA ITB $RESET")

        val preus: DoubleArray = doubleArrayOf(0.15, 0.30)
        val litresConsumits = introduirLitres()
        val preuXLitre: Double = if (litresConsumits > 200) preus[1] else preus[0]

        val esFamiliaNombrosa = readBoolean("$BLUE És família nombrosa? (true/false): $RESET", "Format incorrecte.")

        // Pregunta addicional abans de preguntar pel nombre de membres de la família monomarental
        val esFamiliaMono = if (esFamiliaNombrosa) false else {
            readBoolean("$BLUE És família monomarental? (true/false): $RESET", "Format incorrecte.")
        }

        val membresFamilia = if (esFamiliaMono) {
            readInt("$BLUE Introdueix el nombre de membres de la família monomarental: $RESET", "Introdueix un número!", "Valor fora del rang esperat!", 1, 10)
        } else {
            // Si no és família monomarental, el nombre de membres és irrelevant
            0
        }

        val teBoSocial = readBoolean("$BLUE Tens bo social? (true/false): $RESET", "Format incorrecte.")

        // Emmagatzema el resultat en una variable
        val coste = calcularCostAigua(litresConsumits, esFamiliaNombrosa, esFamiliaMono, teBoSocial, preuXLitre, membresFamilia)

    }
}

/**
 * Calcula el cost de la factura de l'aigua segons els paràmetres donats.
 * @param litresConsumits Litres d'aigua consumits.
 * @param esFamiliaNombrosa Indica si és família nombrosa.
 * @param esFamiliaMono Indica si és família monomarental.
 * @param teBoSocial Indica si té bo social.
 * @param preuXLitre Preu per litre.
 * @param membresFamilia Nombre de membres en la família monomarental.
 * @return Cost total de la factura.
 */
fun calcularCostAigua(litresConsumits: Int, esFamiliaNombrosa: Boolean, esFamiliaMono: Boolean, teBoSocial: Boolean, preuXLitre: Double, membresFamilia: Int): Double {
    var quotaFixa = 6.0
    var quotaVariable: Double = when {
        litresConsumits < 50 -> 0.0
        litresConsumits in 50..200 -> litresConsumits * preuXLitre
        litresConsumits > 200 -> litresConsumits * preuXLitre
        else -> 0.0
    }

    if (esFamiliaNombrosa) {
        // Descompte del 50% per a família nombrosa
        quotaVariable *= 0.5
    } else if (esFamiliaMono && membresFamilia > 0) {
        // Descompte 10x per cada membre en família monomarental
        val descompteMonomarental = 10 * membresFamilia * 0.5 * preuXLitre
        quotaVariable -= descompteMonomarental
    }

    if (teBoSocial) {
        quotaFixa = 3.0
        quotaVariable *= 0.2 // 80% de descompte
    }

    val costTotal = quotaFixa + quotaVariable
    mostrarDesglossament(costTotal, quotaFixa, litresConsumits, quotaVariable)

    // Retorna el cost total
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
