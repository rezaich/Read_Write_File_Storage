package com.zaich.readwritefilestorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.zaich.readwritefilestorage.databinding.ActivityMainBinding
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonNew.setOnClickListener(this@MainActivity)
            buttonOpen.setOnClickListener(this@MainActivity)
            buttonSave.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> savefile()
        }
    }

    private fun savefile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(
                this,
                "isi title dulu",
                Toast.LENGTH_SHORT
            ).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(
                this,
                "isi konten dulu",
                Toast.LENGTH_SHORT
            )
                .show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel,this)
                Toast.makeText(this, "Saving" + fileModel.filename + "file", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showList() {
        val arrayList = ArrayList<String>()
        val path: File = filesDir
        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this,title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading" + fileModel.filename + "data", Toast.LENGTH_SHORT).show()
    }


    private fun newFile() {
        binding.apply {
            editTitle.setText("")
            editFile.setText("")
            Toast.makeText(this@MainActivity, "Clearing file", Toast.LENGTH_SHORT).show()
        }
    }
}