package com.example.mispropinas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.mispropinas.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    //Permite que MainActivity utilice view binding (una sola llamada para todos los views con un ID)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botonCalcular.setOnClickListener { calcularPropina() }
        binding.costoServicioEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    private fun calcularPropina() {
        val textoEnCampo = binding.costoServicioEditText.text.toString()
        val costo = textoEnCampo.toDoubleOrNull()
        if (costo == null || costo == 0.00) {
            binding.resultadoPropina.text = ""
            return
        }

        val porcentajePropina = when (binding.opcionesPropina.checkedRadioButtonId) {
            R.id.opcion_veinte_porciento -> 0.20
            R.id.opcion_dieciocho_porciento -> 0.18
            else ->0.15
        }
        var propina = porcentajePropina * costo

        if (binding.switchRedondear.isChecked) {
            propina = kotlin.math.ceil(propina)

        }

        mostrarPropina(propina)

    }

    private fun mostrarPropina(propina : Double) {
        val propinaFormateada = NumberFormat.getCurrencyInstance().format(propina)
        binding.resultadoPropina.text = getString(R.string.cantidad_de_propina, propinaFormateada)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}