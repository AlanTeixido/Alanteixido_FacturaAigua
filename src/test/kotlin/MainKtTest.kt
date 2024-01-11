import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class MainKtTest {

    @Test
    fun testCalcularCostAigua_FamiliaNombrosa() {
        val coste = calcularCostAigua(400, true, false, false, 0.30, 0)
        assertEquals(66.0, coste, 0.01, "Error en el cálculo del coste para familia numerosa")
    }

    @Test
    fun testCalcularCostAigua_FamiliaMonomarental() {
        val coste = calcularCostAigua(400, false, true, false, 0.30, 4)
        assertEquals(120.0, coste, 0.01, "Error en el cálculo del coste para familia monomarental")
    }
}
