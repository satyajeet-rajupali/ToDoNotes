package com.satyajeet.todonotes.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.satyajeet.todonotes.BuildConfig
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.utils.AppConstant
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var submitButton: MaterialButton
    private lateinit var profileImage: ImageView
    var picturePath = ""
    lateinit var imageLocation: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        bindViews()
        clickListeners()
    }

    private fun clickListeners() {
        submitButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstant.TITLE, title.text.toString())
            intent.putExtra(AppConstant.DESCRIPTION, description.text.toString())
            intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
            Log.d("Testing", "Image Path: $picturePath")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        profileImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (checkAndRequestPermission()) {
                    setUpDialog()
                }
            }

        })
    }

    private fun checkAndRequestPermission(): Boolean {
        val permissionCamera =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val listPermissionNeeded = ArrayList<String>()
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this, listPermissionNeeded.toTypedArray<String>(),
                MY_PERMISSION_CODE
            )
            return false
        }

        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CODE->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    setUpDialog()
                }
            }
        }
    }

    private fun setUpDialog() {
        val view = LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dailog1, null)
        val openCamera = view.findViewById<TextView>(R.id.textView1)
        val openGallery = view.findViewById<TextView>(R.id.textView2)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()


        openCamera.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                    } catch (e: Exception) {

                    }

                    if (photoFile != null) {
                        val photoUri = FileProvider.getUriForFile(
                            this@AddNotesActivity,
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile
                        )
                        imageLocation = photoFile
                        Log.d("Testing", "CameraClicked Path: " + imageLocation)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        dialog.hide()
                        startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                    }
                }
            }

        })

        openGallery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
                dialog.hide()
            }

        })

        dialog.show()

    }

    private fun createImageFile(): File? {

        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)

    }

    private fun bindViews() {
        title = findViewById(R.id.editTextTitle)
        description = findViewById(R.id.editTextDescription)
        submitButton = findViewById(R.id.submit_button)
        profileImage = findViewById(R.id.profile_image)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }

    companion object {
        const val REQUEST_CODE_GALLERY = 100
        const val REQUEST_CODE_CAMERA = 101
        const val MY_PERMISSION_CODE = 111

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            REQUEST_CODE_CAMERA->{
                picturePath = imageLocation.path.toString()
                Log.d("Testing", "CameraClicked Path: " + imageLocation.absoluteFile)

                Glide.with(this).load(imageLocation.absoluteFile).into(profileImage)
            }
            REQUEST_CODE_GALLERY->{
                val selectedImage = data?.data
                picturePath = selectedImage.toString()
                Log.d("Testing", "SelectedImage Path: " + picturePath)

                Glide.with(this).load(picturePath).into(profileImage)

            }
        }
    }
}