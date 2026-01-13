package es.etg.dam.pmdm.rap.fragmetos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import es.etg.dam.pmdm.rap.fragmetos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentActionsListener {
    private lateinit var binding: ActivityMainBinding
    private var derechaRojo = true

    companion object{
        const val NOMBRE = "nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putString(NOMBRE, "Hola")
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RedFragment>(binding.frgDer.id)
                add<BlueFragment>(binding.frgIzq.id, args = bundle)
                //add<BlueFragment>(binding.frgIzq.id)
            }
        }

    }

    fun intercambiar(view: View){
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            if(!derechaRojo) {
                add<RedFragment>(binding.frgDer.id)
                add<BlueFragment>(binding.frgIzq.id)
            }else{
                add<BlueFragment>(binding.frgDer.id)
                add<RedFragment>(binding.frgIzq.id)
            }
            derechaRojo = !derechaRojo
        }
    }

    override fun onClickFragmentButton() {
        Toast.makeText(this, "Se ha pulsado el botón", Toast.LENGTH_SHORT).show()
    }
}