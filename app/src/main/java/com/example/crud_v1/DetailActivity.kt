package com.example.crud_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailActivity : AppCompatActivity() {

    lateinit var detailDesc: TextView
    lateinit var detailTitle: TextView
    lateinit var detailLang: TextView
    lateinit var detailImage: ImageView
    lateinit var deleteButton: FloatingActionButton
    lateinit var editButton: FloatingActionButton
    var key: String = "";
    var imageUrl = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailDesc = findViewById(R.id.detailDesc)
        detailImage = findViewById(R.id.detailImage)
        detailTitle = findViewById(R.id.detailTitle)
        deleteButton = findViewById(R.id.deleteButton)
        editButton = findViewById(R.id.editButton)
        detailLang = findViewById(R.id.detailLang)

        var bundle: Bundle? = this.intent.extras
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"))
            detailTitle.setText(bundle.getString("Title"))
            detailLang.setText(bundle.getString("Language"))
            key = bundle.getString("Key").toString()
            //imageUrl = bundle.getString("Image")
            //Glide.with(this).load(bundle.getString("Image")).into(detailImage)
        }
        deleteButton.setOnClickListener {
            var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorial")
            //var storage: FirebaseStorage = FirebaseStorage.getInstance()

            //var storageReference: StorageReference = storage.getReferenceFromUrl(imageUrl)
            /*storageReference.delete().addOnSuccessListener {
                reference.child(key).removeValue()
                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }*/
            reference.child(key).removeValue()
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        editButton.setOnClickListener {
            var intent: Intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("Title", detailTitle.text.toString())
            intent.putExtra("Description", detailDesc.text.toString())
            intent.putExtra("Language", detailLang.text.toString())
            //intent.putExtra("Image", imageUrl)
            intent.putExtra("Key", key)
            startActivity(intent)
        }
    }
}