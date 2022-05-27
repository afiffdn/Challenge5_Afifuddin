package com.example.challenge5_afifuddin.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityUserBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.datastore.DatastoreManager.Companion.DEF_ID
import com.example.challenge5_afifuddin.datastore.DatastoreManager.Companion.DEF_IMAGE
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.viewmodel.ProfilViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private var id: Int = DEF_ID
    private var uri = DEF_IMAGE
    private val viewmodel: ProfilViewModel by viewModel()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    if (fileUri != null) {
                        loadImage(fileUri)
                    }

                }

                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData()

        binding.btnImage.setOnClickListener {
            openGalerry()
        }

        binding.btnLogout.setOnClickListener {
            viewmodel.deleteUserPref()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnUpdate.setOnClickListener {
            updateData()
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun updateData() {
        binding.apply {
            when {
                etEditEmail.text.toString().isEmpty() ||
                        etEditPassword.text.toString().isEmpty() ||
                        etEditUsername.text.toString().isEmpty() ||
                        etEditNamaLengkap.text.toString().isEmpty() -> {
                    Toast.makeText(this@UserActivity, "Tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val user = User(
                        id,
                        etEditUsername.text.toString(),
                        etEditEmail.text.toString(),
                        etEditPassword.text.toString(),
                        etEditNamaLengkap.text.toString(),
                        uri
                    )
                    viewmodel.setUserPref(user)
                    viewmodel.updataUser(user)
                    Toast.makeText(this@UserActivity, "Suksek mengupdate data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun openGalerry() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun loadImage(result: Uri) {
        result.let {
            binding.btnImage.setImageURI(it)
        }

    }

    private fun fetchData() {
        viewmodel.getDataPref()
        viewmodel.userDataPref.observe(this) {
            if (it != null) {
                id = it.id_user!!
                binding.apply {
                    etEditEmail.setText(it.email)
                    etEditPassword.setText(it.password)
                    etEditNamaLengkap.setText(it.namaLengkap)
                    etEditUsername.setText(it.username)
                }
                if (it.image != DEF_IMAGE) {
                    uri = it.image
                    loadImage(Uri.parse(it.image))
                }
            }
        }
    }
}