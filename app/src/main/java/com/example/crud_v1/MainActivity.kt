package com.example.crud_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var dataList: ArrayList<DataClass>
    lateinit var databaseReferece: DatabaseReference
    lateinit var eventListener: ValueEventListener
    lateinit var searchView: SearchView
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recycleView)
        searchView = findViewById(R.id.search)
        searchView.clearFocus()

        var gridLayoutManager: GridLayoutManager = GridLayoutManager(this,1)
        recyclerView.layoutManager = gridLayoutManager

        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        var dialog: AlertDialog = builder.create()
        dialog.show()

        dataList = ArrayList<DataClass>()

        adapter = MyAdapter(this, dataList)
        recyclerView.adapter = adapter

        databaseReferece = FirebaseDatabase.getInstance().getReference("Android Tutorials")
        dialog.show()

        eventListener = databaseReferece.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    var dataClass: DataClass? = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataClass.key = itemSnapshot.key.toString()
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchList(newText)
                }
                return true
            }
        })

        fab.setOnClickListener {
            var intent:Intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
    }

    fun searchList(text: String){
        var searchList: ArrayList<DataClass> = ArrayList<DataClass>()
        for (dataClass: DataClass in dataList){
            if (dataClass.dataTitle.lowercase().contains(text.lowercase())){
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}