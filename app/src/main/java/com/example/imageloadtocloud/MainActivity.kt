package com.example.imageloadtocloud

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imageloadtocloud.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storageref :StorageReference
    private lateinit var firebaseStorage: FirebaseFirestore
    private var Imageuri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        initvars()
        registerclickevents()
    }



    private fun registerclickevents(){
        binding.upload.setOnClickListener {
            uploaadimage()


        }
        binding.viewall.setOnClickListener {
            startActivity(Intent(this,imageActivity::class.java))
        }
        binding.Imageview.setOnClickListener {
         resultluncher.launch("image/*")
        }
    }
    private val resultluncher  = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        Imageuri = it
        binding.Imageview.setImageURI(it)
    }
    private fun initvars(){
        storageref = FirebaseStorage.getInstance().reference.child("image")
        firebaseStorage =  FirebaseFirestore.getInstance()
    }

private fun uploaadimage(){
    storageref = storageref.child(System.currentTimeMillis().toString())
    Imageuri?.let {
        storageref.putFile(it).addOnCompleteListener{ Task->
            if (Task.isSuccessful) {
               storageref.downloadUrl.addOnSuccessListener {Uri->
                   val map = HashMap<String , Any>()
                   map["pic"] = Uri.toString()
                   firebaseStorage.collection("images").add(map).addOnCompleteListener { Firestoretask->
                       if (Firestoretask.isSuccessful){
                           Toast.makeText( this , "Uploaded successfully",Toast.LENGTH_SHORT).show()
                       }else

                           Toast.makeText( this , Firestoretask.exception?.message,Toast.LENGTH_SHORT).show()
                   }
                   binding.Imageview.setImageResource(R.drawable.vector)

               }
            }else{
                Toast.makeText( this , Task.exception?.message,Toast.LENGTH_SHORT).show()
                binding.Imageview.setImageResource(R.drawable.vector)
            }
        }
    }
}

}