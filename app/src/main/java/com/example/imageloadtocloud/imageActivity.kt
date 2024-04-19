package com.example.imageloadtocloud

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.imageloadtocloud.databinding.ActivityImageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

private lateinit var storageref : StorageReference
private lateinit var firebaseStorage: FirebaseFirestore
private var mlist = mutableListOf<String>()
private lateinit var adapter: Imageadapter
class imageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initvars()
        getimages()
    }

    private fun initvars() {

        firebaseStorage = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Imageadapter(mlist)
        binding.recyclerView.adapter = adapter
    }
    private fun getimages(){
        firebaseStorage.collection("images")
            .get().addOnSuccessListener {
                for (i in it ){
                    mlist.add(i.data["pic"].toString())
                }
                adapter.notifyDataSetChanged()

            } }
    }
