package com.example.crud_v1

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UpdateActivity : AppCompatActivity() {
    lateinit var updateImage: ImageView
    lateinit var updateButton: Button
    lateinit var updateDesc: EditText
    lateinit var updateTitle: EditText
    lateinit var updateLang: EditText
    lateinit var title: String
    lateinit var desc: String
    lateinit var lang: String
    lateinit var imageUrl: String
    lateinit var key: String
    lateinit var oldImageURL: String
    lateinit var uri: Uri
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateButton = findViewById(R.id.updateButton)
        updateDesc = findViewById(R.id.updateDesc)
        updateImage = findViewById(R.id.updateImage)
        updateLang = findViewById(R.id.updateLang)
        updateTitle = findViewById(R.id.updateTopic)

        /*var activityLaucher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult, object : ActivityResultCallback<ActivityResult> {
            override fun onActivityResult(result: ActivityResult?) {
                if (result.resultCode == Activity.RESULT_OK){
                    var data: Intent? = result.data
                    uri = data.data!!
                    updateImage.setImageURI(uri)
                }
                else {
                    Toast.makeText(UpdateActivity.this, "No imagen", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        var bundle: Bundle? = intent.extras
        if (bundle != null){
            //Glide.with(this).load(bundle.getString("Image")).into(updateImage)
            updateTitle.setText(bundle.getString("Title"))
            updateDesc.setText(bundle.getString("Description"))
            updateLang.setText(bundle.getString("Language"))
            key = bundle.getString("Key").toString()
            //oldImageURL = bundle.getString("Image").toString()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials").child(key)

        /*updateImage.setOnClickListener {
            var photoPicker: Intent = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.lauch(photoPicker)
        }*/*/

        updateButton.setOnClickListener {
            saveData()
            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun saveData() {
        //storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.lastPathSegment)

        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        var dialog: AlertDialog = builder.create()
        dialog.show()

        /*storageReference.putFile(uri).addOnSuccessListener {
            var uriTask: Task<Uri> = taskSnapshot.store.downloadUri
            while (!uriTask.isComplete)
            var urlImage: Uri = uriTask.result
            imageUrl = urlImage.toString()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
        }*/

        updateData()
        dialog.dismiss()
    }

    fun updateData() {
        title = updateTitle.text.toString().trim()
        desc = updateDesc.text.toString().trim()
        lang = updateLang.text.toString().trim()

        var dataClass: DataClass = DataClass(title, desc, lang)

        databaseReference.setValue(dataClass).addOnCompleteListener {
            /*if (task.isSuccessful()){
                var reference: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL)
                reference.delete()
            }*/
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            e: Exception ->
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}