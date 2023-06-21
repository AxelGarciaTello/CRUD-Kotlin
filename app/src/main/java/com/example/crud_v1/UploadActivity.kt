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
import androidx.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.DateFormat
import java.util.Calendar

class UploadActivity : AppCompatActivity() {

    lateinit var uploadImage: ImageView
    lateinit var saveButton: Button
    lateinit var uploadTopic: EditText
    lateinit var uploadDesc: EditText
    lateinit var uploadLang: EditText
    lateinit var imageURL: String
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadImage = findViewById(R.id.uploadImage)
        uploadDesc = findViewById(R.id.uploadDesc)
        uploadTopic = findViewById(R.id.uploadTopic)
        uploadLang = findViewById(R.id.uploadLang)
        saveButton = findViewById(R.id.saveButton)

        /*var activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                var data: Intent? = result.data
                uri = data?.data!!
                uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this, "No Imagen", Toast.LENGTH_SHORT).show()
            }
        }*/

        /*uploadImage.setOnClickListener {
            var photoPicker: Intent = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }*/*/

        saveButton.setOnClickListener {
            saveData()
        }

    }

    fun saveData() {
        //var storageReference: StorageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.lastPathSegment.toString())
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        var dialog: AlertDialog = builder.create()
        dialog.show()
        uploadData()
        dialog.dismiss()

        /*storageReference.putFile(uri).addOnSuccessListener {
            taskSnapshot: UploadTask.TaskSnapshot ->
            var uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete)
            var urlImage: Uri = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            e : Exception ->
            dialog.dismiss()
        }*/
    }

    fun uploadData() {
        var title: String = uploadTopic.text.toString()
        var desc: String = uploadDesc.text.toString()
        var lang: String = uploadLang.text.toString()

        var dataClass: DataClass = DataClass(title, desc, lang)

        var currentDate = DateFormat.getDateInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(currentDate).setValue(dataClass).addOnCompleteListener {
            task: Task<Void> ->
            if (task.isSuccessful){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener {
            e: Exception ->
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}