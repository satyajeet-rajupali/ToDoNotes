package com.satyajeet.todonotes.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.FileProvider
import com.google.android.material.button.MaterialButton
import com.satyajeet.todonotes.BuildConfig
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.utils.AppConstant
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddNotesActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var submitButton: MaterialButton
    private lateinit var profileImage: CircleImageView

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
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        profileImage.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setUpDialog()
            }

        })
    }

    private fun setUpDialog() {
        val view = LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dailog1, null)
        val openCamera = view.findViewById<TextView>(R.id.textView1)
        val openGallery = view.findViewById<TextView>(R.id.textView2)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()
        dialog.show()


        openCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(takePictureIntent.resolveActivity(packageManager)!= null){
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                    } catch (e: Exception){

                    }

                    if (photoFile != null){
                        val photoUri = FileProvider.getUriForFile(this@AddNotesActivity,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                    }
                }
            }

        })

        openGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
                dialog.hide()
            }

        })
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
        finish()
    }

    companion object {
        const val REQUEST_CODE_GALLERY = 100
        const val REQUEST_CODE_CAMERA = 101
    }
}