package com.example.challenge5_afifuddin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityUserBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.login.database_login.User
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import com.example.challenge5_afifuddin.viewmodel.ProfilViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private var dB: Database? = null
    private lateinit var profileViewModel: ProfilViewModel
    private lateinit var datastore: DatastoreManager
    private lateinit var viewmodel: MainViewModel

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
        datastore = DatastoreManager(this)
        fetchData()

        binding.btnImage.setOnClickListener {
                openGalerry()
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                datastore.delete()
            }
            startActivity(Intent(this, LoginActivity::class.java))
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
    @SuppressLint("CommitPrefEdits")
    private fun fetchData() {
        dB = Database.getInstance(this)
        profileViewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        var id = 1
        profileViewModel.userData.observe(this) {
            id = it.id_user!!
            binding.etEditUsername.setText(it.username)
            binding.etEditEmail.setText(it.email)
            binding.etEditPassword.setText(it.password)
            binding.etEditNamaLengkap.setText(it.namaLengkap)
        }
        binding.btnUpdate.setOnClickListener {
            when {
                binding.etEditNamaLengkap.text.toString().isEmpty() ||
                        binding.etEditUsername.text.toString().isEmpty() ||
                        binding.etEditEmail.text.toString().isEmpty() ||
                        binding.etEditPassword.text.toString().isEmpty() -> {
                    Toast.makeText(this, "incomplete!", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val user = User(
                        id,
                        binding.etEditUsername.text.toString(),
                        binding.etEditEmail.text.toString(),
                        binding.etEditPassword.text.toString(),
                        binding.etEditNamaLengkap.text.toString(),
                        "no_image"
                    )
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = dB?.user()?.updateUser(user)
                        runBlocking(Dispatchers.Main) {
                            if (result != 0) {
                                Toast.makeText(
                                    this@UserActivity,
                                    "Data ${binding.etEditNamaLengkap.text} Berhasil Disimpan ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this@UserActivity, MainActivity::class.java))
                            } else {
                                Toast.makeText(
                                    this@UserActivity,
                                    "Data Gagal Disimpan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }


    }
}