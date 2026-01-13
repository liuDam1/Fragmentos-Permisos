package es.etg.dam.pmdm.rap.fragmetos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        const val READ_CONTACTS_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //-----------------------------PERMISOS--------------------
        binding.btnAceptar.setOnClickListener {
            checkReadContactsPermission()
        }
        //----------------------------------------------------------
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
    //--------------------------- PERMISOS  -------------------------------------

    private fun checkReadContactsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            //El permiso de acceso a los contactos no está aceptado, se pide
            requestReadConctactsPermission()
        } else {
            //El permiso está aceptado
            //aquí añadiríamos la lógica sobre los contactos
            Toast.makeText(this,"Acceso a la funcionalidad", Toast.LENGTH_SHORT).show()
        }
    }

    //Solicita el permiso
    private fun requestReadConctactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            //El usuario ya ha rechazado el permiso anteriormente, debemos indicarle que vaya a ajustes.
            Toast.makeText(this,"Conceda permisos en ajustes", Toast.LENGTH_SHORT).show()
        } else {
            //El usuario nunca ha aceptado ni rechazado, así que le solicitamos que acepte el permiso.
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                Companion.READ_CONTACTS_REQUEST_CODE
            )
        }
    }

    //Este método escucha la respuesta del usuario ante una solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_CONTACTS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //El usuario ha aceptado el permiso, ya no hay que volver a solicitarlo, podemos lanzar la funcionalidad desde aquí.
                    Toast.makeText(this,"Acceso a la funcionalidad una vez aceptado el permiso", Toast.LENGTH_SHORT).show()
                } else {
                    //El usuario ha rechazado el permiso
                    Toast.makeText(this,"Conceda permisos en ajustes", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Para aquellos permisos no controlados
            }
        }
    }
}